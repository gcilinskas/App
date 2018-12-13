package com.example.moe.appversion04.UI;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Data.SharedPref;
import com.example.moe.appversion04.Model.Message;
import com.example.moe.appversion04.Model.Review;
import com.example.moe.appversion04.Model.User;
import com.example.moe.appversion04.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private Context context;
    private List<Message> messageItems;
    private DatabaseHandler db;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;


    public ChatAdapter(Context context, List<Message> messageItems) {
        this.context = context;
        this.messageItems = messageItems;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        ChatAdapter.ChatViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType) {
            case 1:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
                holder = new ChatAdapter.ChatViewHolder(view1);
                break;
            case 2:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_owner, parent, false);
                holder = new ChatAdapter.ChatViewHolder(view2);
                break;
            default:
                View view3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
                holder = new ChatAdapter.ChatViewHolder(view3);
                break;
        }
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
        // Task 3
        db = new DatabaseHandler(context);
        Message message = messageItems.get(position);
           User user = db.getUser(message.getUser_id());

        switch (holder.getItemViewType()){
            case 1:
                holder.txtMessage.setText(message.getContent());
                holder.txtMessageData.setText(message.getDateAdded());
                Picasso.with(context).load(Uri.parse(user.getImageUri())).into(holder.imgMessage);
                break;
            case 2:
                holder.txtMessage.setText(message.getContent());
                holder.txtMessageData.setText(message.getDateAdded());
                Picasso.with(context).load(Uri.parse(user.getImageUri())).into(holder.imgMessage);
                break;
            default:
                holder.txtMessage.setText(message.getContent());
                holder.txtMessageData.setText(message.getDateAdded());
                Picasso.with(context).load(Uri.parse(user.getImageUri())).into(holder.imgMessage);
                break;
        }

    }

    public void refillAdapter(Message message, RecyclerView rv){
        messageItems.add(message);
        notifyItemInserted(getItemCount()-1);
        rv.smoothScrollToPosition(getItemCount()-1);
    }

    public void focusOnMessage(RecyclerView rv){
        rv.smoothScrollToPosition(getItemCount());
    }

    public void cleanUp(){
        messageItems.clear();
    }



    @Override
    public int getItemCount() {
        return messageItems.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView txtMessage;
        public TextView txtMessageData;
        public ImageView imgMessage;
        Context context;

        public ChatViewHolder(@NonNull View v) {
            super(v);
            this.txtMessage = (TextView) v.findViewById(R.id.txtMessage);
            this.txtMessageData = (TextView) v.findViewById(R.id.txtMessageData);
            this.imgMessage = (ImageView) v.findViewById(R.id.imgMessage);
        }
    }

        @Override
    public int getItemViewType(int position) {
        final int current = Integer.parseInt(SharedPref.getDefaults("user_id", context));
        if (messageItems.get(position).getUser_id() != current) {
            return 1;
        } else if (messageItems.get(position).getUser_id() == current) {
            return 2;
        }
        return 0;
    }




}
