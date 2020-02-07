package com.ws.upc_schedule.data;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Crawler extends AsyncTask<String,Document, Void> {

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
        Decoder.decoder(documents[0]);
    }
}
