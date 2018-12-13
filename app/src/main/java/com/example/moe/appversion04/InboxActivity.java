package com.example.moe.appversion04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Data.SharedPref;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Model.Message;
import com.example.moe.appversion04.UI.InboxAdapter;
import com.example.moe.appversion04.UI.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private RecyclerView recyclerView;
    private InboxAdapter inboxAdapter;
    private List<Message> messageList;
    private List<Message> listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        db = new DatabaseHandler(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageList = new ArrayList<>();
        listItems = new ArrayList<>();

        Integer userId = Integer.parseInt(SharedPref.getDefaults("user_id", getApplicationContext()));

        if(!db.verifyEmptyInboxForReceivers(userId)) {
            messageList = db.getAllReceiverMessages(userId);
        } else {
            messageList = null;
        }

        if(messageList != null) {

            for (Message m : messageList) {
                Message message = new Message();

                message.setContent(m.getContent());
                message.setUser_id(m.getUser_id());
                message.setReceiver_id(m.getReceiver_id());
                message.setDateAdded(m.getDateAdded());
                message.setId(m.getId());

                listItems.add(message);
            }

        }

        inboxAdapter = new InboxAdapter(this, listItems);
        recyclerView.setAdapter(inboxAdapter);
        inboxAdapter.notifyDataSetChanged();

    }
}
