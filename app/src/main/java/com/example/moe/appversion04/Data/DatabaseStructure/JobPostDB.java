package com.example.moe.appversion04.Data.DatabaseStructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobPostDB extends SQLiteOpenHelper {

    private Context ctx;

    public JobPostDB(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    public void addJobPost(JobPost jobPost){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_POST_USER_ID, jobPost.getPostUserId());
        values.put(Constants.KEY_POST_TITLE, jobPost.getTitle());
        values.put(Constants.KEY_POST_DESCRIPTION, jobPost.getDescription());
        values.put(Constants.KEY_POST_PRICE, jobPost.getPrice());
        values.put(Constants.KEY_POST_LOCATION, jobPost.getLocation());
        values.put(Constants.KEY_POST_RATING, jobPost.getRating());
        values.put(Constants.KEY_POST_IMAGE_URI, jobPost.getImageUri());
        values.put(Constants.KEY_POST_DATE_NAME, java.lang.System.currentTimeMillis());
        values.put(Constants.KEY_POST_CATEGORY_ID, jobPost.getCategory_id());
        values.put(Constants.KEY_POST_SUBCATEGORY_ID, jobPost.getSubcategory_id());

        db.insert(Constants.TABLE_NAME_POSTS, null, values);
        Log.d("DBTEST", "Saved Post To DB -- " );
    }

    public JobPost getJobPost(int id){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME_POSTS, new String[] {
                        Constants.KEY_POST_ID,Constants.KEY_POST_USER_ID, Constants.KEY_POST_TITLE, Constants.KEY_POST_DESCRIPTION, Constants.KEY_POST_PRICE,
                        Constants.KEY_POST_LOCATION, Constants.KEY_POST_RATING, Constants.KEY_POST_IMAGE_URI, Constants.KEY_POST_DATE_NAME, Constants.KEY_POST_CATEGORY_ID,
                        Constants.KEY_POST_SUBCATEGORY_ID},
                Constants.KEY_POST_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

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

        return jobPost;
    }

    public List<JobPost> getAllJobPosts() {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();

        List<JobPost> jobPostList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_POSTS, new String[] {
                        Constants.KEY_POST_ID,Constants.KEY_POST_USER_ID, Constants.KEY_POST_TITLE, Constants.KEY_POST_DESCRIPTION, Constants.KEY_POST_PRICE,
                        Constants.KEY_POST_LOCATION, Constants.KEY_POST_RATING, Constants.KEY_POST_IMAGE_URI,
                        Constants.KEY_POST_DATE_NAME, Constants.KEY_POST_CATEGORY_ID, Constants.KEY_POST_SUBCATEGORY_ID},
                null, null, null, null,
                Constants.KEY_POST_DATE_NAME + " DESC");

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

        return jobPostList;
    }



    public int updateJobPost(JobPost jobPost) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_POST_TITLE, jobPost.getTitle());
        values.put(Constants.KEY_POST_DESCRIPTION, jobPost.getDescription());
        values.put(Constants.KEY_POST_PRICE, jobPost.getPrice());
        values.put(Constants.KEY_POST_LOCATION, jobPost.getLocation());
        values.put(Constants.KEY_POST_RATING, jobPost.getRating());
        values.put(Constants.KEY_POST_IMAGE_URI, jobPost.getImageUri());

        //updated row
        return db.update(Constants.TABLE_NAME_POSTS, values, Constants.KEY_POST_ID + "=?",
                new String[]{ String.valueOf(jobPost.getId()) });
    }

    public void deleteJobPost(int id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();
        db.delete(Constants.TABLE_NAME_POSTS, Constants.KEY_POST_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    public int getJobPostCount(){
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME_POSTS;
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    /**  **/

    public List<JobPost> getSelectedJobPosts(int category_id) {

        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<JobPost> jobPostList = new ArrayList<>();

        Cursor cursor = db.rawQuery( "SELECT * FROM " + Constants.TABLE_NAME_POSTS + " WHERE " +
                Constants.KEY_POST_CATEGORY_ID + "=?", new String[] { Integer.toString(category_id) } );


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


    public List<JobPost> getJoinedPosts(int category_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<JobPost> jobPostList = new ArrayList<>();

        Cursor cursor = db.rawQuery( " select * from postsTBL INNER JOIN categoriesTBL on postsTBL.category_id = categoriesTBL.id WHERE categoriesTBL.id = ?",
                new String[] { Integer.toString(category_id) } );

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


    public List<JobPost> getJoinedSubcategoryPosts(int subcategory_id) {

        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<JobPost> jobPostList = new ArrayList<>();

        Cursor cursor = db.rawQuery( " select * from postsTBL INNER JOIN subcategoriesTBL on postsTBL.subcategory_id = subcategoriesTBL.id WHERE subcategoriesTBL.id = ?",
                new String[] { Integer.toString(subcategory_id) } );

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

    public List<JobPost> getUserPost(int user_id){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<JobPost> jobPostList = new ArrayList<>();

        Cursor cursor = db.rawQuery( " select * from postsTBL WHERE postUserId = ?",
                new String[] { Integer.toString(user_id) } );

        if(cursor.moveToFirst()) {
            do {
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

                jobPostList.add(jobPost);

                //         Log.d("DBTEST", "getJobPost(user_id).get(1).getTitle() is called ----  title" + getUserPost(user_id).get(1).getTitle());
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
