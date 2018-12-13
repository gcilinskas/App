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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Data.SharedPref;
import com.example.moe.appversion04.DetailsActivity;
import com.example.moe.appversion04.Model.Category;
import com.example.moe.appversion04.Model.Favorite;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Model.Subcategory;
import com.example.moe.appversion04.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<JobPost> jobPostItems;
    private List<Category> categoryItems;
    private List<Subcategory> subcategoryItems;
    private int count = 0;

    public RecyclerViewAdapter(Context context, List<JobPost> jobPostItems, List<Category> categoryItems, List<Subcategory> subcategoryItems) {
        this.context = context;
        this.jobPostItems = jobPostItems;
        this.categoryItems = categoryItems;
        this.subcategoryItems = subcategoryItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from
                (parent.getContext()).inflate(R.layout.list_row_job,parent,false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        JobPost jobPost = jobPostItems.get(position);
        Category category = categoryItems.get(position);

        Subcategory subcategory = subcategoryItems.get(position);
        holder.jobPostTitle.setText(jobPost.getTitle());
        holder.jobPostDescription.setText(jobPost.getDescription());
        holder.jobPostPrice.setText(jobPost.getPrice());
        holder.jobPostLocation.setText(jobPost.getLocation());
        holder.jobPostDateAdded.setText(jobPost.getDateItemAdded());
        Picasso.with(context).load(Uri.parse(jobPost.getImageUri())).into(holder.jobPostImage);
        holder.jobPostCategoryName.setText(category.getName());
        holder.jobPostSubcategoryName.setText(subcategory.getName());

        holder.setIsRecyclable(false);

        DatabaseHandler db;
        db = new DatabaseHandler(context);
        int currentUser = Integer.parseInt(SharedPref.getDefaults("user_id", context));

        if(db.getCurrentUserFavorites(currentUser).size() > 0){
            List<Favorite> fa = new ArrayList<>();
            fa = db.getCurrentUserFavorites(currentUser);
            for(Favorite f : fa) {
                if( f.getPost_id() == jobPost.getId()){
                    holder.favoriteView.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return jobPostItems.size();
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
        public ImageView favoriteView;

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

            favoriteView = (ImageView) view.findViewById(R.id.favorite);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //go to next screen
                    int position = getAdapterPosition();

                    JobPost jobPost = jobPostItems.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("postId", jobPost.getId());
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

            favoriteView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DatabaseHandler db;
                    db = new DatabaseHandler(context);
                    int currentUser = Integer.parseInt(SharedPref.getDefaults("user_id", context));
                    int position = getAdapterPosition();
                    JobPost jobPost = jobPostItems.get(position);
                    int count = 0;

                    List<Favorite> fa = new ArrayList<>();
                    fa = db.getCurrentUserFavorites(currentUser);

                    if(fa.size() > 0) {
                        for(Favorite f : fa) {
                            if( f.getPost_id() == jobPost.getId()){
                                Favorite favorite = db.getIsFavorForDelete(jobPost.getId(), currentUser);
                                    db.deleteFavorite(favorite.getId());
                                    favoriteView.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                                    count = 1;
                            }
                        }

                        if(count == 0){
                            Favorite favorite = new Favorite();
                            favorite.setPost_id(jobPost.getId());
                            favorite.setUser_id(currentUser);
                            db.addFavorite(favorite);
                            favoriteView.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                        }
                    }

                    else {
                        Favorite favorite = new Favorite();
                        favorite.setPost_id(jobPost.getId());
                        favorite.setUser_id(currentUser);
                        db.addFavorite(favorite);
                        favoriteView.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                    }

                }
            });

        }

        @Override
        public void onClick(View v) {



        }
    }



}
