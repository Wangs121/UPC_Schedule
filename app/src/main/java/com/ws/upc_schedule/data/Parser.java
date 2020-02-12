package com.ws.upc_schedule.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jsoup.nodes.Document;

import java.lang.reflect.Type;
import java.util.Collection;

import com.ws.upc_schedule.R;
import com.ws.upc_schedule.data.ClassesContainer.CMessage;
import com.ws.upc_schedule.myDateUtils;

import static android.content.Context.MODE_PRIVATE;

public class Parser {

    public static String firstDayofTerm = null;
    public static String term = null;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void findTerm(Document doc){
        Gson g = new Gson();
        String con = doc.body().text();
//        Log.d("data",con);
        JsonObject table;
        JsonElement d,weeks;
        String buffer;
        String[] weekdays;
        table = g.fromJson(con,JsonObject.class);//转换为JsonObject
        d = table.get("d");
        buffer = g.toJson(d);//转换为String
        table = g.fromJson(buffer,JsonObject.class);
        weeks = table.get("weekdays");
        weekdays = g.fromJson(weeks,String[].class);//得到日期
//        int ddays = myDateUtils.getdDays()
        term = myDateUtils.getTerm(weekdays[0]);
    }
    public static String getTerm(){
        return term;
    }
    public static void parser(Document doc){
        Log.d("data","start parsing");
        Log.d("data",firstDayofTerm);
            //解析
            Gson g = new Gson();
            String con = doc.body().text();
//        Log.d("data",con);
            JsonObject table;
            JsonElement d, weeks;
            String buffer;
            String[] weekdays;
            table = g.fromJson(con, JsonObject.class);//转换为JsonObject
            d = table.get("d");
            buffer = g.toJson(d);//转换为String
            table = g.fromJson(buffer, JsonObject.class);
            weeks = table.get("weekdays");
            weekdays = g.fromJson(weeks, String[].class);//得到日期
            d = table.get("classes");
            buffer = g.toJson(d);

            Type collectionType = new TypeToken<Collection<ClassesContainer>>() {
            }.getType();
            Collection<ClassesContainer> classes = g.fromJson(buffer, collectionType);
            int i = 0;
            if (firstDayofTerm == null) {
                firstDayofTerm = weekdays[0];
                dhHelper.storeFirstDayofTerm(firstDayofTerm);
            }
            for (ClassesContainer c : classes) {
                //第一次遍历为周日，随之增加
                if (c.getc_1() != null && !c.getc_1().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_1()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-01", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_2() != null && !c.getc_2().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_2()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-02", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_3() != null && !c.getc_3().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_3()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-03", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_4() != null && !c.getc_4().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_4()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-04", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_5() != null && !c.getc_5().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_5()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-05", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_6() != null && !c.getc_6().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_6()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-06", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_7() != null && !c.getc_7().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_7()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-07", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_8() != null && !c.getc_8().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_8()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-08", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_9() != null && !c.getc_9().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_9()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-09", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_10() != null && !c.getc_10().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_10()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-10", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_11() != null && !c.getc_11().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_11()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-11", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                if (c.getc_12() != null && !c.getc_12().isEmpty()) {
                    //
                    for (CMessage cm : c.getc_12()) {
                        dhHelper.cdbh_insert(weekdays[i] + "-12", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                    }
                }
                i += 1;
            }

    }


}
