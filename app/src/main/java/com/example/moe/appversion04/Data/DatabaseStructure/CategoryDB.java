package com.example.moe.appversion04.Data.DatabaseStructure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moe.appversion04.Data.DatabaseHandler;
import com.example.moe.appversion04.Model.Category;
import com.example.moe.appversion04.Util.Constants;

import java.util.ArrayList;
import java.util.List;

public class CategoryDB extends SQLiteOpenHelper {

    private Context ctx;

    public CategoryDB(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_CATEGORIES_NAME, category.getName());

        db.insert(Constants.TABLE_NAME_CATEGORIES, null, values);
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME_CATEGORIES, new String[] {
                        Constants.KEY_CATEGORIES_ID, Constants.KEY_CATEGORIES_NAME}, Constants.KEY_CATEGORIES_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Category category = new Category();
        category.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_CATEGORIES_ID))));
        category.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CATEGORIES_NAME)));
        cursor.close();
        return category;

    }

    public List<Category> getAllCategories(){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();
        List<Category> categoryList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME_CATEGORIES, new String[] {
                        Constants.KEY_CATEGORIES_ID, Constants.KEY_CATEGORIES_NAME},
                null, null, null, null,
                Constants.KEY_CATEGORIES_ID + " DESC");

        if(cursor.moveToFirst()) {
            do{

                Category category = new Category();
                category.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_CATEGORIES_ID))));
                category.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_CATEGORIES_NAME)));

                categoryList.add(category);
            } while(cursor.moveToNext());
        }

        return categoryList;
    }

    public int getCategoriesCount(){
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME_CATEGORIES;
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public int getCategoryId (int id){
        int categoryId = 0;
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();
        String[] columns = {
                Constants.KEY_SUBCATEGORIES_CATEGORY_ID
        };
        String selection = Constants.KEY_SUBCATEGORIES_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(Constants.TABLE_NAME_SUBCATEGORIES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.moveToFirst();
        categoryId = cursor.getInt(cursor.getColumnIndex(Constants.KEY_SUBCATEGORIES_CATEGORY_ID));
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return categoryId;
        }
        cursor.close();
        return categoryId;
    }

    public void installCategories(){
        SQLiteDatabase db = DatabaseHandler.getInstance(this.ctx).getWritableDatabase();
        db.execSQL(" INSERT INTO subcategoriesTBL(id,subcategory_name,category_id)VALUES(1,'PHP',1);");
        db.execSQL(" INSERT INTO subcategoriesTBL(id,subcategory_name,category_id)VALUES(2,'JAVA',1);");
        db.execSQL(" INSERT INTO subcategoriesTBL(id,subcategory_name,category_id)VALUES(3,'Mobile Apps',1);");
        db.execSQL(" INSERT INTO subcategoriesTBL(id,subcategory_name,category_id)VALUES(4,'Logo Design',2);");
        db.execSQL(" INSERT INTO subcategoriesTBL(id,subcategory_name,category_id)VALUES(5,'Illustration',2);");
        db.execSQL(" INSERT INTO subcategoriesTBL(id,subcategory_name,category_id)VALUES(6,'Web Design',2);");
        db.execSQL(" INSERT INTO subcategoriesTBL(id,subcategory_name,category_id)VALUES(7,'Producers',3);");
        db.execSQL(" INSERT INTO subcategoriesTBL(id,subcategory_name,category_id)VALUES(8,'Voice Over',3);");
        db.execSQL(" INSERT INTO subcategoriesTBL(id,subcategory_name,category_id)VALUES(9,'Mixing',3);");
        db.execSQL(" INSERT INTO categoriesTBL(id,category_name)VALUES(1,'Programming and Tech');");
        db.execSQL(" INSERT INTO categoriesTBL(id,category_name)VALUES(2,'Graphics and Design');");
        db.execSQL(" INSERT INTO categoriesTBL(id,category_name)VALUES(3,'Music and Audio');");
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
