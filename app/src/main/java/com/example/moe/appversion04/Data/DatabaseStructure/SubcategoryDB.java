package com.example.moe.appversion04.Data.DatabaseStructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Model.Subcategory;
import com.example.moe.appversion04.Util.Constants;

import java.util.ArrayList;
import java.util.List;

public class SubcategoryDB extends SQLiteOpenHelper {

    private Context ctx;

    public SubcategoryDB (Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    public void addSubcategory(Subcategory subcategory){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_SUBCATEGORIES_NAME, subcategory.getName());
        values.put(Constants.KEY_SUBCATEGORIES_CATEGORY_ID, subcategory.getCategory_id());

        db.insert(Constants.TABLE_NAME_CATEGORIES, null, values);
        Log.d("DBTEST", "SUBCATEGORY SAVED TO DB ----- subcategoryName  = " + subcategory.getName());
    }

    public Subcategory getSubcategory (int id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME_SUBCATEGORIES, new String[] {
                        Constants.KEY_SUBCATEGORIES_ID, Constants.KEY_SUBCATEGORIES_NAME, Constants.KEY_SUBCATEGORIES_CATEGORY_ID}, Constants.KEY_SUBCATEGORIES_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Subcategory subcategory = new Subcategory();
        subcategory.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_SUBCATEGORIES_ID))));
        subcategory.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_SUBCATEGORIES_NAME)));
        subcategory.setCategory_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_SUBCATEGORIES_CATEGORY_ID))));
        cursor.close();
        return subcategory;
    }

    public List<Subcategory> getAllSubcategories(){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<Subcategory> subcategoryList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_SUBCATEGORIES, new String[] {
                        Constants.KEY_SUBCATEGORIES_ID, Constants.KEY_SUBCATEGORIES_NAME, Constants.KEY_SUBCATEGORIES_CATEGORY_ID},
                null, null, null, null,
                Constants.KEY_SUBCATEGORIES_ID + " DESC");

        if(cursor.moveToFirst()) {
            do{

                Subcategory subcategory = new Subcategory();
                subcategory.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_SUBCATEGORIES_ID))));
                subcategory.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_SUBCATEGORIES_NAME)));
                subcategory.setCategory_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_SUBCATEGORIES_CATEGORY_ID))));

                subcategoryList.add(subcategory);
            } while(cursor.moveToNext());
        }
        return subcategoryList;
    }


    public int getSubcategoriesCount(){
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME_SUBCATEGORIES;
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        cursor.close();
        return cursor.getCount();
    }

    public int getSubcategoryId (String name){
        int subcategoryId = 0;
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();
        String[] columns = {
                Constants.KEY_SUBCATEGORIES_ID
        };
        String selection = Constants.KEY_SUBCATEGORIES_NAME + " = ?";
        String[] selectionArgs = { name };

        Cursor cursor = db.query(Constants.TABLE_NAME_SUBCATEGORIES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.moveToFirst();
        subcategoryId = cursor.getInt(cursor.getColumnIndex(Constants.KEY_SUBCATEGORIES_ID));
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return subcategoryId;
        }
        cursor.close();
        return subcategoryId;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
