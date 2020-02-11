package com.ws.upc_schedule.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.ws.upc_schedule.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class dhHelper {
    private static ClassesDataBase cdbh = null;
    private static SharedPreferences FirstDayofTerm = null;

    private static String currentDate = null;//本周日的日期

    public static void cdbh_init(Context context){
        cdbh = new ClassesDataBase(context);
        FirstDayofTerm = context.getSharedPreferences(String.valueOf(R.string.FirstDayofTerm), MODE_PRIVATE);
    }

    public static void storeFirstDayofTerm(String firstDayofTerm){
        final SharedPreferences.Editor FDTSP_edit = FirstDayofTerm.edit();
        FDTSP_edit.putString(String.valueOf(R.string.FirstDayofTermKey),firstDayofTerm);
        FDTSP_edit.commit();
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

    private static Cursor get_one_data(String index){
        return cdbh.getOneData(index);
    }
//    public static List<Course> get_one_weekCourse(String yearDayMonth){
//        if(currentDate != yearDayMonth){
//            currentDate = yearDayMonth;
//            int day =
//            for(int mday=day;mday<day+7;mday++){
//                for(int mclasses=1;mclasses<12;mclasses++){
//
//                }
//            }
////            courses.add(new Course())
//        }
//    }
    public static List<Course> get_one_weekCourse(String yearDayMonth) {
        List<Course> courses = new ArrayList<>();
        int sd = Integer.parseInt(yearDayMonth.substring(8));
        int id;
        String yearDay = yearDayMonth.substring(0,8);
        String index ;

        for(int day=0;day<7;day++){
            for(int classes=0;classes<12;classes++){
                id = sd+day;
                if(id<10){
                    if(classes<10){
                        index = yearDay+"0"+id+"-0"+classes;
                    }else {
                        index = yearDay+"0"+id+"-"+classes;
                    }
                }else{
                    if(classes<10){
                        index = yearDay+ id +"-0"+classes;
                    }else {
                        index = yearDay+ id +"-"+classes;
                    }
                }
                Cursor data = get_one_data(index);
                if(data.getCount()>0){
                    data.moveToNext();
                    courses.add(new Course(data.getString(0),data.getString(1),
                            data.getString(2),data.getString(3),
                            data.getInt(4)));
                }
            }
        }
        return courses;
    }
}
