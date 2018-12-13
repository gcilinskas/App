package com.example.moe.appversion04.UI;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Model.Review;
import com.example.moe.appversion04.Model.User;
import com.example.moe.appversion04.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private List<Review> reviewItems;
    private DatabaseHandler db;

    public ReviewAdapter(Context context, List<Review> reviewItems) {
        this.context = context;
        this.reviewItems = reviewItems;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review, parent, false);
        // Task 2
        ReviewViewHolder holder = new ReviewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        // Task 3
        db = new DatabaseHandler(context);

        Review review = reviewItems.get(position);
        User user = db.getUser(review.getUser_id());

        holder.txtReviewContent.setText(review.getContent());
        holder.txtReviewAuthor.setText(user.getUsername());
        holder.txtReviewDate.setText(review.getDate_name());
        Picasso.with(context).load(Uri.parse(user.getImageUri())).into(holder.imgReview);
        holder.ratingBar.setRating(review.getRating());
    }

    @Override
    public int getItemCount() {
        // Task 1
        return reviewItems.size();
    }

    /* Add your code here */
    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        public TextView txtReviewContent;
        public TextView txtReviewAuthor;
        public TextView txtReviewDate;
        public ImageView imgReview;
        private RatingBar ratingBar;

        public ReviewViewHolder(View v) {
            super(v);
            this.txtReviewContent = (TextView) v.findViewById(R.id.txtReviewContent);
            this.txtReviewAuthor = (TextView) v.findViewById(R.id.txtReviewAuthor);
            this.txtReviewDate = (TextView) v.findViewById(R.id.txtReviewDate);
            this.ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
            this.imgReview = (ImageView) v.findViewById(R.id.imgReview);
        }


    }
}