package com.ws.upc_schedule.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClassesDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SQLiteCLASSES.db";
    private static final String TABLE_NAME = "CLASSESTABLE";
    private static final String COLUMN1 = "INDEXs";
    private static final String COLUMN2 = "NAME";
    private static final String COLUMN3 = "LOCATION";
    private static final String COLUMN4 = "TEACHER";
    private static final String COLUMN5 = "TOTALLENGTH";
    private static ClassesDataBase instance;

    public ClassesDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (INDEXs TEXT,NAME TEXT,LOCATION TEXT, TEACHER TEXT, TOTALLENGTH INTEGER)");
//        db.execSQL("CREATE TABLE " + TABLE_NAME + " (INDEXx TEXT,NAME TEXT,LOCATION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public static synchronized ClassesDataBase getDatabaseHelper(Context context){
        if (instance == null){
            instance = new ClassesDataBase(context);
        }
        return instance;
    }

    public boolean insertData(String index,String name,String location,String teacher,int totallength){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN1, index);
        contentValues.put(COLUMN2, name);
        contentValues.put(COLUMN3, location);
        contentValues.put(COLUMN4, teacher);
        contentValues.put(COLUMN5, totallength);
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
    public Cursor getOneData(String index){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where INDEXs = " + "'" + index + "'";
        Cursor data = db.rawQuery(Query,null);
//        String sql = "SELECT EXISTS (SELECT * FROM"
        return data;
    }
    public boolean checkDataExists(String index){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where INDEXs = " + "'" + index + "'";
        Cursor data = db.rawQuery(Query,null);
//        String sql = "SELECT EXISTS (SELECT * FROM"
        if(data.getCount() <=0){
            //data.close();
            return false;
        }
        return true;
    }

    public void updateData(String index,String name,String location,String teacher,int totallength){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN1, index);
        contentValues.put(COLUMN2, name);
        contentValues.put(COLUMN3, location);
        contentValues.put(COLUMN4, teacher);
        contentValues.put(COLUMN5, totallength);
        db.update(TABLE_NAME, contentValues, "INDEXs = ?", new String[] {index});
    }

    public Integer deleteData(String index){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "INDEXs = ?", new String[] {index});
    }

    public Integer EmptyData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null, null);
    }
}
