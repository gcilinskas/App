package com.example.moe.appversion04.Data.DatabaseStructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Model.Review;
import com.example.moe.appversion04.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewDB extends SQLiteOpenHelper {

    private Context ctx;

    public ReviewDB(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    public void addReview(Review review) {

        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_REVIEW_CONTENT, review.getContent());
        values.put(Constants.KEY_REVIEW_RATING, review.getRating());
        values.put(Constants.KEY_REVIEW_USER_ID, review.getUser_id());
        values.put(Constants.KEY_REVIEW_POST_ID, review.getPost_id());
        values.put(Constants.KEY_REVIEW_DATE_NAME, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME_REVIEWS, null, values);
    }

    public Review getReview(int id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME_REVIEWS, new String[] {
                        Constants.KEY_REVIEW_ID, Constants.KEY_REVIEW_CONTENT, Constants.KEY_REVIEW_RATING,
                        Constants.KEY_REVIEW_USER_ID, Constants.KEY_REVIEW_POST_ID, Constants.KEY_REVIEW_DATE_NAME}, Constants.KEY_REVIEW_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Review review = new Review();
        review.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_ID))));
        review.setContent(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_CONTENT)));
        review.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_RATING))));
        review.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_USER_ID))));
        review.setPost_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_POST_ID))));
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_REVIEW_DATE_NAME))).getTime());
        review.setDate_name(formatedDate);

        return review;

    }

    public List<Review> getAllReviews(){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<Review> reviewList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_REVIEWS, new String[] {
                        Constants.KEY_REVIEW_ID, Constants.KEY_REVIEW_CONTENT, Constants.KEY_REVIEW_RATING,
                        Constants.KEY_REVIEW_USER_ID, Constants.KEY_REVIEW_POST_ID, Constants.KEY_REVIEW_DATE_NAME},
                null, null, null, null,
                Constants.KEY_REVIEW_DATE_NAME + " DESC");

        if(cursor.moveToFirst()) {
            do{

                Review review = new Review();
                review.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_ID))));
                review.setContent(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_CONTENT)));
                review.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_RATING))));
                review.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_USER_ID))));
                review.setPost_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_POST_ID))));
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_REVIEW_DATE_NAME))).getTime());
                review.setDate_name(formatedDate);

                reviewList.add(review);

            } while(cursor.moveToNext());
        }

        return reviewList;
    }

    public int updateReview(Review review){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_REVIEW_CONTENT, review.getContent());
        values.put(Constants.KEY_REVIEW_RATING, review.getRating());
        values.put(Constants.KEY_REVIEW_USER_ID, review.getUser_id());
        values.put(Constants.KEY_REVIEW_POST_ID, review.getPost_id());
        values.put(Constants.KEY_REVIEW_DATE_NAME, java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME_REVIEWS, values, Constants.KEY_REVIEW_ID + "=?",
                new String[] {String.valueOf(review.getId())});

    }

    public void deleteReview(int id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();
        db.delete(Constants.TABLE_NAME_REVIEWS, Constants.KEY_REVIEW_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();

    }


    public List<Review> getAllPostReviews(int postId) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<Review> reviewList = new ArrayList<>();

        Cursor cursor = db.rawQuery( " select * from reviewsTBL where post_id = ?",
                new String[] { Integer.toString(postId) } );
        if(cursor.moveToFirst()) {
            do{

                Review review = new Review();
                review.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_ID))));
                review.setContent(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_CONTENT)));
                review.setRating(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_RATING))));
                review.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_USER_ID))));
                review.setPost_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_REVIEW_POST_ID))));
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_REVIEW_DATE_NAME))).getTime());
                review.setDate_name(formatedDate);

                reviewList.add(review);

            } while(cursor.moveToNext());
        }

        return reviewList;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
