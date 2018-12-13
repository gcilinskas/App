package com.example.moe.appversion04;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Data.SharedPref;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Model.User;

public class CreatePostActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private EditText price;
    private EditText location;
    private Button btnCreateJobPost;
    private DatabaseHandler db;

    private ImageView  postGallery;
    private Button btnPostGallery;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    private RadioGroup txt_help_gest;
    private RadioGroup txt_help_gest1;
    private RadioGroup txt_help_gest2;

    private RadioButton rButton1;
    private RadioButton rButton2;
    private RadioButton rButton3;
    private RadioButton rButton4;
    private RadioButton rButton5;
    private RadioButton rButton6;
    private RadioButton rButton7;
    private RadioButton rButton8;
    private RadioButton rButton9;

    private RadioGroup radioGroup;

    private String selectedRadioButtonText;

    private TextView select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        db = new DatabaseHandler(this);

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);
        price = (EditText) findViewById(R.id.price);
        location = (EditText) findViewById(R.id.location);
        btnCreateJobPost = (Button) findViewById(R.id.btnCreateJobPost);
        postGallery = (ImageView) findViewById(R.id.postGallery);
        btnPostGallery = (Button) findViewById(R.id.btnPostGallery);
        select =  (TextView) findViewById(R.id.select);
        rButton1 = (RadioButton) findViewById(R.id.rButton1);
        rButton2 = (RadioButton) findViewById(R.id.rButton2);
        rButton3 = (RadioButton) findViewById(R.id.rButton3);
        rButton4 = (RadioButton) findViewById(R.id.rButton4);
        rButton5 = (RadioButton) findViewById(R.id.rButton5);
        rButton6 = (RadioButton) findViewById(R.id.rButton6);
        rButton7 = (RadioButton) findViewById(R.id.rButton7);
        rButton8 = (RadioButton) findViewById(R.id.rButton8);
        rButton9 = (RadioButton) findViewById(R.id.rButton9);
        txt_help_gest = (RadioGroup) findViewById(R.id.txt_help_gest);
        txt_help_gest1 = (RadioGroup) findViewById(R.id.txt_help_gest1);
        txt_help_gest2 = (RadioGroup) findViewById(R.id.txt_help_gest2);

        // hide until its title is clicked
        txt_help_gest.setVisibility(View.GONE);
        txt_help_gest1.setVisibility(View.GONE);
        txt_help_gest2.setVisibility(View.GONE);

        btnPostGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnCreateJobPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(        !title.getText().toString().isEmpty()
                        && !description.getText().toString().isEmpty()
                        && !price.getText().toString().isEmpty()
                        && !location.getText().toString().isEmpty()) {
                    saveJobPostToDB(v);

                }
            }
        });

        txt_help_gest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the checked Radio Button ID from Radio Group[
                int selectedRadioButtonID = txt_help_gest.getCheckedRadioButtonId();

                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID != -1) {
                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    selectedRadioButtonText = selectedRadioButton.getText().toString();
                    select.setText(selectedRadioButtonText);
                }
            }
        });

        txt_help_gest1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // Get the checked Radio Button ID from Radio Group[
                int selectedRadioButtonID = txt_help_gest1.getCheckedRadioButtonId();

                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID != -1) {

                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    selectedRadioButtonText = selectedRadioButton.getText().toString();
                    select.setText(selectedRadioButtonText);

                }

            }
        });

        txt_help_gest2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // Get the checked Radio Button ID from Radio Group[
                int selectedRadioButtonID = txt_help_gest2.getCheckedRadioButtonId();

                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID != -1) {

                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    selectedRadioButtonText = selectedRadioButton.getText().toString();
                    select.setText(selectedRadioButtonText);

                }

            }
        });

    }
    /**
     * onClick handler
     */


    public void toggle_contents(View v){
        txt_help_gest.setVisibility( txt_help_gest.isShown()
                ? View.GONE
                : View.VISIBLE );

        for(int i = 0; i < 3; i++){
            ((RadioButton)txt_help_gest1.getChildAt(i)).setChecked(false);
            ((RadioButton)txt_help_gest2.getChildAt(i)).setChecked(false);
        }

    }

    public void toggle_contents1(View v){
        txt_help_gest1.setVisibility( txt_help_gest1.isShown()
                ? View.GONE
                : View.VISIBLE );

        for(int i = 0; i < 3; i++){
            ((RadioButton)txt_help_gest.getChildAt(i)).setChecked(false);
            ((RadioButton)txt_help_gest2.getChildAt(i)).setChecked(false);
        }
    }

    public void toggle_contents2(View v){
        txt_help_gest2.setVisibility( txt_help_gest2.isShown()
                ? View.GONE
                : View.VISIBLE );

        for(int i = 0; i < 3; i++){
            ((RadioButton)txt_help_gest1.getChildAt(i)).setChecked(false);
            ((RadioButton)txt_help_gest.getChildAt(i)).setChecked(false);
        }

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            postGallery.setImageURI(imageUri);
        }
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

    private void saveJobPostToDB(View v){

        JobPost jobPost = new JobPost();

        String newTitle = title.getText().toString();
        String newDescription = description.getText().toString();
        String newPrice = price.getText().toString();
        String newLocation = location.getText().toString();

        String userId = SharedPref.getDefaults("user_id", getApplicationContext());
        long newPostUserIdLong = db.getUserId(String.valueOf(db.getUser(Integer.parseInt(userId)).getEmail()));
        int newPostUserId = (int) newPostUserIdLong;

        int newSubcategoryId = db.getSubcategoryId(selectedRadioButtonText);
        int newCategoryId = db.getCategoryId(newSubcategoryId);

        jobPost.setTitle(newTitle);
        jobPost.setDescription(newDescription);
        jobPost.setPrice(newPrice);
        jobPost.setLocation(newLocation);
        jobPost.setImageUri(imageUri.toString());

        jobPost.setPostUserId(newPostUserId);
        jobPost.setSubcategory_id(newSubcategoryId);
        jobPost.setCategory_id(newCategoryId);

        // save to DB
        db.addJobPost(jobPost);

        Toast.makeText(this,"Item Saved!", Toast.LENGTH_LONG).show();


        Intent intent = new Intent(CreatePostActivity.this, JobPostsActivity.class);
        intent.putExtra("title", jobPost.getTitle());
        intent.putExtra("description", jobPost.getDescription());
        intent.putExtra("price", jobPost.getPrice());
        intent.putExtra("location", jobPost.getLocation());
        intent.putExtra("imageUri", jobPost.getImageUri());

        intent.putExtra("postUserId", jobPost.getPostUserId());
        intent.putExtra("categoryId", jobPost.getCategory_id());
        intent.putExtra("subcategoryId", jobPost.getSubcategory_id());


        startActivity(intent);

    }

}
