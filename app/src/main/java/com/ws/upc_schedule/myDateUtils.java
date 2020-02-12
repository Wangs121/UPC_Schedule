package com.ws.upc_schedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

@RequiresApi(api = Build.VERSION_CODES.O)
public class myDateUtils {

    private static String firstDayofTerm = null;
    private static String endDayofTerm = null;
//    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static int nowYear;
    private static int nowMonth;
    private static int nowDay;
    private static String currentYMD;
    private static int currentWeek;
    //本周日的YMD格式
//    private static String CurrentFirstWeekDaysMonthDay = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void initilize(Context context){
        final SharedPreferences loggedSP = context.getSharedPreferences(String.valueOf(R.string.FirstDayofTerm), MODE_PRIVATE);
        firstDayofTerm = loggedSP.getString(String.valueOf(R.string.FirstDayofTermKey),null);
        endDayofTerm = dd2YMD(firstDayofTerm,17*7);
        nowYear = Calendar.getInstance().get(Calendar.YEAR);
        nowMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        currentYMD = nowYear +"-";
        if(nowMonth<10){
            currentYMD+="0"+nowMonth+"-";
        }else {
            currentYMD+=nowMonth+"-";
        }
        if(nowDay<10){
            currentYMD+="0"+nowDay;
        }else {
            currentYMD+=nowDay;
        }
        //未开学
        if(getSpecificWeek(currentYMD)<0){
            currentYMD = firstDayofTerm;
//            CurrentFirstWeekDaysMonthDay = firstDayofTerm;
        }
        //学期已结束
        if(getSpecificWeek(currentYMD) > getSpecificWeek(endDayofTerm)){
            currentYMD = endDayofTerm;
//            CurrentFirstWeekDaysMonthDay = endDayofTerm;
        }
        currentWeek = getSpecificWeek(currentYMD);

    }

    public static String getFirstDayofTerm() {
        return firstDayofTerm;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int getSpecificWeek(String yearMonthDay) {

        int days=0;
        LocalDate d1 = LocalDate.parse(yearMonthDay,formatter);
        LocalDate d2 = LocalDate.parse(firstDayofTerm,formatter);
        days = (int)ChronoUnit.DAYS.between(d2, d1);
        days = (int)days/7+1;
        return days;
    }

    public static String getCurrentYMD() {
        return currentYMD;
    }

    public static int getCurrentWeek() {
        return currentWeek;
    }

    //    public static  int DayofWeek2Int(DayOfWeek dayOfWeek){
//        switch (dayOfWeek){
//            case MONDAY:
//                return 1;
//
//            case TUESDAY:
//                return 2;
//
//            case WEDNESDAY:
//                return 3;
//
//            case THURSDAY:
//                return 4;
//
//            case FRIDAY:
//                return 5;
//
//            case SATURDAY:
//                return 6;
//
//            case SUNDAY:
//                return 7;
//        }
//        return 0;
//    }
    //获得当前周的七个月日期
//    public static List<Integer> getCurrent7Days(){
//        int weekofDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//        int monthOfDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//        List<Integer> ret = new ArrayList<>();
//        for (int i=0;i<7;i++){
//            ret.add(monthOfDay - weekofDay+1+i);
//        }
//        return  ret;
//    }
    public static String getCurrentFirstWeekDaysMonthDay(){
//        if(CurrentFirstWeekDaysMonthDay==null){
            String YM = currentYMD.substring(0,8);
            int d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - Calendar.getInstance().get(Calendar.DAY_OF_WEEK) +1;
            if(d<10){
                YM+= "0"+d;
            }else {
                YM+=d;
            }
            return YM;
//            return CurrentFirstWeekDaysMonthDay= YM;
//        }
//        return CurrentFirstWeekDaysMonthDay;
    }
//    //偏离当前天数转换到yyyy-mm-dd格式
    public static String dd2YMD(int dw){
        LocalDate d1 = LocalDate.parse(getCurrentFirstWeekDaysMonthDay(),formatter);
        return d1.plusDays(dw).format(formatter);
    }
//    //偏离指定天数转换到yyyy-mm-dd格式
    public static String dd2YMD(String YDM,int dw){
        LocalDate d1 = LocalDate.parse(YDM,formatter);
        return d1.plusDays(dw).format(formatter);
    }


    //用来判断学年
    public static String getYear(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(sdf.format(now));
        sdf = new SimpleDateFormat("MM");
        int month = Integer.parseInt(sdf.format(now));
        //八月前为上一学年
        if(month<=8){
            return (year-1)+"-"+year;
        }else {
            return year +"-"+(year+1);
        }
    }
    //用来判断学期
    public static String getTerm(String yearMonthDay) {
        int days=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String YDM = sdf.format(now);
        LocalDate d1 = LocalDate.parse(yearMonthDay,formatter);
        LocalDate d2 = LocalDate.parse(YDM,formatter);
        days = (int)ChronoUnit.DAYS.between(d2, d1);
        if(days==0){
            return "1";
        }else{
            return "2";
        }
    }
}
