package com.ws.upc_schedule.data;

import android.content.Context;
import android.database.Cursor;

public class dhHelper {
    private static ClassesDataBase cdbh = null;

    public static void cdbh_init(Context context){
        cdbh = new ClassesDataBase(context);
    }
    public static boolean cdbh_insert(String index,String name,String location,String teacher,int totallength){
        if(cdbh == null)    return false;
        cdbh.insertData(index,name,location,teacher,totallength);
        return true;
    }
    public static boolean cdbh_empty(){
        if(cdbh == null)    return false;
        cdbh.EmptyData();
        return true;
    }
    public static Cursor get_all_data(){
        return cdbh.getAllData();
    }
}
