package com.example.moe.appversion04.Data.DatabaseStructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Model.Message;
import com.example.moe.appversion04.Util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageDB extends SQLiteOpenHelper {
    private Context ctx;

    public MessageDB(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    public void addMessage(Message message) {

        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_MESSAGE_CONTENT, message.getContent());
        values.put(Constants.KEY_MESSAGE_USER_ID, message.getUser_id());
        values.put(Constants.KEY_MESSAGE_RECEIVER_ID, message.getReceiver_id());
        values.put(Constants.KEY_MESSAGE_DATE_NAME, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME_MESSAGES, null, values);
    }

    public Message getMessage(int id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME_MESSAGES, new String[] {
                        Constants.KEY_MESSAGE_ID, Constants.KEY_MESSAGE_CONTENT, Constants.KEY_MESSAGE_USER_ID, Constants.KEY_MESSAGE_RECEIVER_ID,
                        Constants.KEY_MESSAGE_DATE_NAME}, Constants.KEY_MESSAGE_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Message message = new Message();
        message.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_ID))));
        message.setContent(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_CONTENT)));
        message.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_USER_ID))));
        message.setReceiver_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_RECEIVER_ID))));

        java.text.DateFormat dateFormat = java.text.DateFormat.getTimeInstance(DateFormat.LONG, Locale.ENGLISH);
        String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_MESSAGE_DATE_NAME))).getTime());
        message.setDateAdded(formatedDate);

        return message;

    }

    public List<Message> getAllMessages(){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<Message> messageList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_MESSAGES, new String[] {
                        Constants.KEY_MESSAGE_ID, Constants.KEY_MESSAGE_CONTENT, Constants.KEY_MESSAGE_USER_ID, Constants.KEY_MESSAGE_RECEIVER_ID,
                        Constants.KEY_MESSAGE_DATE_NAME},
                null, null, null, null,
                Constants.KEY_MESSAGE_DATE_NAME + " DESC");

        if(cursor.moveToFirst()) {
            do{

                Message message = new Message();
                message.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_ID))));
                message.setContent(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_CONTENT)));
                message.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_USER_ID))));
                message.setReceiver_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_RECEIVER_ID))));

                java.text.DateFormat dateFormat = java.text.DateFormat.getTimeInstance(DateFormat.LONG, Locale.ENGLISH);
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_MESSAGE_DATE_NAME))).getTime());
                message.setDateAdded(formatedDate);

                messageList.add(message);

            } while(cursor.moveToNext());
        }

        return messageList;
    }

    public int updateMessage(Message message){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_MESSAGE_CONTENT, message.getContent());
        values.put(Constants.KEY_MESSAGE_USER_ID, message.getUser_id());
        values.put(Constants.KEY_MESSAGE_RECEIVER_ID, message.getUser_id());
        values.put(Constants.KEY_MESSAGE_DATE_NAME, java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME_MESSAGES, values, Constants.KEY_MESSAGE_ID + "=?",
                new String[] {String.valueOf(message.getId())});

    }

    public void deleteMessage(int id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();
        db.delete(Constants.TABLE_NAME_MESSAGES, Constants.KEY_MESSAGE_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();

    }

    public List<Message> getSingleChatMessages(int user_id, int post_user_id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<Message> messageList = new ArrayList<>();

        Cursor cursor = db.rawQuery( " select * from messagesTBL where user_id = ? and receiver_id = ?",
                new String[] { Integer.toString(user_id), Integer.toString(post_user_id) } );
        if(cursor.moveToFirst()) {
            do{

                Message message = new Message();
                message.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_ID))));
                message.setContent(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_CONTENT)));
                message.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_USER_ID))));
                message.setReceiver_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_RECEIVER_ID))));

                java.text.DateFormat dateFormat = java.text.DateFormat.getTimeInstance(DateFormat.LONG, Locale.ENGLISH);
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_MESSAGE_DATE_NAME))).getTime());
                message.setDateAdded(formatedDate);

                messageList.add(message);


            } while(cursor.moveToNext());
        }
        cursor.close();
        return messageList;
    }

    public boolean verifyEmptyInboxForReceivers(int receiver_id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * FROM messagesTBL where receiver_id = ?",
                new String[] {Integer.toString(receiver_id) } );
        if(cursor != null) {
            return true;
        } else{
            return false;
        }

    }

    public List<Message> getAllReceiverMessages(int receiver_id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<Message> messageList = new ArrayList<>();

        Cursor cursor = db.rawQuery( "select *,MAX(receiver_id) FROM messagesTBL where receiver_id = ?",
                new String[] {Integer.toString(receiver_id) } );
        if(cursor.moveToFirst()) {
            do{

                Message message = new Message();
                message.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_ID))));
                message.setContent(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_CONTENT)));
                message.setUser_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_USER_ID))));
                message.setReceiver_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_MESSAGE_RECEIVER_ID))));

                java.text.DateFormat dateFormat = java.text.DateFormat.getTimeInstance(DateFormat.LONG, Locale.ENGLISH);
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_MESSAGE_DATE_NAME))).getTime());
                message.setDateAdded(formatedDate);

                messageList.add(message);


            } while(cursor.moveToNext());
        }
        cursor.close();
        return messageList;

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
