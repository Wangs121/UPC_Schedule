package com.ws.upc_schedule.Login;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ws.upc_schedule.data.ClassesContainer;
import com.ws.upc_schedule.data.ClassesContainer.CMessage;

import org.jsoup.nodes.Document;

import java.lang.reflect.Type;
import java.util.Collection;

public class LoginParser {

    private static String firstDayofTerm = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String findTerm(Document doc, Context context) {
        Gson g = new Gson();
        String con = doc.body().text();
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
        String term = LoginDateUtils.getTerm(weekdays[0]);
        LoginRepository.setTerm(context, term);
        return term;
    }

    public static void parse(String[] strings, Context context, LogindbHelper logindbHelper) {
        //解析
        Gson g = new Gson();
//        String con = doc.body().text();
//        Log.d("data",con);
        JsonObject table;
        JsonElement d;
        String buffer;
        table = g.fromJson(strings[0], JsonObject.class);//转换为JsonObject
        d = table.get("d");
        buffer = g.toJson(d);//转换为String
        table = g.fromJson(buffer, JsonObject.class);

        d = table.get("classes");
        buffer = g.toJson(d);

        Type collectionType = new TypeToken<Collection<ClassesContainer>>() {
        }.getType();
        Collection<ClassesContainer> classes = g.fromJson(buffer, collectionType);
        int i = 0;
        String weeks;
        if (Integer.parseInt(strings[1]) < 10) {
            weeks = "0" + Integer.parseInt(strings[1]) + "-";
        } else {
            weeks = strings[1] + "-";
        }
        if (firstDayofTerm == null) {
            JsonObject t;
            JsonElement w;
            String b;
            String[] weekdays;
            t = g.fromJson(strings[0], JsonObject.class);//转换为JsonObject
            d = t.get("d");
            b = g.toJson(d);//转换为String
            t = g.fromJson(b, JsonObject.class);
            w = t.get("weekdays");
            weekdays = g.fromJson(w, String[].class);//得到日期
            firstDayofTerm = weekdays[0];
            LoginRepository.setFirstDayofTerm(context, firstDayofTerm);
        }
        for (ClassesContainer c : classes) {
            //第一次遍历为周日，随之增加
            if (c.getc_1() != null && !c.getc_1().isEmpty()) {
                //
                for (CMessage cm : c.getc_1()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-01", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_2() != null && !c.getc_2().isEmpty()) {
                //
                for (CMessage cm : c.getc_2()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-02", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_3() != null && !c.getc_3().isEmpty()) {
                //
                for (CMessage cm : c.getc_3()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-03", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_4() != null && !c.getc_4().isEmpty()) {
                //
                for (CMessage cm : c.getc_4()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-04", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_5() != null && !c.getc_5().isEmpty()) {
                //
                for (CMessage cm : c.getc_5()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-05", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_6() != null && !c.getc_6().isEmpty()) {
                //
                for (CMessage cm : c.getc_6()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-06", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_7() != null && !c.getc_7().isEmpty()) {
                //
                for (CMessage cm : c.getc_7()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-07", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_8() != null && !c.getc_8().isEmpty()) {
                //
                for (CMessage cm : c.getc_8()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-08", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_9() != null && !c.getc_9().isEmpty()) {
                //
                for (CMessage cm : c.getc_9()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-09", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_10() != null && !c.getc_10().isEmpty()) {
                //
                for (CMessage cm : c.getc_10()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-10", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_11() != null && !c.getc_11().isEmpty()) {
                //
                for (CMessage cm : c.getc_11()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-11", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_12() != null && !c.getc_12().isEmpty()) {
                //
                for (CMessage cm : c.getc_12()) {
                    logindbHelper.insertData(weeks + cm.get_weekday() + "-12", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            i += 1;
        }
    }
}

