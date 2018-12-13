package com.example.moe.appversion04;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Data.SharedPref;
import com.example.moe.appversion04.Model.Message;
import com.example.moe.appversion04.Model.Review;
import com.example.moe.appversion04.Model.User;
import com.example.moe.appversion04.UI.ChatAdapter;

import com.example.moe.appversion04.UI.ReviewAdapter;
import com.example.moe.appversion04.Util.Constants;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collector;

public class ChatActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private EditText edtMessage;
    private TextView txtMessageReceiver;
    private FloatingActionButton fabSend;

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;
    private List<Message> listItems;
    private List<Message> listItemsAnswer;
    private List<Message> messageListAnswer;
    private List<Message> listPagination;
    private SwipeRefreshLayout swipe;
    private final int TOTAL_ITEMS_LOAD = 10;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scroll);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID3);
        db = new DatabaseHandler(this);
        edtMessage = (EditText) findViewById(R.id.edtMessage);
        fabSend = (FloatingActionButton) findViewById(R.id.fabSend);
        txtMessageReceiver = (TextView) findViewById(R.id.txtMessageReceiver);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);


        final Bundle bundle = getIntent().getExtras();
        final int current = Integer.parseInt(SharedPref.getDefaults("user_id", getApplicationContext()));
        final int receiver = bundle.getInt("postUserId");
        final int userView = bundle.getInt("userView");


        scrollView.post(new Runnable() {
            @Override
            public void run() {

                chatAdapter.focusOnMessage(recyclerView);
                int scrollY = scrollView.getScrollY();
                scrollView.scrollTo(0, scrollY + 200);
            }
        });


        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtMessage.getText().toString().isEmpty()) {
                    User user = db.getUser(Integer.parseInt(SharedPref.getDefaults("user_id", getApplicationContext())));
                    Message message = new Message();
                    message.setContent(edtMessage.getText().toString());
                    message.setUser_id(current);
                    message.setReceiver_id(bundle.getInt("postUserId"));
                    db.addMessage(message);
                    Log.d("Chat", "---- testchat" + message.getId() + message.getContent() + message.getReceiver_id() + message.getUser_id());
                    edtMessage.setText("");
                    chatAdapter.refillAdapter(message, recyclerView);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtMessage.getWindowToken(), 0);
                    int scrollY = scrollView.getScrollY();
                    scrollView.scrollTo(0, scrollY + 140);
                }
            }
        });

        messageList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        listItemsAnswer = new ArrayList<>();
        messageListAnswer = new ArrayList<>();
        listPagination = new ArrayList<>();


        messageList = db.getSingleChatMessages(current, receiver);
        messageListAnswer = db.getSingleChatMessages(receiver, current);

        loadMessages();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    currentPage++;
                    listItems.clear();
                    chatAdapter.cleanUp();
                    loadMessages();
            }
        });

 }

 public void loadMessages(){

     if (messageList.size() > 0) {
         for (Message m : messageList) {
             Message message = new Message();
             message.setContent(m.getContent());
             message.setUser_id(m.getUser_id());
             message.setReceiver_id(m.getReceiver_id());
             message.setDateAdded(m.getDateAdded());
             listItems.add(message);
             Log.d("check", message.getId()+ " " + message.getContent());
         }
     }

     Log.d("check", "-------------------");

     if (messageListAnswer.size() > 0) {
         for (Message m : messageListAnswer) {
             Message message = new Message();
             message.setContent(m.getContent());
             message.setUser_id(m.getUser_id());
             message.setReceiver_id(m.getReceiver_id());
             message.setDateAdded(m.getDateAdded());
             Log.d("chat", message.getContent() + " " + message.getUser_id() + " user " + message.getReceiver_id() + " receiver ---- asdfg");
             listItems.add(message);
             Log.d("check", message.getId()+ " " + message.getContent());
         }
     }

     Log.d("check", "++++++++++ ----------------");

     Collections.sort(listItems, new Comparator<Message>() {
         public int compare(Message o1, Message o2) {
             return o1.getDateAdded().compareTo(o2.getDateAdded());
         }
     });

     for (int i = 0; i < listItems.size(); i++) {
         Log.d("chat", String.valueOf(listItems.get(i).getUser_id() + " - " + listItems.get(i).getReceiver_id() + listItems.get(i).getId()) + " ---- chatTest " + listItems.get(i).getContent() + "   " + listItems.get(i).getDateAdded());
     }

     swipe.setRefreshing(false);

     if(listItems.size() > currentPage * TOTAL_ITEMS_LOAD){
         chatAdapter = new ChatAdapter(this, getLastItems(currentPage * TOTAL_ITEMS_LOAD));
     } else{
         chatAdapter = new ChatAdapter(this, listItems);
     }

     recyclerView.setAdapter(chatAdapter);
     chatAdapter.notifyDataSetChanged();

 }


public List<Message> getLastItems(int i) {

    for(int j = 1; j <= i; j++) {
        listPagination.add( listItems.get(listItems.size()-j));
    }

    Collections.sort(listPagination, new Comparator<Message>() {
        public int compare(Message o1, Message o2) {
            return o1.getDateAdded().compareTo(o2.getDateAdded());
        }
    });
    return listPagination;
}


}



