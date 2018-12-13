package com.example.moe.appversion04.Util;

import android.content.Context;

import com.example.moe.appversion04.Data.DatabaseHandler;

public class Constants {


    public static final int DB_VERSION = 28;
    public static final String DB_NAME = "getWorkDB";
    public static final String TABLE_NAME = "usersTBL";
    public static final String TABLE_NAME_POSTS = "postsTBL";
    public static final String TABLE_NAME_CATEGORIES = "categoriesTBL";
    public static final String TABLE_NAME_SUBCATEGORIES = "subcategoriesTBL";
    public static final String TABLE_NAME_FAVORITES = "favoritesTBL";
    public static final String TABLE_NAME_REVIEWS = "reviewsTBL";
    public static final String TABLE_NAME_MESSAGES = "messagesTBL";

    //Table columns for USER
    public static final String KEY_ID = "user_id";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IMAGE_URI = "image_uri";
    public static final String KEY_DATE_NAME = "date_added";

    //Table columns for CATEGORIES
    public static final String KEY_CATEGORIES_ID = "id";
    public static final String KEY_CATEGORIES_NAME = "category_name";

    //Table columns for SUBCATEGORIES
    public static final String KEY_SUBCATEGORIES_ID = "id";
    public static final String KEY_SUBCATEGORIES_NAME = "subcategory_name";
    public static final String KEY_SUBCATEGORIES_CATEGORY_ID = "category_id";

    //Table columns for POSTS
    public static final String KEY_POST_ID = "id";
    public static final String KEY_POST_USER_ID = "postUserId";
    public static final String KEY_POST_TITLE = "title";
    public static final String KEY_POST_DESCRIPTION = "description";
    public static final String KEY_POST_PRICE = "price";
    public static final String KEY_POST_LOCATION = "location";
    public static final String KEY_POST_RATING = "rating";
    public static final String KEY_POST_IMAGE_URI = "imageUri";
    public static final String KEY_POST_DATE_NAME = "date_added";
    public static final String KEY_POST_CATEGORY_ID = "category_id";
    public static final String KEY_POST_SUBCATEGORY_ID = "subcategory_id";

    //Table columns for Favorites
    public static final String KEY_FAVORITE_ID = "id";
    public static final String KEY_FAVORITE_USER_ID = "user_id";
    public static final String KEY_FAVORITE_POST_ID = "post_id";
    public static final String KEY_FAVORITE_DATE_NAME = "date_added";

    //Table columns for Reviews
    public static final String KEY_REVIEW_ID = "id";
    public static final String KEY_REVIEW_CONTENT = "content";
    public static final String KEY_REVIEW_RATING = "rating";
    public static final String KEY_REVIEW_USER_ID = "user_id";
    public static final String KEY_REVIEW_POST_ID = "post_id";
    public static final String KEY_REVIEW_DATE_NAME = "date_added";

    //Table columns for Messages
    public static final String KEY_MESSAGE_ID = "id";
    public static final String KEY_MESSAGE_CONTENT = "content";
    public static final String KEY_MESSAGE_USER_ID = "user_id";
    public static final String KEY_MESSAGE_RECEIVER_ID = "receiver_id";
    public static final String KEY_MESSAGE_DATE_NAME = "date_added";

}
