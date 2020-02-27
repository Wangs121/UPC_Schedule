package com.ws.upc_schedule.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.temporal.ChronoUnit;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class dateUtils {
    private static String termBeginDay;
    private static String term;
    private static String stuYear = null;//aaaa-bbbb年第n学期
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void setTerm(String term) {
        dateUtils.term = term;
    }

    public static void setTermBeginDay(String termBeginDay) {
        dateUtils.termBeginDay = termBeginDay;
    }

    public static String getStuYear() {
        Date now = new Date();
        if (stuYear == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            int year = Integer.parseInt(sdf.format(now));
            sdf = new SimpleDateFormat("MM");
            int month = Integer.parseInt(sdf.format(now));
            //八月前为上一学年
            if (month <= 8) {
                stuYear = (year - 1) + "-" + year;
            } else {
                stuYear = year + "-" + (year + 1);
            }
            stuYear += "年\t第" + term + "学期";
        }
        return stuYear;
    }

    public static String getTermBeginDay() {
        return termBeginDay;
    }

    public static int getCurrentWeek() {
            int currentWeek = 1;
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
            return currentWeek;
    }

    //获得第Week周的第一天的YDM
    public static String getFirstDayofWeek(int Week) {
        LocalDate d = LocalDate.parse(termBeginDay, formatter);
        return d.plusDays((Week-1) * 7).format(formatter);
    }

    public static int[] getWeekMonthDays(String YMD) {
        int month = Integer.parseInt(YMD.substring(5, 7));
        int day = Integer.parseInt(YMD.substring(8));
        int[] WeekMonthDays = new int[7];
        WeekMonthDays[0] = day;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            for (int i = 1; i < 7; i++) {
                day += 1;
                if (day > 31) {
                    day = 1;
                }
                WeekMonthDays[i] = day;
            }
        } else if (month == 2) {
            //判断是否为闰年
            int year = Integer.parseInt(YMD.substring(0, 4));
            if ((year % 4 == 0) & (year % 100 != 0) || (year % 100 == 0)) {
                for (int i = 1; i < 7; i++) {
                    day += 1;
                    if (day > 29) {
                        day = 1;
                    }
                    WeekMonthDays[i] = day;
                }
            } else {
                for (int i = 1; i < 7; i++) {
                    day += 1;
                    if (day > 28) {
                        day = 1;
                    }
                    WeekMonthDays[i] = day;
                }
            }
        } else {
            for (int i = 1; i < 7; i++) {
                day += 1;
                if (day > 30) {
                    day = 1;
                }
                WeekMonthDays[i] = day;
            }
        }

        return WeekMonthDays;
    }
}
