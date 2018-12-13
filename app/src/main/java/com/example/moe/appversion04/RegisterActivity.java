package com.example.moe.appversion04;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Data.SharedPref;
import com.example.moe.appversion04.Model.User;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText password2;
    private Button registerSubmit;
    private DatabaseHandler db;

    private ImageView registerImageUri;
    private Button btnRegisterImageUri;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    Boolean registration = false;

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHandler(this);

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        registerSubmit = (Button) findViewById(R.id.registerSubmit);
        registerImageUri = (ImageView) findViewById(R.id.registerImageUri);
        btnRegisterImageUri = (Button) findViewById(R.id.btnRegisterImageUri);

        firstName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (firstName.getText().toString().length() <= 0) {
                    firstName.setError("First name can not be empty");
                } else {
                    firstName.setError(null);
                }
            }
        });

        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (lastName.getText().toString().length() <= 0) {
                    lastName.setError("Last name can not be empty");
                } else {
                    lastName.setError(null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (email.getText().toString().length() <= 0) {
                    email.setError("Email can not be empty");
                } else {
                    email.setError(null);

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnRegisterImageUri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!firstName.getText().toString().isEmpty()
                        && !lastName.getText().toString().isEmpty()
                        && !phoneNumber.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty()
                        && !username.getText().toString().isEmpty()
                        && !password.getText().toString().isEmpty()
                        && !password2.getText().toString().isEmpty()
                        ) {
                    if (db.checkIfEmailExists(email.getText().toString())) {
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                            if (!Pattern.matches("[a-zA-Z]+", phoneNumber.getText().toString())) {
                                if (!(phoneNumber.length() < 6 || phoneNumber.length() > 13)) {
                                    if (db.checkIfUsernameExists(username.getText().toString())) {

                                        saveUserToDB();
                                        Log.d("Register", "praejo");

                                    } else {
                                        Toast.makeText(getApplicationContext(), "This username already exists", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "This email already exists", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }


    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            registerImageUri.setImageURI(imageUri);
        }
    }

    private void saveUserToDB() {

        User user = new User();
        String newUserFirstName = firstName.getText().toString();
        String newUserLastName = lastName.getText().toString();
        String newUserPhoneNumber = phoneNumber.getText().toString();
        String newUserEmail = email.getText().toString();
        String newUserUsername = username.getText().toString();
        String newUserPassword = password.getText().toString();

        user.setFirstName(newUserFirstName);
        user.setLastName(newUserLastName);
        user.setPhoneNumber(newUserPhoneNumber);
        user.setEmail(newUserEmail);
        user.setUsername(newUserUsername);
        user.setPassword(newUserPassword);
        user.setImageUri(imageUri.toString());

        // save to DB
        db.addUser(user);
        Log.d("User Added: ----", String.valueOf(db.getUsersCount()) + " getUsersCount()");
        Toast.makeText(this, "Item Saved!", Toast.LENGTH_LONG).show();
        Log.d("Vardas : ---- ", user.getFirstName());
        Log.d("UserId : ---- ", String.valueOf(user.getId()) + "-- userId on Register Activity");

        Intent intent = new Intent(RegisterActivity.this, com.example.moe.appversion04.HomeActivity.class);
        intent.putExtra("FirstName", user.getFirstName());
        intent.putExtra("ImageUri", user.getImageUri());

        String ID = String.valueOf(db.getUserId(newUserEmail));
        SharedPref.setDefaults("user_id", ID, getApplicationContext());

        startActivity(intent);

        SharedPref.setDefaults("image_uri", user.getImageUri(), getApplicationContext());
    }
}
