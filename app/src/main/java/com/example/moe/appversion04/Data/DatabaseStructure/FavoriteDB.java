package com.example.moe.appversion04.Data.DatabaseStructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Model.Favorite;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteDB extends SQLiteOpenHelper {

    private Context ctx;

    public FavoriteDB(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    public void addFavorite(Favorite favorite) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_FAVORITE_USER_ID, favorite.getUser_id());
        values.put(Constants.KEY_FAVORITE_POST_ID, favorite.getPost_id());
        values.put(Constants.KEY_FAVORITE_DATE_NAME, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME_FAVORITES, null, values);
        Log.d("DBTEST", "Favorite Saved to DB ---- favoriteId =  " + favorite.getId());
    }

    public Favorite getFavorite(int id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME_FAVORITES, new String[] {
                        Constants.KEY_FAVORITE_ID, Constants.KEY_FAVORITE_USER_ID, Constants.KEY_FAVORITE_POST_ID, Constants.KEY_FAVORITE_DATE_NAME},
                Constants.KEY_FAVORITE_ID + "=?", new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor!=null)
            cursor.moveToFirst();

        Favorite favorite = new Favorite();
        favorite.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_ID))));
        favorite.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_USER_ID))));
        favorite.setPost_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_POST_ID))));
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_FAVORITE_DATE_NAME))).getTime());
        favorite.setDate_name(formatedDate);

        return favorite;
    }

    public List<Favorite> getAllFavorites(){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();

        List<Favorite> favoriteList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_FAVORITES, new String[] {
                        Constants.KEY_FAVORITE_ID, Constants.KEY_FAVORITE_USER_ID, Constants.KEY_FAVORITE_POST_ID, Constants.KEY_FAVORITE_DATE_NAME},
                null, null, null, null, Constants.KEY_FAVORITE_DATE_NAME + " DESC");

        if(cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite();
                favorite.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_ID))));
                favorite.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_USER_ID))));
                favorite.setPost_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_POST_ID))));
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_FAVORITE_DATE_NAME))).getTime());
                favorite.setDate_name(formatedDate);
                favoriteList.add(favorite);
            } while (cursor.moveToNext());
        }

        return favoriteList;
    }

    public int updateFavorite(Favorite favorite) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_FAVORITE_USER_ID, favorite.getUser_id());
        values.put(Constants.KEY_FAVORITE_POST_ID, favorite.getPost_id());
        values.put(Constants.KEY_FAVORITE_DATE_NAME, java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME_FAVORITES, values, Constants.KEY_FAVORITE_ID + "=?",
                new String[]{ String.valueOf(favorite.getId()) });
    }

    public void deleteFavorite(int id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();
        db.delete(Constants.TABLE_NAME_FAVORITES, Constants.KEY_FAVORITE_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    public Favorite getIsFavor(int postId){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<Favorite> favorList = new ArrayList<>();

        Cursor cursor = db.rawQuery( " select * from favoritesTBL WHERE post_id = ?",
                new String[] { Integer.toString(postId) } );

        if(cursor!=null)
            cursor.moveToFirst();

        Favorite favorite = new Favorite();
        favorite.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_ID))));
        favorite.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_USER_ID))));
        favorite.setPost_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_POST_ID))));
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_FAVORITE_DATE_NAME))).getTime());
        favorite.setDate_name(formatedDate);

        return favorite;
    }

    public Favorite getIsFavorForDelete(int postId, int userId){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Favorite> favorList = new ArrayList<>();

        Cursor cursor = db.rawQuery( " select * from favoritesTBL WHERE post_id = ? and user_id = ?",
                new String[] { Integer.toString(postId), Integer.toString(userId) } );

        if(cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite();
                favorite.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_ID))));
                favorite.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_USER_ID))));
                favorite.setPost_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_POST_ID))));
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_FAVORITE_DATE_NAME))).getTime());
                favorite.setDate_name(formatedDate);
                return favorite;
            } while (cursor.moveToNext());
        } else{
            return null;
        }
    }

    public boolean getFav(int current_user_id, int job_post_id) {
        int jobPostId = 0;
        int currentUserId = 0;

        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        Cursor cursor = db.rawQuery( " select post_id from favoritesTBL WHERE user_id = ? and post_id = ?",
                new String[] { Integer.toString(current_user_id), Integer.toString(job_post_id) } );

        if(cursor != null) {

            cursor.close();
            db.close();
            return true;
        } else{
            return false;
        }
    }

    public List<Favorite> getCurrentUserFavorites(int current_user_id){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();

        List<Favorite> favoriteList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM favoritesTBL WHERE user_id =?",new String[] {String.valueOf(current_user_id)});

        if(cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite();
                favorite.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_ID))));
                favorite.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_USER_ID))));
                favorite.setPost_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_FAVORITE_POST_ID))));
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_FAVORITE_DATE_NAME))).getTime());
                favorite.setDate_name(formatedDate);
                favoriteList.add(favorite);
            } while (cursor.moveToNext());
        }

        return favoriteList;
    }

    public int getCurrentUserFav(int current_user_id, int job_post_id){
        int jobPostId = 0;
        int currentUserId = 0;

        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        Cursor cursor = db.rawQuery( " select post_id from favoritesTBL WHERE user_id = ? and post_id = ?",
                new String[] { Integer.toString(current_user_id), Integer.toString(job_post_id) } );

        if(cursor != null) {
            currentUserId = cursor.getInt(cursor.getColumnIndex(Constants.KEY_FAVORITE_USER_ID));
            cursor.close();
            db.close();
            return currentUserId;
        } else{
            return currentUserId;
        }
    }

    public List<JobPost> getCurrentUserFavoriteJobPosts(int user_id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<JobPost> jobPostList = new ArrayList<>();

        Cursor cursor = db.rawQuery( " select * from postsTBL INNER JOIN favoritesTBL on postsTBL.id = favoritesTBL.post_id WHERE favoritesTBL.user_id = ?",
                new String[] { Integer.toString(user_id) } );

        if(cursor.moveToFirst()) {
            do{
                JobPost jobPost = new JobPost();
                jobPost.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_POST_ID))));
                jobPost.setPostUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_POST_USER_ID))));
                jobPost.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_POST_TITLE)));
                jobPost.setDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_POST_DESCRIPTION)));
                jobPost.setPrice(cursor.getString(cursor.getColumnIndex(Constants.KEY_POST_PRICE)));
                jobPost.setLocation(cursor.getString(cursor.getColumnIndex(Constants.KEY_POST_LOCATION)));
                jobPost.setRating(cursor.getString(cursor.getColumnIndex(Constants.KEY_POST_RATING)));
                jobPost.setImageUri(cursor.getString(cursor.getColumnIndex(Constants.KEY_POST_IMAGE_URI)));

                jobPost.setCategory_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_POST_CATEGORY_ID))));
                jobPost.setSubcategory_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_POST_SUBCATEGORY_ID))));

                //convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_POST_DATE_NAME)))
                        .getTime());

                jobPost.setDateItemAdded(formatedDate);

                //Add to JobPostList
                jobPostList.add(jobPost);

                Log.d("DBTEST", "Get All Job Posts is Called ---- " + jobPost.getPostUserId());

            } while(cursor.moveToNext());
        }

        cursor.close();
        return jobPostList;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
