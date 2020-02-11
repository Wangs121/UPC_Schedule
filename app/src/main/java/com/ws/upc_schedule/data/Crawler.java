package com.ws.upc_schedule.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ws.upc_schedule.Login.LoginRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Crawler extends AsyncTask<Context, Document, Void> {

    @Override
    protected Void doInBackground(Context... contexts) {
        String[] cookies = LoginRepository.ReadCookies(contexts[0]);
        String url = "https://app.upc.edu.cn/timetable/wap/default/get-datatmp";
        String year = "2019-2020";
        String term = "1";
        String type = "2";
        Document mes;
//            Toast.makeText(contexts[0],"更新中",Toast.LENGTH_SHORT).show();
        Log.d("data","start");
        try{
            Log.d("data","开始获取数据");
            for(int i=1;i<19;i++){
                mes = Jsoup.connect(url)
                        .data("year",year)
                        .data("term",term)
                        .data("week",Integer.toString(i))
                        .data("type",type)
                        .cookie("eai-sess",cookies[0])
                        .cookie("UUkey",cookies[1])
                        .ignoreContentType(true)
                        .post();
                Log.d("data",mes.toString());
                if(mes.body().text().contains("操作成功")) {
                    publishProgress(mes);
                }else{
//                    Toast.makeText(contexts[0],"获取数据失败，请重新登录",Toast.LENGTH_SHORT).show();
                    return null;
                }
//            Log.d("data",mes.body().toString());
            }
//            String test = "\n" +
//                    " {\"e\":0,\"m\":\"操作成功\",\"d\":{\"classes\":[{\"c_1\":[],\"c_2\":[],\"c_3\":[],\"c_4\":[],\"c_5\":[],\"c_6\":[],\"c_7\":[],\"c_8\":[],\"c_9\":[],\"c_10\":[],\"c_11\":[],\"c_12\":[],\"c_13\":[],\"c_14\":[]},{\"c_1\":[],\"c_2\":[],\"c_3\":[],\"c_4\":[],\"c_5\":[],\"c_6\":[],\"c_7\":[{\"course_id\":\"201920201004299\",\"course_name\":\"互换性与技术测量基础\",\"location\":\"西廊102\",\"weekday\":\"1\",\"lessons\":\"0708\",\"teacher\":\"秦冬黎\",\"week\":\"9-14\",\"week_type\":\"0\",\"course_time\":\"16:10~18:00\",\"lessArr\":[7,8],\"order\":7,\"totalLength\":2}],\"c_9\":[{\"course_id\":\"201920201003963\",\"course_name\":\"人机工程学\",\"location\":\"南教209\",\"weekday\":\"1\",\"lessons\":\"0910\",\"teacher\":\"王玉新\",\"week\":\"10-17\",\"week_type\":\"0\",\"course_time\":\"19:00~20:50\",\"lessArr\":[9,10],\"order\":9,\"totalLength\":2}],\"c_11\":[],\"c_12\":[],\"c_13\":[],\"c_14\":[]},{\"c_1\":[],\"c_2\":[],\"c_3\":[{\"course_id\":\"201920201005524\",\"course_name\":\"汽车发动机原理\",\"location\":\"南堂215\",\"weekday\":\"2\",\"lessons\":\"0304\",\"teacher\":\"崔运静\",\"week\":\"9-16\",\"week_type\":\"0\",\"course_time\":\"10:10~12:00\",\"lessArr\":[3,4],\"order\":3,\"totalLength\":2}],\"c_5\":[],\"c_6\":[],\"c_7\":[],\"c_8\":[],\"c_9\":[],\"c_10\":[],\"c_11\":[],\"c_12\":[],\"c_13\":[],\"c_14\":[]},{\"c_1\":[{\"course_id\":\"201920201000348\",\"course_name\":\"汽车液压与气动控制\",\"location\":\"南教501\",\"weekday\":\"3\",\"lessons\":\"0102\",\"teacher\":\"刘峰\",\"week\":\"4-15\",\"week_type\":\"0\",\"course_time\":\"8:00~9:50\",\"lessArr\":[1,2],\"order\":1,\"totalLength\":2}],\"c_3\":[],\"c_4\":[],\"c_5\":[],\"c_6\":[],\"c_7\":[{\"course_id\":\"201920201004299\",\"course_name\":\"互换性与技术测量基础\",\"location\":\"西廊102\",\"weekday\":\"3\",\"lessons\":\"0708\",\"teacher\":\"秦冬黎\",\"week\":\"9-14\",\"week_type\":\"0\",\"course_time\":\"16:10~18:00\",\"lessArr\":[7,8],\"order\":7,\"totalLength\":2}],\"c_9\":[{\"course_id\":\"201920201003963\",\"course_name\":\"人机工程学\",\"location\":\"南教209\",\"weekday\":\"3\",\"lessons\":\"0910\",\"teacher\":\"王玉新\",\"week\":\"10-17\",\"week_type\":\"0\",\"course_time\":\"19:00~20:50\",\"lessArr\":[9,10],\"order\":9,\"totalLength\":2}],\"c_11\":[],\"c_12\":[],\"c_13\":[],\"c_14\":[]},{\"c_1\":[],\"c_2\":[],\"c_3\":[],\"c_4\":[],\"c_5\":[{\"course_id\":\"201920201005524\",\"course_name\":\"汽车发动机原理\",\"location\":\"南堂215\",\"weekday\":\"4\",\"lessons\":\"0506\",\"teacher\":\"崔运静\",\"week\":\"9-14\",\"week_type\":\"0\",\"course_time\":\"14:00~15:50\",\"lessArr\":[5,6],\"order\":5,\"totalLength\":2}],\"c_7\":[],\"c_8\":[],\"c_9\":[],\"c_10\":[],\"c_11\":[],\"c_12\":[],\"c_13\":[],\"c_14\":[]},{\"c_1\":[{\"course_id\":\"201920201000348\",\"course_name\":\"汽车液压与气动控制\",\"location\":\"南教501\",\"weekday\":\"5\",\"lessons\":\"0102\",\"teacher\":\"刘峰\",\"week\":\"7-15\",\"week_type\":\"0\",\"course_time\":\"8:00~9:50\",\"lessArr\":[1,2],\"order\":1,\"totalLength\":2}],\"c_3\":[],\"c_4\":[],\"c_5\":[],\"c_6\":[],\"c_7\":[],\"c_8\":[],\"c_9\":[],\"c_10\":[],\"c_11\":[],\"c_12\":[],\"c_13\":[],\"c_14\":[]},{\"c_1\":[],\"c_2\":[],\"c_3\":[],\"c_4\":[],\"c_5\":[],\"c_6\":[],\"c_7\":[],\"c_8\":[],\"c_9\":[],\"c_10\":[],\"c_11\":[],\"c_12\":[],\"c_13\":[],\"c_14\":[]}],\"weekdays\":[\"2019-12-08\",\"2019-12-09\",\"2019-12-10\",\"2019-12-11\",\"2019-12-12\",\"2019-12-13\",\"2019-12-14\"]}}";
//            mes = Jsoup.parse(test);
//            publishProgress(mes);
            return null;
        }catch(Exception e){
//            Log.d("data",e.toString());
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Document... documents){
        super.onProgressUpdate(documents);
        Parser.parser(documents[0]);
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
//        Toast.makeText(result,"更新成功",Toast.LENGTH_SHORT).show();
//
    }
}
