//package com.example.moe.appversion04.UI;
//
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.moe.appversion04.Model.Message;
//import com.example.moe.appversion04.R;
//
//import java.util.List;
//
//public class DataAdapter extends RecyclerView.Adapter {
//    private final int VIEW_ITEM = 1;
//    private final int VIEW_PROG = 0;
//
//    private List<Message> messageList;
//
//    // The minimum amount of items to have below your current scroll position
//// before loading more.
//    private int visibleThreshold = 5;
//    private int lastVisibleItem, totalItemCount;
//    private boolean loading;
//    private ChatAdapter.OnLoadMoreListener onLoadMoreListener;
//
//
//    public DataAdapter(List<Message> messages, RecyclerView recyclerView) {
//        messageList = messages;
//
//        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//
//            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
//                    .getLayoutManager();
//
//            recyclerView
//                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
//                        @Override
//                        public void onScrolled(RecyclerView recyclerView,
//                                               int dx, int dy) {
//                            super.onScrolled(recyclerView, dx, dy);
//
//                            totalItemCount = linearLayoutManager.getItemCount();
//                            lastVisibleItem = linearLayoutManager
//                                    .findLastVisibleItemPosition();
//                            if (!loading
//                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                                // End has been reached
//                                // Do something
//                                if (onLoadMoreListener != null) {
//                                    onLoadMoreListener.onLoadMore();
//                                }
//                                loading = true;
//                            }
//                        }
//                    });
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return messageList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                      int viewType) {
//        RecyclerView.ViewHolder vh;
//        if (viewType == VIEW_ITEM) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(
//                    R.layout.message, parent, false);
//
//            vh = new MessageViewHolder(v);
//        } else {
//            View v = LayoutInflater.from(parent.getContext()).inflate(
//                    R.layout.progressbar_item, parent, false);
//
//            vh = new ProgressViewHolder(v);
//        }
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof MessageViewHolder) {
//
//            Message message= (Message) messageList.get(position);
//
//            ((MessageViewHolder) holder).txtMessage.setText(message.getContent());
//
//            ((MessageViewHolder) holder).txtMessageData.setText(message.getDateAdded());
//
//        } else {
//            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
//        }
//    }
//
//    public void setLoaded() {
//        loading = false;
//    }
//
//    @Override
//    public int getItemCount() {
//        return messageList.size();
//    }
//
//    public void setOnLoadMoreListener(ChatAdapter.OnLoadMoreListener onLoadMoreListener) {
//        this.onLoadMoreListener = onLoadMoreListener;
//    }
//
//
//    //
//    public static class MessageViewHolder extends RecyclerView.ViewHolder {
//        public TextView txtMessage;
//
//        public TextView txtMessageData;
//
//        public MessageViewHolder(View v) {
//            super(v);
//            txtMessage = (TextView) v.findViewById(R.id.txtMessage);
//
//            txtMessageData = (TextView) v.findViewById(R.id.txtMessageData);
//
////            v.setOnClickListener(new View.OnClickListener() {
////
////                @Override
////                public void onClick(View v) {
////                    Toast.makeText(v.getContext(),
////                            "OnClick :" + message.getName() + " \n "+student.getEmailId(),
////                            Toast.LENGTH_SHORT).show();
////
////                }
////            });
//        }
//    }
//
//    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
//        public ProgressBar progressBar;
//
//        public ProgressViewHolder(View v) {
//            super(v);
//            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
//        }
//    }
//}
