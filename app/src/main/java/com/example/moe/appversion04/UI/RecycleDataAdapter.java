package com.example.moe.appversion04.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.DetailsActivity;
import com.example.moe.appversion04.EditUserPostActivity;
import com.example.moe.appversion04.Model.Category;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Model.Subcategory;
import com.example.moe.appversion04.R;
import com.example.moe.appversion04.UserPostActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecycleDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<JobPost> jobPostList;
    private List<Category> categoryItems;
    private List<Subcategory> subcategoryItems;

    public  class AllPosts extends RecyclerView.ViewHolder {
        public TextView jobPostTitle;
        public TextView jobPostDescription;
        public TextView jobPostPrice;
        public TextView jobPostLocation;
        public TextView jobPostDateAdded;
        public ImageView jobPostImage;
        public TextView jobPostCategoryName;
        public TextView jobPostSubcategoryName;
        public Button jobPostEdit;
        public Button jobPostDelete;

        public AllPosts(View v) {
            super(v);

            this.jobPostTitle = (TextView) v.findViewById(R.id.jobPostTitle);
            this.jobPostDescription = (TextView) v.findViewById(R.id.jobPostDescription);
            this.jobPostPrice = (TextView) v.findViewById(R.id.jobPostPrice);
            this.jobPostLocation = (TextView) v.findViewById(R.id.jobPostLocation);
            this.jobPostDateAdded = (TextView) v.findViewById(R.id.jobPostDateAdded);
            this.jobPostImage = (ImageView) v.findViewById(R.id.jobPostImage);
            this.jobPostCategoryName = (TextView) v.findViewById(R.id.jobPostCategory);
            this.jobPostSubcategoryName = (TextView) v.findViewById(R.id.jobPostSubCategory);

            this.jobPostEdit = (Button) v.findViewById(R.id.jobPostEdit);
            this.jobPostDelete = (Button) v.findViewById(R.id.jobPostDelete);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //go to next screen
                    int position = getAdapterPosition();

                    JobPost jobPost = jobPostList.get(position);
                    Log.d("RecyclerViewAdapter :", "JobPostsActivity ---- get Items from database " + jobPostList.size() + " jobPostItems.size()");
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra("title", jobPost.getTitle());
                    intent.putExtra("description", jobPost.getDescription());
                    intent.putExtra("price", jobPost.getPrice());
                    intent.putExtra("location", jobPost.getLocation());
                    intent.putExtra("dateAdded", jobPost.getDateItemAdded());
                    intent.putExtra("imageUri", jobPost.getImageUri());
                    intent.putExtra("postUserId", jobPost.getPostUserId());
                    intent.putExtra("categoryId", jobPost.getCategory_id());
                    intent.putExtra("subcategoryId", jobPost.getSubcategory_id());

                    mContext.startActivity(intent);
                }
            });

            jobPostEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //go to next screen
                    int position = getAdapterPosition();

                    JobPost jobPost = jobPostList.get(position);
                    Log.d("RecyclerViewAdapter :", "JobPostsActivity ---- get Items from database " + jobPostList.size() + " jobPostItems.size()");
                    Intent intent = new Intent(mContext, EditUserPostActivity.class);
                    intent.putExtra("currentUserPostId", jobPost.getId());
                    intent.putExtra("title", jobPost.getTitle());
                    intent.putExtra("description", jobPost.getDescription());
                    intent.putExtra("price", jobPost.getPrice());
                    intent.putExtra("location", jobPost.getLocation());
                    intent.putExtra("dateAdded", jobPost.getDateItemAdded());
                    intent.putExtra("imageUri", jobPost.getImageUri());
                    intent.putExtra("postUserId", jobPost.getPostUserId());
                    intent.putExtra("categoryId", jobPost.getCategory_id());
                    intent.putExtra("subcategoryId", jobPost.getSubcategory_id());

                    mContext.startActivity(intent);
                }
            });

            jobPostDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    DatabaseHandler db;

                    JobPost jobPost = jobPostList.get(position);
                    db = new DatabaseHandler(mContext);
                    db.deleteJobPost(jobPost.getId());

                    Intent intent = new Intent(mContext, UserPostActivity.class);
                    mContext.startActivity(intent);

                }
            });

        }
    }

    public RecycleDataAdapter(Context mContext, List<JobPost> jobPostList, List<Category> categoryItems, List<Subcategory> subcategoryItems) {
        this.mContext = mContext;
        this.jobPostList = jobPostList;
        this.categoryItems = categoryItems;
        this.subcategoryItems = subcategoryItems;
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_job_edit, parent, false);

        return new AllPosts(itemView);
        //return new ViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        JobPost jobPost = jobPostList.get(position);
        Category category = categoryItems.get(position);
        Subcategory subcategory = subcategoryItems.get(position);

        ((AllPosts)holder).jobPostTitle.setText(jobPost.getTitle());
        ((AllPosts)holder).jobPostDescription.setText(jobPost.getDescription());
        ((AllPosts)holder).jobPostPrice.setText(jobPost.getPrice());
        ((AllPosts)holder).jobPostLocation.setText(jobPost.getLocation());
        ((AllPosts)holder).jobPostDateAdded.setText(jobPost.getDateItemAdded());
        Picasso.with(mContext).load(Uri.parse(jobPost.getImageUri())).into(((AllPosts)holder).jobPostImage);
        ((AllPosts)holder).jobPostCategoryName.setText(category.getName());
        ((AllPosts)holder).jobPostSubcategoryName.setText(subcategory.getName());

    }

    @Override
    public int getItemCount() {
        return jobPostList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView jobPostTitle;
        public TextView jobPostDescription;
        public TextView jobPostPrice;
        public TextView jobPostLocation;
        public TextView jobPostDateAdded;
        public ImageView jobPostImage;
        public TextView jobPostCategoryName;
        public TextView jobPostSubcategoryName;
        public int id;
        Context context;

        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);

            context = ctx;

            jobPostTitle = (TextView) view.findViewById(R.id.jobPostTitle);
            jobPostDescription = (TextView) view.findViewById(R.id.jobPostDescription);
            jobPostPrice = (TextView) view.findViewById(R.id.jobPostPrice);
            jobPostLocation = (TextView) view.findViewById(R.id.jobPostLocation);
            jobPostDateAdded = (TextView) view.findViewById(R.id.jobPostDateAdded);
            jobPostImage = (ImageView) view.findViewById(R.id.jobPostImage);
            jobPostCategoryName = (TextView) view.findViewById(R.id.jobPostCategory);
            jobPostSubcategoryName = (TextView) view.findViewById(R.id.jobPostSubCategory);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //go to next screen
                    int position = getAdapterPosition();

                    JobPost jobPost = jobPostList.get(position);
                    Log.d("RecyclerViewAdapter :", "JobPostsActivity ---- get Items from database " + jobPostList.size() + " jobPostItems.size()");
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("title", jobPost.getTitle());
                    intent.putExtra("description", jobPost.getDescription());
                    intent.putExtra("price", jobPost.getPrice());
                    intent.putExtra("location", jobPost.getLocation());
                    intent.putExtra("dateAdded", jobPost.getDateItemAdded());
                    intent.putExtra("imageUri", jobPost.getImageUri());
                    intent.putExtra("postUserId", jobPost.getPostUserId());
                    intent.putExtra("categoryId", jobPost.getCategory_id());
                    intent.putExtra("subcategoryId", jobPost.getSubcategory_id());

                    context.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View view) {

        }
    }


}

