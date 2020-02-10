package com.ws.upc_schedule;

import android.icu.util.Calendar;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.List;

public class myDateUtils {
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
    public static List<Integer> get_title_day(){
        int weekofDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int monthOfDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        List<Integer> ret = new ArrayList<>();
        for (int i=0;i<7;i++){
            ret.add(monthOfDay - weekofDay+1+i);
        }
        return  ret;
    }
}
