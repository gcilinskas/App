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
import com.example.moe.appversion04.UI.RecycleDataAdapter;
import com.example.moe.appversion04.UI.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserPostActivity extends AppCompatActivity {

    private List<JobPost> jobPostListC;
    private List<JobPost> listItemss;

    private List<Category> categoryItems;
    private List<Subcategory> subcategoryItems;
    private DatabaseHandler db;

    private RecyclerView recyclerView;
    // private RecyclerViewAdapter recyclerViewAdapter;
    private RecycleDataAdapter recycleDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);

        db = new DatabaseHandler(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobPostListC = new ArrayList<>();
        listItemss = new ArrayList<>();
        categoryItems = new ArrayList<>();
        subcategoryItems = new ArrayList<>();

        //Get items from database

        Bundle bundle = getIntent().getExtras();

        int filterUserPost = bundle.getInt("FilterUserPost");

        jobPostListC = db.getUserPost(filterUserPost);

        Log.d("TAG", filterUserPost + "---- size user filterUserPost");

        for(JobPost c : jobPostListC) {
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

            listItemss.add(jobPost);
        }


        Log.d("TAG", listItemss.size() + "---- size user");

        recycleDataAdapter = new RecycleDataAdapter(this, listItemss, categoryItems,subcategoryItems);
        recyclerView.setAdapter(recycleDataAdapter);
        recycleDataAdapter.notifyDataSetChanged();

    }
}
