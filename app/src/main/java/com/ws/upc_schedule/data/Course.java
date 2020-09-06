package com.ws.upc_schedule.data;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Course {

    private static final Random random = new Random();
    private static Map<String,Integer> colors = new HashMap<>();


//    private String index;
    private String name;
    private String location;
    private String teacher;
    private int length;


    private int week;
    private int color ;
    private int day;
    private int start;


    public Course(String index, String name, String location, String teacher, int length){
//        this.index = index;
        this.name = name;
        this.location = location;
        this.teacher = teacher;
        this.length = length;
        this.week = Integer.parseInt(index.substring(0,2));
        this.day = Integer.parseInt(index.substring(3,4));
        this.start = Integer.parseInt(index.substring(5));

        if(colors.get(name)==null){
            colors.put(name,randomColor());
        }
        this.color = colors.get(name);
    }

    public int getWeek() {
        return week;
    }

    public int getDay() {
        return day;
    }

    public int getLength(){
        return length;
    }

    public int getStart(){
        return start;
    }

    public int[] geStart2Time(){

//        switch (start){
//            case 1:
//                return 8;
//            case 2:
//                return 9;
//            case 3:
//                return 10;
//            case 4:
//                return 11;
//            case 5:
//                return 14;
//            case 6:
//                return 15;
//            case 7:
//                return 16;
//            case 8:
//                return 17;
//            case 9:
//                return 19;
//            case 10:
//                return 20;
//            case 11:
//                return 21;
//            case 12:
//                return 22;
//        }
        int[] sT = new int[2];
        switch (start){
            case 1:
                sT[0] = 8;
                sT[1] = 0;
                break;
            case 2:
                sT[0] = 8;
                sT[1] = 50;
                break;
            case 3:
                sT[0] = 9;
                sT[1] = 55;
                break;
            case 4:
                sT[0] = 10;
                sT[1] = 45;
                break;
            case 5:
                sT[0] = 11;
                sT[1] = 35;
                break;
            case 6:
                sT[0] = 14;
                sT[1] = 0;
                break;
            case 7:
                sT[0] = 14;
                sT[1] = 50;
                break;
            case 8:
                sT[0] = 15;
                sT[1] = 55;
                break;
            case 9:
                sT[0] = 16;
                sT[1] = 45;
                break;
            case 10:
                sT[0] = 19;
                sT[1] = 0;
                break;
            case 11:
                sT[0] = 19;
                sT[1] = 50;
                break;
            case 12:
                sT[0] = 20;
                sT[1] = 40;
                break;
        }
        return sT;
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
    private static @ColorInt int randomColor(){
        int r,g,b;
        do{
            r= random.nextInt(256);
            g= random.nextInt(256);
            b= random.nextInt(256);
        }while ((r*0.299 + g*0.587 + b*0.114)>186);
        return Color.argb(255,r,g,b);
    }
}
