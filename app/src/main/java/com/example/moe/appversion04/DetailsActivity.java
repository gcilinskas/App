package com.example.moe.appversion04;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Data.SharedPref;
import com.example.moe.appversion04.Model.Category;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Model.Review;
import com.example.moe.appversion04.Model.User;
import com.example.moe.appversion04.UI.RecyclerViewAdapter;
import com.example.moe.appversion04.UI.ReviewAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private TextView title;
    private TextView description;
    private TextView price;
    private TextView location;
    private TextView dateAdded;
    private ImageView imageUri;
    private ImageView userImageUri;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader = new ArrayList<>();
    private List<Review> listItems;
    private HashMap<String,List<String>> listHash = new HashMap<>();
    private Button btnWriteReview;
    private Button btnReadReview;
    private LinearLayout show;
    private DatabaseHandler db;
    private FloatingActionButton fabDetails;
    private TextView categoryNameDet;
    private TextView subCategoryNameDet;

    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        fabDetails = (FloatingActionButton) findViewById(R.id.fabDetails);
        title = (TextView) findViewById(R.id.titleDet);
        description = (TextView) findViewById(R.id.descriptionDet);
        price = (TextView) findViewById(R.id.priceDet);
        location = (TextView) findViewById(R.id.locationDet);
        dateAdded = (TextView) findViewById(R.id.dateAddedDet);
        imageUri = (ImageView) findViewById(R.id.imageUriDet);
        userImageUri = (ImageView) findViewById(R.id.userImageUri);
        btnWriteReview = (Button) findViewById(R.id.btnWriteReview);
        btnReadReview = (Button) findViewById(R.id.btnReadReview);
        show = (LinearLayout) findViewById(R.id.show);
        categoryNameDet = (TextView) findViewById(R.id.categoryNameDet);
        subCategoryNameDet = (TextView) findViewById(R.id.subCategoryNameDet);

        db = new DatabaseHandler(this);

        final Bundle bundle = getIntent().getExtras();

        int categoryId = bundle.getInt("categoryId");
        int subcategoryId = bundle.getInt("subcategoryId");

        Category category = db.getCategory(categoryId);
        Category subcategory = db.getCategory(subcategoryId);

        title.setText("Pavadinimas: " + bundle.getString("title"));
        categoryNameDet.setText("Category: " + category.getName());
        subCategoryNameDet.setText("SubCategory: " + subcategory.getName());
        description.setText(bundle.getString("description"));
        price.setText(bundle.getString("price"));
        location.setText(bundle.getString("location"));
        dateAdded.setText(bundle.getString("dateAdded"));

        User user = db.getUser(bundle.getInt("postUserId"));

        Picasso.with(getApplicationContext()).load(Uri.parse(user.getImageUri())).into(userImageUri);
        Picasso.with(getApplicationContext()).load(Uri.parse(bundle.getString("imageUri"))).into(imageUri);

        show.setVisibility(View.GONE);

        initData();
        listView = (ExpandableListView)findViewById(R.id.exp);
        listAdapter = new com.example.moe.appversion04.UI.ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        btnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.review_create, null);
                final EditText txtReviewContentCreate = (EditText) mView.findViewById(R.id.txtReviewContentCreate);
                final RatingBar ratingBarCreate = (RatingBar) mView.findViewById(R.id.ratingBarCreate);
                Button btnSubmitReview = (Button) mView.findViewById(R.id.btnSubmitReview);

                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                btnSubmitReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!txtReviewContentCreate.getText().toString().isEmpty() && ratingBarCreate.getRating() != 0.0 ){

                            Review review = new Review();
                            User user = db.getUser(Integer.parseInt(SharedPref.getDefaults("user_id", getApplicationContext())));

                            review.setContent(txtReviewContentCreate.getText().toString());
                            review.setRating(ratingBarCreate.getRating());

                            review.setUser_id(user.getId());
                            review.setPost_id(bundle.getInt("postId"));

                            db.addReview(review);
                            Log.d("Details", review.getContent() + review.getRating() + " CHECK ---- rating create");

                            dialog.dismiss();

                        }

                    }
                });

            }
        });


        //Reviews RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewList = new ArrayList<>();
        listItems = new ArrayList<>();

        reviewList = db.getAllPostReviews(bundle.getInt("postId"));

        if(reviewList.size() > 0) {
            for (Review c : reviewList) {

                Review review = new Review();

                review.setContent(c.getContent());
                review.setUser_id(c.getUser_id());
                review.setPost_id(c.getPost_id());
                review.setDate_name(c.getDate_name());
                review.setRating(c.getRating());

                listItems.add(review);
            }
        }

        reviewAdapter = new ReviewAdapter(this, listItems);
        recyclerView.setAdapter(reviewAdapter);
        reviewAdapter.notifyDataSetChanged();


        fabDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isReceiver = 0;
                Intent intent = new Intent(DetailsActivity.this, ChatActivity.class);

                intent.putExtra("postUserId", bundle.getInt("postUserId"));
                intent.putExtra("userView", isReceiver);

                startActivity(intent);
            }
        });


    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        User user = db.getUser(bundle.getInt("postUserId"));
//
//        listDataHeader = new ArrayList<>();
//        listHash = new HashMap<>();
        Log.d("DetailsUser", user.getFirstName() + "---- userfirstname");
        listDataHeader.add(user.getUsername());

        List<String> userData = new ArrayList<>();
        userData.add("User Information ");
        userData.add(user.getFirstName() + "  " + user.getLastName());
        userData.add(user.getEmail());
        userData.add(String.valueOf(user.getPhoneNumber()));
        userData.add("User Registered On: " + user.getDateItemAdded());
        Log.d("DetailsUser", user.getFirstName() + "---- userfirstname");

        listHash.put(listDataHeader.get(0),userData);
    }


    public void toggle_contents(View v){
        show.setVisibility( show.isShown()
                ? View.GONE
                : View.VISIBLE );
    }

    public static void slide_down(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }



}
