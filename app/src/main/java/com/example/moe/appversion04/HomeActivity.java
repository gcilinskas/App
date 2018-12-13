package com.example.moe.appversion04;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Data.SharedPref;
import com.example.moe.appversion04.Model.User;
import com.example.moe.appversion04.UI.RecycleDataAdapter;
import com.example.moe.appversion04.Util.Constants;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    private TextView txtFirstNameHome;
    private TextView txtUserIdHome;
    private Button btnBecomeFreelancer;
    private Button btnExploreFreelancer;
    private Button btnExploreCategory1;
    private Button btnExploreCategory2;
    private Button btnExploreCategory3;
    private ImageView homeImageUri;
    private Button btnUpdatePost;
    private Button btnFavJobPosts;
    private Button btnInbox;
    private Button btnLogout;

    private RelativeLayout subcategoryHomePHP;
    private RelativeLayout subcategoryHomeJAVA;
    private RelativeLayout subcategoryHomeMobileApps;
    private RelativeLayout subcategoryHomeLogoDesign;
    private RelativeLayout subcategoryHomeIllustration;
    private RelativeLayout subcategoryHomeWebDesign;
    private RelativeLayout subcategoryHomeProducer;
    private RelativeLayout subcategoryHomeVoiceOver;
    private RelativeLayout subcategoryHomeMixing;

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHandler(this);

        Bundle extras = getIntent().getExtras();
        Bundle extrasPost = getIntent().getExtras();


        txtFirstNameHome = (TextView) findViewById(R.id.txtFirstNameHome);
        txtUserIdHome = (TextView) findViewById(R.id.txtUserIdHome);

        String firstName = extras.getString("FirstName");
        String userId = SharedPref.getDefaults("user_id", getApplicationContext());

        txtFirstNameHome.setText(firstName);
        txtUserIdHome.setText(userId);

        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnBecomeFreelancer = (Button) findViewById(R.id.btnBecomeFreelancer);
        btnExploreFreelancer = (Button) findViewById(R.id.btnExploreFreelancer);
        btnExploreCategory1 = (Button) findViewById(R.id.btnExploreCategory1);
        btnExploreCategory2 = (Button) findViewById(R.id.btnExploreCategory2);
        btnExploreCategory3 = (Button) findViewById(R.id.btnExploreCategory3);
        homeImageUri = (ImageView) findViewById(R.id.homeImageUri);
        btnUpdatePost = (Button) findViewById(R.id.btnUpdatePost);
        btnFavJobPosts = (Button) findViewById(R.id.btnFavJobPosts);
        btnInbox = (Button) findViewById(R.id.btnInbox);

        subcategoryHomePHP = (RelativeLayout) findViewById(R.id.subcategoryHomePHP);
        subcategoryHomeJAVA = (RelativeLayout) findViewById(R.id.subcategoryHomeJAVA);
        subcategoryHomeMobileApps = (RelativeLayout) findViewById(R.id.subcategoryHomeMobileApps);
        subcategoryHomeLogoDesign = (RelativeLayout) findViewById(R.id.subcategoryHomeLogoDesign);
        subcategoryHomeIllustration = (RelativeLayout) findViewById(R.id.subcategoryHomeIllustration);
        subcategoryHomeWebDesign = (RelativeLayout) findViewById(R.id.subcategoryHomeWebDesign);
        subcategoryHomeProducer = (RelativeLayout) findViewById(R.id.subcategoryHomeProducer);
        subcategoryHomeVoiceOver = (RelativeLayout) findViewById(R.id.subcategoryHomeVoiceOver);
        subcategoryHomeMixing = (RelativeLayout) findViewById(R.id.subcategoryHomeMixing);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                if (AccessToken.getCurrentAccessToken() != null) {
                    disconnectFromFacebook();
                }
            }
        });

        btnBecomeFreelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, com.example.moe.appversion04.CreatePostActivity.class);

                startActivity(intent);
            }
        });

        btnExploreFreelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, JobPostsActivity.class);
                startActivity(intent);
            }
        });

        btnExploreCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CategoryJobPostsActivity.class);
                intent.putExtra("FilterCategory", 1);
                startActivity(intent);
            }
        });

        btnExploreCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CategoryJobPostsActivity.class);
                intent.putExtra("FilterCategory", 2);
                startActivity(intent);
            }
        });

        btnExploreCategory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CategoryJobPostsActivity.class);
                intent.putExtra("FilterCategory", 3);
                startActivity(intent);
            }
        });

        subcategoryHomePHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubcategoryJobPostsActivity.class);
                intent.putExtra("FilterSubcategory", 1);
                startActivity(intent);
            }
        });

        subcategoryHomeJAVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubcategoryJobPostsActivity.class);
                intent.putExtra("FilterSubcategory", 2);
                startActivity(intent);
            }
        });

        subcategoryHomeMobileApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubcategoryJobPostsActivity.class);
                intent.putExtra("FilterSubcategory", 3);
                startActivity(intent);
            }
        });

        subcategoryHomeLogoDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubcategoryJobPostsActivity.class);
                intent.putExtra("FilterSubcategory", 4);
                startActivity(intent);
            }
        });

        subcategoryHomeIllustration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubcategoryJobPostsActivity.class);
                intent.putExtra("FilterSubcategory", 5);
                startActivity(intent);
            }
        });

        subcategoryHomeWebDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubcategoryJobPostsActivity.class);
                intent.putExtra("FilterSubcategory", 6);
                startActivity(intent);
            }
        });

        subcategoryHomeProducer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubcategoryJobPostsActivity.class);
                intent.putExtra("FilterSubcategory", 7);
                startActivity(intent);
            }
        });

        subcategoryHomeVoiceOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubcategoryJobPostsActivity.class);
                intent.putExtra("FilterSubcategory", 8);
                startActivity(intent);
            }
        });

        subcategoryHomeMixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SubcategoryJobPostsActivity.class);
                intent.putExtra("FilterSubcategory", 9);
                startActivity(intent);
            }
        });

        btnUpdatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserPostActivity.class);
                Integer fup = Integer.parseInt(SharedPref.getDefaults("user_id", getApplicationContext()));
                intent.putExtra("FilterUserPost", fup);
                startActivity(intent);
            }
        });

        btnFavJobPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        btnInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, InboxActivity.class);
                startActivity(intent);
            }
        });


        if(SharedPref.getDefaults("image_uri", getApplicationContext())!=null) {
            Picasso.with(getApplicationContext()).load(Uri.parse(SharedPref.getDefaults("image_uri", getApplicationContext()))).into(homeImageUri);
        } else{
            Picasso.with(getApplicationContext()).load(R.drawable.face).into(homeImageUri);
        }


    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {

            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }

}
