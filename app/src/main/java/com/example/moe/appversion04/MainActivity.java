package com.example.moe.appversion04;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Data.SharedPref;
import com.example.moe.appversion04.Model.User;
import com.example.moe.appversion04.Util.Constants;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.facebook.AccessToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.zxing.common.StringUtils;

public class MainActivity extends AppCompatActivity {

    private Button registerButton;
    private Button loginButton;
    private EditText loginEmail;
    private EditText loginPassword;
    private DatabaseHandler db;
    private Button install;
    private Button emailLogin;
    private Button emailCancel;

    private LoginButton login_button;

    private String fbProfileFirstName;
    private String fbProfileLastName;
    private String fbProfileEmail;
    private String fbProfileImageUri;
    private String fbProfileUsername;

    private CallbackManager callbackManager;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize facebook login button
        login_button = (LoginButton) findViewById(R.id.login_button);
        login_button.setBackgroundResource(R.drawable.facebook_login);

        //get database
        db = new DatabaseHandler(this);

        //initialize register button
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        //initialize login button
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set login as alert window
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_login, null);

                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

                loginEmail = (EditText) view.findViewById(R.id.loginEmail);
                loginPassword = (EditText) view.findViewById(R.id.loginPassword);
                emailLogin = (Button) view.findViewById(R.id.emailLogin);
                emailCancel = (Button) view.findViewById(R.id.emailCancel);

                //get inputs and check if valid
                emailLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputEmail = loginEmail.getText().toString();
                        String inputPassword = loginPassword.getText().toString();
                        Boolean checkEmailPass = db.emailpassword(inputEmail, inputPassword);
                        if (!inputEmail.isEmpty() && !inputPassword.isEmpty()) {
                            if (checkEmailPass) {

                                Toast.makeText(getApplicationContext(), "Logged In Succesfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, com.example.moe.appversion04.HomeActivity.class);
                                intent.putExtra("FirstName", inputEmail);
                                String ID = String.valueOf(db.getUserId(inputEmail));
                                SharedPref.setDefaults("user_id", ID, getApplicationContext());

                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_LONG).show();
                            }
                        } else{
                            Toast.makeText(getApplicationContext(), "Some Fields are empty", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                //go back
                emailCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        //install categories if db was cleaned
        install = (Button) findViewById(R.id.install);
        install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.installCategories();
            }
        });

        printKeyHash();

        //initialize FacebookSDK
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();
        login_button.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends","user_photos"));

        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                // get JSON from GraphRequest
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("responsee", object.toString());
                        getData(object);
                        Log.d("responsee", object.optString("last_name"));
                        Log.d("responsee", object.optString("first_name"));
                        String optEmail = object.optString("email");

                        if(optEmail != null){
                            int userFromEmailIndex = TextUtils.lastIndexOf(optEmail, '@');
                            fbProfileUsername = TextUtils.substring(optEmail,0,userFromEmailIndex);
                            fbProfileEmail = optEmail;
                        } else{
                            fbProfileUsername = null;
                            fbProfileEmail = null;
                        }

                        if(object.optString("id")!=null){
                            String optImageUri = "https://graph.facebook.com/"+object.optString("id")+"/picture?width=200&height=200";
                            fbProfileImageUri = optImageUri;
                        } else {
                            fbProfileImageUri = null;
                        }

                        if(object.optString("name")!=null){
                            String optFirstName = object.optString("first_name");
                            String optLastName = object.optString("last_name");
                            fbProfileFirstName = optFirstName;
                            fbProfileLastName = optLastName;
                        } else{
                            fbProfileFirstName = null;
                            fbProfileLastName = null;
                        }

                        //check if user is saved to db
                        if(fbProfileEmail != null){

                            if(db.checkEmail(fbProfileEmail)){
                                User user = new User(fbProfileFirstName,fbProfileLastName,fbProfileEmail,fbProfileUsername,fbProfileImageUri);
                                db.addUser(user);
                                Intent intent = new Intent(MainActivity.this, com.example.moe.appversion04.HomeActivity.class);
                                intent.putExtra("FirstName", fbProfileFirstName);
                                intent.putExtra("ImageUri", fbProfileImageUri);
                                startActivity(intent);
                                String ID = String.valueOf(user.getId());
                                SharedPref.setDefaults("image_uri", fbProfileImageUri, getApplicationContext());
                                SharedPref.setDefaults("user_id", ID, getApplicationContext());
                                Log.d("registerFacebook", "register addedToDB" + fbProfileFirstName + "    firstnamee");
                            }
                            else{
                                Intent intent = new Intent(MainActivity.this, com.example.moe.appversion04.HomeActivity.class);
                                intent.putExtra("FirstName", fbProfileFirstName);
                                intent.putExtra("ImageUri", fbProfileImageUri);
                                startActivity(intent);
                                String ID = String.valueOf(db.getUserId(fbProfileEmail));
                                SharedPref.setDefaults("image_uri", fbProfileImageUri, getApplicationContext());
                                SharedPref.setDefaults("user_id", ID, getApplicationContext());
                                Log.d("registerFacebook", "register loggedIn");
                            }

                            } else{
                                //pagal numeri prisijungt
                            }

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,email,name,first_name,last_name,friends");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }

    //get Data to graph request on facebook login
    private void getData(JSONObject object) {
        try{
            Log.d("uriii", "https://graph.facebook.com/"+object.getString("id")+"/picture?width=200&height=200");
            Log.d("objectGet", object.getString("name"));
            Log.d("objectGet", object.getString("email"));
            Log.d("objectGet", object.getString("first_name"));
            object.getString("name");
            object.getString("email");
            object.getString("first_name");
            object.getString("last_name");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //hash password for valid facebook login
    private void printKeyHash(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.moe.appversion04", PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }



}
