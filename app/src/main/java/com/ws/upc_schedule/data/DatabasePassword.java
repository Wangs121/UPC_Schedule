package com.ws.upc_schedule.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabasePassword extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SQLiteDemo.db";
    private static final String TABLE_NAME = "DemoTable";
    private static final String COLUMN1 = "ID";
    private static final String COLUMN2 = "PASSWORD";
    private static DatabasePassword instance;

    public DatabasePassword(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID TEXT,PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public static synchronized DatabasePassword getDatabaseHelper(Context context){
        if (instance == null){
            instance = new DatabasePassword(context);
        }
        return instance;
    }

    public boolean insertData(String id,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN1, id);
        contentValues.put(COLUMN2, password);
        long success = db.insert(TABLE_NAME, null, contentValues);
        if (success == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public void updateData(String id, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN1, id);
        contentValues.put(COLUMN2, password);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
}
