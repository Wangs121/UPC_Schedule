package com.ws.upc_schedule.navigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ws.upc_schedule.Login.LoginDateUtils;
import com.ws.upc_schedule.Login.LoginParser;
import com.ws.upc_schedule.Login.LoginRepository;
import com.ws.upc_schedule.Login.LogindbHelper;
import com.ws.upc_schedule.LoginActivity;
import com.ws.upc_schedule.MainActivity;
import com.ws.upc_schedule.R;
import com.ws.upc_schedule.data.ClassesContainer;
import com.ws.upc_schedule.data.dbHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SettingFragment extends Fragment {

    private ProgressBar progressBar;

    private List<String> courses = new ArrayList<>();
    private String year;
    private String term = null;
    private String[] cookies;
    private String firstDayofTerm = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        final Button logout = root.findViewById(R.id.Logout);
        final Button update = root.findViewById(R.id.update);
        final Button show_all = root.findViewById(R.id.show);
        progressBar = root.findViewById(R.id.progressBar);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.clear();
                dbHelper.close();
                LoginRepository.logout(getContext());
                LoginRepository.clearTerm(getContext());
                LoginRepository.clearCookies(getContext());
                LoginRepository.clearFirstDayofTerm(getContext());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "正在更新数据", Toast.LENGTH_SHORT).show();
                dbHelper.clear();
                year = LoginDateUtils.getYear();
                cookies = LoginRepository.ReadCookies(getContext());
                LoginRepository.clearTerm(getContext());
                new updateUserInf().execute();
            }
        });
        return root;
    }

    public void viewData() {
        Cursor data = dbHelper.getAllData();
        StringBuffer stringBuffer = new StringBuffer();
        while (data.moveToNext()) {
            stringBuffer.append("\nINDEX: " + data.getString(0));
            stringBuffer.append("\nNAME: " + data.getString(1));
            stringBuffer.append("\nLOCATION: " + data.getString(2));
            stringBuffer.append("\nTEACHER: " + data.getString(3));
            stringBuffer.append("\nLENGTH: " + data.getString(4));
            stringBuffer.append("\n ");
        }
        dialog("Data", stringBuffer.toString());
    }

    public void dialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    //获得学期
    public class updateUserInf extends AsyncTask<Void, Void, Document> {

        @Override
        protected Document doInBackground(Void... voids) {
            String url = "https://app.upc.edu.cn/timetable/wap/default/get-datatmp";
            String type = "2";
            Document mes;
            try {
                mes = Jsoup.connect(url)
                        .data("year", year)
                        .data("term", "2")
                        .data("week", "1")
                        .data("type", type)
                        .cookie("eai-sess", cookies[0])
                        .cookie("UUkey", cookies[1])
                        .ignoreContentType(true)
                        .post();
                Log.d("Login", "学期试探结果");
                Log.d("Login", mes.toString());
                return mes;
            } catch (Exception e) {
                return null;
            }
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Document result) {
            super.onPostExecute(result);

            if (result instanceof Document) {
                term = LoginParser.findTerm(result);
                if (term.equals("0")) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Cookies失效，请重新登录", Toast.LENGTH_SHORT).show();
                } else {
                    LoginRepository.setTerm(getContext(), term);
                    new init_Classes().execute();
                }
                Log.d("Login", "判断出学期为：" + term);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "连接失败", Toast.LENGTH_SHORT).show();
                Log.d("Login", "连接失败");
            }
        }
    }

    //初始化课程数据库
    public class init_Classes extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... strings) {
            String url = "https://app.upc.edu.cn/timetable/wap/default/get-datatmp";
            String type = "2";
            Document mes;
            String p;
            try {
                Log.d("Login", "开始获取数据");
                for (int i = 1; i < 19; i++) {
                    mes = Jsoup.connect(url)
                            .data("year", year)
                            .data("term", term)
                            .data("week", Integer.toString(i))
                            .data("type", type)
                            .cookie("eai-sess", cookies[0])
                            .cookie("UUkey", cookies[1])
                            .ignoreContentType(true)
                            .post();
//                Log.d("data",mes.toString());
                    p = mes.body().text();
                    if (p.contains("操作成功")) {
//                        publishProgress(new String[]{p,String.valueOf(i)});
                        publishProgress(p);
                    } else {
                        return null;
                    }
                }
                return null;
            } catch (Exception e) {
//            Log.d("data",e.toString());
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(String... strings) {
            super.onProgressUpdate(strings);
//            Parser.parser(documents[0]);
//            LoginParser.parse(strings,getApplicationContext(),logindbHelper);
            courses.add(strings[0]);
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //结束此activity
//            setResult(Activity.RESULT_OK);
            int week = 1;
            for (String course : courses) {
                parse(course, week);
                week++;
            }
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "数据更新完成，重启软件生效", Toast.LENGTH_SHORT).show();

        }
    }

    public void parse(String string, int week) {
        //解析
        Gson g = new Gson();
//        String con = doc.body().text();
//        Log.d("data",con);
        JsonObject table;
        JsonElement d;
        String buffer;
        table = g.fromJson(string, JsonObject.class);//转换为JsonObject
        d = table.get("d");
        buffer = g.toJson(d);//转换为String
        table = g.fromJson(buffer, JsonObject.class);

        d = table.get("classes");
        buffer = g.toJson(d);

        Type collectionType = new TypeToken<Collection<ClassesContainer>>() {
        }.getType();
        Collection<ClassesContainer> classes = g.fromJson(buffer, collectionType);
        String weeks;
        if (week < 10) {
            weeks = "0" + week + "-";
        } else {
            weeks = week + "-";
        }
        if (firstDayofTerm == null) {
            JsonObject t;
            JsonElement w;
            String b;
            String[] weekdays;
            t = g.fromJson(string, JsonObject.class);//转换为JsonObject
            d = t.get("d");
            b = g.toJson(d);//转换为String
            t = g.fromJson(b, JsonObject.class);
            w = t.get("weekdays");
            weekdays = g.fromJson(w, String[].class);//得到日期
            firstDayofTerm = weekdays[0];
            LoginRepository.setFirstDayofTerm(getContext(), firstDayofTerm);
        }
        for (ClassesContainer c : classes) {
            //第一次遍历为周日，随之增加
            if (c.getc_1() != null && !c.getc_1().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_1()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-01", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_2() != null && !c.getc_2().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_2()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-02", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_3() != null && !c.getc_3().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_3()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-03", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_4() != null && !c.getc_4().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_4()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-04", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_5() != null && !c.getc_5().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_5()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-05", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_6() != null && !c.getc_6().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_6()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-06", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_7() != null && !c.getc_7().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_7()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-07", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_8() != null && !c.getc_8().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_8()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-08", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_9() != null && !c.getc_9().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_9()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-09", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_10() != null && !c.getc_10().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_10()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-10", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_11() != null && !c.getc_11().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_11()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-11", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
            if (c.getc_12() != null && !c.getc_12().isEmpty()) {
                //
                for (ClassesContainer.CMessage cm : c.getc_12()) {
                    dbHelper.insert(weeks + cm.get_weekday() + "-12", cm.get_course_name(), cm.get_location(), cm.get_teacher(), cm.get_totalLength());
                }
            }
        }
    }
}