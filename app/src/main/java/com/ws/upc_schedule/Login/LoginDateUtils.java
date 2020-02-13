package com.ws.upc_schedule.Login;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class LoginDateUtils {

    //用来判断学年
    public static String getYear() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(sdf.format(now));
        sdf = new SimpleDateFormat("MM");
        int month = Integer.parseInt(sdf.format(now));
        //八月前为上一学年
        if (month <= 8) {
            return (year - 1) + "-" + year;
        } else {
            return year + "-" + (year + 1);
        }
    }

    //用来判断学期
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTerm(String yearMonthDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int days = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String YDM = sdf.format(now);
        LocalDate d1 = LocalDate.parse(yearMonthDay, formatter);
        LocalDate d2 = LocalDate.parse(YDM, formatter);
        days = (int) ChronoUnit.DAYS.between(d2, d1);
        if (days == 0) {
            return "1";
        } else {
            return "2";
        }
    }
}
