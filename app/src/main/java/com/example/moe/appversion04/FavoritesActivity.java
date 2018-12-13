package com.example.moe.appversion04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Data.SharedPref;
import com.example.moe.appversion04.Model.Category;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Model.Subcategory;
import com.example.moe.appversion04.UI.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<JobPost> jobPostList;
    private List<JobPost> listItems;

    private List<Category> categoryItems;
    private List<Subcategory> subcategoryItems;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        db = new DatabaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobPostList = new ArrayList<>();
        listItems = new ArrayList<>();
        categoryItems = new ArrayList<>();
        subcategoryItems = new ArrayList<>();


        //Get items from database
        Integer userId = Integer.parseInt(SharedPref.getDefaults("user_id", getApplicationContext()));
        jobPostList = db.getCurrentUserFavoriteJobPosts(userId);



        for(JobPost c : jobPostList) {


                JobPost jobPost = new JobPost();
                Category category = db.getCategory(c.getCategory_id());
                Subcategory subcategory = db.getSubcategory(c.getSubcategory_id());

                jobPost.setTitle(c.getTitle());
                jobPost.setDescription(c.getDescription());
                jobPost.setPrice(c.getPrice());
                jobPost.setLocation(c.getLocation());
                jobPost.setDateItemAdded("Added on: " + c.getDateItemAdded());
                jobPost.setImageUri(c.getImageUri());
                jobPost.setId(c.getId());

                jobPost.setPostUserId(c.getPostUserId());
                jobPost.setCategory_id(c.getCategory_id());
                jobPost.setSubcategory_id(c.getSubcategory_id());

                categoryItems.add(category);
                subcategoryItems.add(subcategory);

                listItems.add(jobPost);

        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, listItems, categoryItems,subcategoryItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }
}
