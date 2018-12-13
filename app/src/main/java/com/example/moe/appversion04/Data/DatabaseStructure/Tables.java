package com.example.moe.appversion04.Data.DatabaseStructure;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Util.Constants;

public class Tables {

    public Tables(){

    }

    public void createTables(SQLiteDatabase db){
        String CREATE_USERS_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_FIRST_NAME +
                " TEXT," + Constants.KEY_LAST_NAME + " TEXT," + Constants.KEY_PHONE_NUMBER +
                " TEXT," + Constants.KEY_EMAIL + " TEXT," + Constants.KEY_USERNAME +
                " TEXT," + Constants.KEY_PASSWORD + " TEXT, " + Constants.KEY_IMAGE_URI +
                " TEXT," + Constants.KEY_DATE_NAME + " LONG);";

        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_CATEGORIES + "("
                + Constants.KEY_CATEGORIES_ID + " INTEGER PRIMARY KEY," + Constants.KEY_CATEGORIES_NAME + " TEXT);";

        String CREATE_SUBCATEGORIES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_SUBCATEGORIES + "("
                + Constants.KEY_SUBCATEGORIES_ID + " INTEGER PRIMARY KEY," + Constants.KEY_SUBCATEGORIES_NAME
                + " TEXT," + Constants.KEY_SUBCATEGORIES_CATEGORY_ID + " INTEGER);";

        String CREATE_POSTS_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_POSTS + "("
                + Constants.KEY_POST_ID + " INTEGER PRIMARY KEY," + Constants.KEY_POST_USER_ID + " INTEGER," + Constants.KEY_POST_TITLE
                + " TEXT," + Constants.KEY_POST_DESCRIPTION + " TEXT," + Constants.KEY_POST_PRICE
                + " TEXT," + Constants.KEY_POST_LOCATION + " TEXT," + Constants.KEY_POST_RATING
                + " TEXT," + Constants.KEY_POST_IMAGE_URI + " TEXT," + Constants.KEY_POST_DATE_NAME
                + " LONG," + Constants.KEY_POST_CATEGORY_ID + " INTEGER," + Constants.KEY_POST_SUBCATEGORY_ID + " INTEGER);";

        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_FAVORITES + "("
                + Constants.KEY_FAVORITE_ID + " INTEGER PRIMARY KEY," + Constants.KEY_FAVORITE_USER_ID + " INTEGER,"
                + Constants.KEY_FAVORITE_POST_ID + " INTEGER," + Constants.KEY_FAVORITE_DATE_NAME + " LONG);";

        String CREATE_REVIEWS_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_REVIEWS + "("
                + Constants.KEY_REVIEW_ID + " INTEGER PRIMARY KEY," + Constants.KEY_REVIEW_CONTENT + " TEXT,"
                + Constants.KEY_REVIEW_RATING + " REAL," + Constants.KEY_REVIEW_USER_ID + " INTEGER,"
                + Constants.KEY_REVIEW_POST_ID + " INTEGER," + Constants.KEY_REVIEW_DATE_NAME + " LONG);";

        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME_MESSAGES + "("
                + Constants.KEY_MESSAGE_ID + " INTEGER PRIMARY KEY," + Constants.KEY_MESSAGE_CONTENT + " TEXT,"
                + Constants.KEY_MESSAGE_USER_ID + " INTEGER," + Constants.KEY_MESSAGE_RECEIVER_ID + " INTEGER," + Constants.KEY_MESSAGE_DATE_NAME + " LONG);";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_SUBCATEGORIES_TABLE);
        db.execSQL(CREATE_POSTS_TABLE);
        db.execSQL(CREATE_FAVORITES_TABLE);
        db.execSQL(CREATE_REVIEWS_TABLE);
        db.execSQL(CREATE_MESSAGES_TABLE);
    }


    public void deleteTables(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_SUBCATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_MESSAGES);
        createTables(db);
    }





}
