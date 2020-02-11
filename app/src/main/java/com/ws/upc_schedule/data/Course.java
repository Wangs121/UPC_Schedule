package com.ws.upc_schedule.data;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Course {

    private static final Random random = new Random();
    private static Map<String,Integer> colors = new HashMap<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar c = Calendar.getInstance();


    private String index;
    private String name;
    private String location;
    private String teacher;
    private int length;

    private String yearMonthDay;

    private int dayOfWeek;
    private int color ;
    private int dayofMonth;
    private int start;

//    private int end;

    Course(String index,String name,String location,String teacher, int length){
        this.index = index;
        this.name = name;
        this.location = location;
        this.teacher = teacher;
        this.length = length;
        this.yearMonthDay = index.substring(0,11);
        this.dayofMonth = Integer.parseInt(index.substring(5,7));
        this.start = Integer.parseInt(index.substring(11,13));
        try{
            c.setTime(sdf.parse(yearMonthDay));
            this.dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        }catch (Exception e){

        }
        if(colors.get(name)==null){
            colors.put(name,randomColor());
        }
        this.color = colors.get(name);
    }
    public int getDayofWeek(){
        return dayOfWeek;
    }
    public int getDayofWeeks(){
        return dayOfWeek-1;
    }

    public int getLength(){
        return length;
    }

    public int getStart(){
        return start;
    }

    public int geStart2Time(){
        switch (start){
            case 1:
                return 8;
            case 2:
                return 9;
            case 3:
                return 10;
            case 4:
                return 11;
            case 5:
                return 14;
            case 6:
                return 15;
            case 7:
                return 16;
            case 8:
                return 17;
            case 9:
                return 19;
            case 10:
                return 20;
            case 11:
                return 21;
            case 12:
                return 22;
        }
        return 0;
    }
    public int getColor(){
        return color;
    }
    public String getName(){
        return name;
    }
    public String getLocation(){
        return location;
    }
    public String getTeacher(){
        return teacher;
    }
    public int getDayofMonth(){
        return dayofMonth;
    }
    private static @ColorInt int randomColor(){
        return Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
    }
}
