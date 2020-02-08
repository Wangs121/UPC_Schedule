package com.ws.upc_schedule.data;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.jsoup.nodes.Document;

import java.lang.reflect.Type;
import java.util.Collection;

import com.ws.upc_schedule.data.ClassesContainer.CMessage;

public class Parser {
    private AppCompatActivity activity;
    private ClassesDataBase dh;


    public Parser(AppCompatActivity activity){
        this.activity = activity;
        dh = new ClassesDataBase(this.activity);
    }

    public void parser(Document doc){
//        Log.d("data",doc.toString());
        Gson g = new Gson();
        String con = doc.body().text().toString();
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
        d = table.get("classes");
        buffer = g.toJson(d);

        Type collectionType = new TypeToken<Collection<ClassesContainer>>(){}.getType();
        Collection<ClassesContainer> classes = g.fromJson(buffer,collectionType);

        //解析至存储至数据库的格式
        int i=0;
        for(ClassesContainer c:classes) {
            //第一次遍历为周日，随之增加
            if(c.getc_1()!=null && !c.getc_1().isEmpty()) {
                //
                for(CMessage cm:c.getc_1()) {
                    Log.d("parse",weekdays[i]+"-01");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_2()!=null && !c.getc_2().isEmpty()) {
                //
                for(CMessage cm:c.getc_2()) {
                    Log.d("parse",weekdays[i]+"-02");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_3()!=null && !c.getc_3().isEmpty()) {
                //
                for(CMessage cm:c.getc_3()) {
                    Log.d("parse",weekdays[i]+"-03");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_4()!=null && !c.getc_4().isEmpty()) {
                //
                for(CMessage cm:c.getc_4()) {
                    Log.d("parse",weekdays[i]+"-04");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_5()!=null && !c.getc_5().isEmpty()) {
                //
                for(CMessage cm:c.getc_5()) {
                    Log.d("parse",weekdays[i]+"-05");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_6()!=null && !c.getc_6().isEmpty()) {
                //
                for(CMessage cm:c.getc_6()) {
                    Log.d("parse",weekdays[i]+"-06");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_7()!=null && !c.getc_7().isEmpty()) {
                //
                for(CMessage cm:c.getc_7()) {
                    Log.d("parse",weekdays[i]+"-07");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_8()!=null && !c.getc_8().isEmpty()) {
                //
                for(CMessage cm:c.getc_8()) {
                    Log.d("parse",weekdays[i]+"-08");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_9()!=null && !c.getc_9().isEmpty()) {
                //
                for(CMessage cm:c.getc_9()) {
                    Log.d("parse",weekdays[i]+"-09");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_10()!=null && !c.getc_10().isEmpty()) {
                //
                for(CMessage cm:c.getc_10()) {
                    Log.d("parse",weekdays[i]+"-10");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_11()!=null && !c.getc_11().isEmpty()) {
                //
                for(CMessage cm:c.getc_11()) {
                    Log.d("parse",weekdays[i]+"-11");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            if(c.getc_12()!=null && !c.getc_12().isEmpty()) {
                //
                for(CMessage cm:c.getc_12()) {
                    Log.d("parse",weekdays[i]+"-12");
                    Log.d("parse",cm.get_course_name());
                    Log.d("parse",cm.get_location());
                    Log.d("parse",cm.get_teacher());
                    Log.d("parse",Integer.toString(cm.get_totalLength()));
                }
            }
            i+=1;
        }

    }
}
