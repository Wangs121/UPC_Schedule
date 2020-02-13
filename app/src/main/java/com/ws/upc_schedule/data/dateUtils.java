package com.ws.upc_schedule.data;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.temporal.ChronoUnit;

import com.ws.upc_schedule.Login.LoginRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class dateUtils {
    private static String termBeginDay;
    private static int currentWeek = 0;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void setTermBeginDay(String termBeginDay) {
        dateUtils.termBeginDay = termBeginDay;
    }

    public static String getTermBeginDay() {
        return termBeginDay;
    }

    public static int getCurrentWeek() {
        if (currentWeek == 0) {
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String n = sdf.format(now);

            LocalDate d1 = LocalDate.parse(termBeginDay, formatter);
            LocalDate d2 = LocalDate.parse(n, formatter);
            int delta = (int) ChronoUnit.DAYS.between(d1, d2);
            currentWeek = (int) (delta / 7) + 1;
            if (currentWeek < 1) {
                currentWeek = 1;
            }
            if (currentWeek > 18) {
                currentWeek = 18;
            }
        }
        return currentWeek;
    }

    public static String getFirstDayofWeek(int Week) {
        LocalDate d = LocalDate.parse(termBeginDay, formatter);
        return d.plusDays(Week * 7).format(formatter);
    }
}
