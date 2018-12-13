package com.example.moe.appversion04.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moe.appversion04.ChatActivity;
import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.DetailsActivity;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Model.Message;
import com.example.moe.appversion04.Model.Review;
import com.example.moe.appversion04.Model.User;
import com.example.moe.appversion04.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxViewHolder> {


    private Context context;
    private List<Message> messageItems;
    private DatabaseHandler db;

    public InboxAdapter(Context context, List<Message> messageItems) {
        this.context = context;
        this.messageItems = messageItems;
    }

    @Override
    public InboxAdapter.InboxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_item, parent, false);
        // Task 2
        InboxAdapter.InboxViewHolder holder = new InboxAdapter.InboxViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(InboxAdapter.InboxViewHolder holder, int position) {
        // Task 3
        db = new DatabaseHandler(context);

        Message message = messageItems.get(position);
        User user = db.getUser(message.getUser_id());

        holder.txtClientUsername.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        // Task 1
        return messageItems.size();
    }

    /* Add your code here */
    public class InboxViewHolder extends RecyclerView.ViewHolder {


        public TextView txtClientUsername;

        public InboxViewHolder(View v) {
            super(v);
            this.txtClientUsername = (TextView) v.findViewById(R.id.txtClientUsername);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //go to next screen
                    int position = getAdapterPosition();


                    Message message = messageItems.get(position);
                    User user = db.getUser(message.getUser_id());
                    int isReceiver = 1;

                    Intent intent = new Intent(context, ChatActivity.class);

                    intent.putExtra("id", message.getId());
                    intent.putExtra("content", message.getContent());
                    intent.putExtra("userId", message.getUser_id());
                    intent.putExtra("receiverId", message.getReceiver_id());
                    intent.putExtra("userView", isReceiver);
                    intent.putExtra("postUserId", message.getUser_id());
                    intent.putExtra("clientId", message.getUser_id());



                    context.startActivity(intent);
                }
            });

        }
    }


    }
