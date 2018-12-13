package com.example.moe.appversion04.Data.DatabaseStructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Model.User;
import com.example.moe.appversion04.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDB extends SQLiteOpenHelper {

    private Context ctx;

    public UserDB(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    //ADD User
    public void addUser(User user){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_FIRST_NAME, user.getFirstName());
        values.put(Constants.KEY_LAST_NAME, user.getLastName());
        values.put(Constants.KEY_PHONE_NUMBER, user.getPhoneNumber());
        values.put(Constants.KEY_EMAIL, user.getEmail());
        values.put(Constants.KEY_USERNAME, user.getUsername());
        values.put(Constants.KEY_PASSWORD, user.getPassword());
        values.put(Constants.KEY_IMAGE_URI, user.getImageUri());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());
        //Insert the row
        db.insert(Constants.TABLE_NAME, null, values);
    }

    //GET User
    public User getUser(int id){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                        Constants.KEY_ID, Constants.KEY_FIRST_NAME, Constants.KEY_LAST_NAME,
                        Constants.KEY_PHONE_NUMBER, Constants.KEY_EMAIL, Constants.KEY_USERNAME,
                        Constants.KEY_USERNAME, Constants.KEY_PASSWORD, Constants.KEY_IMAGE_URI,
                        Constants.KEY_DATE_NAME}, Constants.KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        user.setFirstName(cursor.getString(cursor.getColumnIndex(Constants.KEY_FIRST_NAME)));
        user.setLastName(cursor.getString(cursor.getColumnIndex(Constants.KEY_LAST_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL)));
        user.setUsername(cursor.getString(cursor.getColumnIndex(Constants.KEY_USERNAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD)));
        user.setImageUri(cursor.getString(cursor.getColumnIndex(Constants.KEY_IMAGE_URI)));

        //convert timestamp to something readable
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                .getTime());

        user.setDateItemAdded(formatedDate);
        cursor.close();
        return user;
    }

    //GET All Users
    public List<User> getAllUsers(){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();

        List<User> userList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                        Constants.KEY_ID, Constants.KEY_FIRST_NAME, Constants.KEY_LAST_NAME,
                        Constants.KEY_PHONE_NUMBER, Constants.KEY_EMAIL, Constants.KEY_USERNAME,
                        Constants.KEY_USERNAME, Constants.KEY_PASSWORD, Constants.KEY_IMAGE_URI,
                        Constants.KEY_DATE_NAME},
                null, null, null, null,
                Constants.KEY_DATE_NAME + " DESC");

        if(cursor.moveToFirst()) {
            do{
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(Constants.KEY_FIRST_NAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(Constants.KEY_LAST_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(Constants.KEY_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(Constants.KEY_PASSWORD)));
                user.setImageUri(cursor.getString(cursor.getColumnIndex(Constants.KEY_IMAGE_URI)));

                //convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                        .getTime());

                user.setDateItemAdded(formatedDate);

                userList.add(user);
            } while(cursor.moveToNext());
        }

        return userList;
    }

    //UPDATE User
    public int updateUser(User user) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_FIRST_NAME, user.getFirstName());
        values.put(Constants.KEY_LAST_NAME, user.getLastName());
        values.put(Constants.KEY_EMAIL, user.getEmail());
        values.put(Constants.KEY_USERNAME, user.getUsername());
        values.put(Constants.KEY_PASSWORD, user.getPassword());
        values.put(Constants.KEY_IMAGE_URI, user.getImageUri());

        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?",
                new String[]{ String.valueOf(user.getId()) });
    }

    //DELETE User
    public void deleteUser(int id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    //GET Users Count
    public int getUsersCount(){
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public String getUsername(){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        String selectQuery = "SELECT "+Constants.KEY_USERNAME+ " FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String username=cursor.getString(cursor.getColumnIndex(Constants.KEY_USERNAME));
        cursor.close();
        return username;
    }

    public long getUserId (String email){
        long userId = 0;
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();
        String[] columns = {
                Constants.KEY_ID
        };
        String selection = Constants.KEY_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(Constants.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.moveToFirst();
        userId = cursor.getLong(cursor.getColumnIndex(Constants.KEY_ID));
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return userId;
        }
        cursor.close();
        return userId;
    }


    public Boolean checkEmail(String email){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + Constants.TABLE_NAME +
                " where email=?",new String[]{email});

        if(cursor.getCount() > 0) return false;
        else return true;
    }


    public Boolean emailpassword(String email, String password){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Constants.TABLE_NAME +
                " where email=? and password=?", new String[] {email,password});
        if(cursor.getCount()>0)

            return true;

        else return false;

    }

    public Boolean checkIfUsernameExists(String username) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT username FROM " + Constants.TABLE_NAME + " WHERE username = ?", new String[] {username});

        if(cursor != null)
            return true;
        else
            return false;

    }

    public Boolean checkIfEmailExists(String email) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT email FROM " + Constants.TABLE_NAME + " WHERE email = ?", new String[] {email});

        if(cursor != null)
            return true;
        else
            return false;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
