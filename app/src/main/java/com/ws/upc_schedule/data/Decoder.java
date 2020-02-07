package com.ws.upc_schedule.data;

import android.util.Log;

import org.jsoup.nodes.Document;

public class Decoder {

    public static void decoder(Document doc){
//        Log.d("data",doc.toString());
        String con = doc.body().text().toString();
//        Log.d("data",con);

    }
}
