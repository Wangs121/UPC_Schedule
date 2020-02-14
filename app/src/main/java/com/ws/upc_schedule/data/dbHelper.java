package com.ws.upc_schedule.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.ws.upc_schedule.Login.LoginRepository;

import java.util.ArrayList;
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
        dateUtils.setTerm(LoginRepository.getTerm(context));
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
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }

    //获取week周的所有课程
    public static List<Course> getOneWeekCoueses(int week) {
        List<Course> courses = new ArrayList<>();
        String index;
        for (int days = 0; days < 7; days++) {
            for (int classes = 1; classes < 12; classes++) {
                if (week < 10) {
                    if (classes < 10) {
                        index = "0" + week + "-" + days + "-0" + classes;
                    } else {
                        index = "0" + week + "-" + days + "-" + classes;
                    }
                } else {
                    if (classes < 10) {
                        index = week + "-" + days + "-0" + classes;
                    } else {
                        index = week + "-" + days + "-" + classes;
                    }
                }
//                Log.d("Courses",index);
                Cursor data = dbHelper.getOneData(index);
                if (data.getCount() > 0) {
                    data.moveToNext();
//                    Log.d("Courses",data.getString(0)+data.getString(1)+
//                            data.getString(2)+ data.getString(3)+
//                            data.getInt(4));
                    courses.add(new Course(data.getString(0), data.getString(1),
                            data.getString(2), data.getString(3),
                            data.getInt(4)));
                }
            }
        }
        return courses;
    }

    public static List<Course> getCurrentCourses() {
        if (currentCourses == null) {
            currentCourses = getOneWeekCoueses(dateUtils.getCurrentWeek());
        }
        return currentCourses;
    }
}
