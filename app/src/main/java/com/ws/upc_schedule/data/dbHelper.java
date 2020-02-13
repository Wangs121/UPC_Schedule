package com.ws.upc_schedule.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ws.upc_schedule.Login.LoginRepository;

import java.util.List;

public class dbHelper {
    private static ClassesDataBase dbHelper = null;

//    private static String termBeginDay = null;
    private static List<Course> currentCourses = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void initilize(Context context) {
        dbHelper = new ClassesDataBase(context);
        dateUtils.setTermBeginDay(
                LoginRepository.getFirstDayofTerm(context));
    }

    public static void insert(String index, String name, String location, String teacher, int totallength) {
        dbHelper.insertData(index, name, location, teacher, totallength);
    }

    public static void clear() {
        dbHelper.clearData();
    }

    public static Cursor getAllData() {
        return dbHelper.getAllData();
    }

    public static void close() {
        dbHelper.close();
        dbHelper = null;
    }

//    //获取week周的所有课程
//    public static List<Course> getOneWeekCoueses(String week) {
//    }
//
//    public static List<Course> getCurrentCourses(){
//        if(currentCourses == null){
//
//        }
//    }
}
