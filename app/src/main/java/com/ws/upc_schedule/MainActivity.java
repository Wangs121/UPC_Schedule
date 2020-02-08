package com.ws.upc_schedule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ws.upc_schedule.Login.LoginRepository;
import com.ws.upc_schedule.data.ClassesContainer;
import com.ws.upc_schedule.data.ClassesDataBase;
import com.ws.upc_schedule.data.ClassesContainer.CMessage;
import com.ws.upc_schedule.data.Parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.reflect.Type;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    Button logout;
    Button update;
    Button clear;
    Button show_all;
    ClassesDataBase dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //判断是否登录过
        boolean a = LoginRepository.isLoggedIn(this);
        if( a== false) {
            Toast.makeText(getApplicationContext(),"未登录",Toast.LENGTH_SHORT).show();
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
        }

        logout = (Button)findViewById(R.id.Logout);
        update = (Button)findViewById(R.id.update);
        clear = (Button)findViewById((R.id.clear));
        show_all = (Button)findViewById(R.id.show);
        dh = new ClassesDataBase(this);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRepository.logout(MainActivity.this);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] c = LoginRepository.ReadCookies(MainActivity.this);
                new crawler().execute(c);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dh.EmptyData();
            }
        });
        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData();
            }
        });

    }

    public void viewData(){
        Cursor data = dh.getAllData();
        StringBuffer stringBuffer = new StringBuffer();
        while (data.moveToNext()){
            stringBuffer.append("\nINDEXs: " + data.getString(0));
            stringBuffer.append("\nNAME: " + data.getString(1));
            stringBuffer.append("\nLOCATION: " + data.getString(2));
            stringBuffer.append("\nTEACHER: " + data.getString(3));
            stringBuffer.append("\nTOTALLENGTH: " + data.getString(4));
//            stringBuffer.append("\nPassword: " + data.getString(2) + "\n");
        }
        dialog("Data", stringBuffer.toString());
    }

    public void dialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public class crawler extends AsyncTask<String, Document, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String url = "https://app.upc.edu.cn/timetable/wap/default/get-datatmp";
            String year = "2019-2020";
            String term = "1";
            String type = "2";
            Document mes;
            try{
                for(int i=1;i<19;i++){
                    mes = Jsoup.connect(url)
                            .data("year",year)
                            .data("term",term)
                            .data("week",Integer.toString(i))
                            .data("type",type)
                            .cookie("eai-sess",strings[0])
                            .cookie("UUkey",strings[1])
                            .ignoreContentType(true)
                            .post();
                    publishProgress(mes);
//            Log.d("data",mes.body().toString());
                }
                return null;
            }catch(Exception e){
//            Log.d("data",e.toString());
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Document... documents){
            super.onProgressUpdate(documents);
            //解析
            Gson g = new Gson();
            String con = documents[0].body().text();
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
            int i=0;
            for(ClassesContainer c:classes) {
                //第一次遍历为周日，随之增加
                if(c.getc_1()!=null && !c.getc_1().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_1()) {
                        dh.insertData(weekdays[i]+"-01",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_2()!=null && !c.getc_2().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_2()) {
                        dh.insertData(weekdays[i]+"-02",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_3()!=null && !c.getc_3().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_3()) {
                        dh.insertData(weekdays[i]+"-03",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_4()!=null && !c.getc_4().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_4()) {
                        dh.insertData(weekdays[i]+"-04",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_5()!=null && !c.getc_5().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_5()) {
                        dh.insertData(weekdays[i]+"-05",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_6()!=null && !c.getc_6().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_6()) {
                        dh.insertData(weekdays[i]+"-06",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_7()!=null && !c.getc_7().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_7()) {
                        dh.insertData(weekdays[i]+"-07",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_8()!=null && !c.getc_8().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_8()) {
                        dh.insertData(weekdays[i]+"-08",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_9()!=null && !c.getc_9().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_9()) {
                        dh.insertData(weekdays[i]+"-09",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_10()!=null && !c.getc_10().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_10()) {
                        dh.insertData(weekdays[i]+"-10",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_11()!=null && !c.getc_11().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_11()) {
                        dh.insertData(weekdays[i]+"-11",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                if(c.getc_12()!=null && !c.getc_12().isEmpty()) {
                    //
                    for(CMessage cm:c.getc_12()) {
                        dh.insertData(weekdays[i]+"-12",cm.get_course_name(),cm.get_location(),cm.get_teacher(),cm.get_totalLength());
                    }
                }
                i+=1;
            }
        }
    }

}
