package com.ws.upc_schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.ws.upc_schedule.Login.LoginRepository;
import com.ws.upc_schedule.data.Crawler;
import com.ws.upc_schedule.data.SP_name;

public class MainActivity extends AppCompatActivity {

//    Button but;
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
        Log.d("Cookie","gonna print cookies");
        String[] c = LoginRepository.ReadCookies(this);
        new Crawler().execute(c);
//        Log.d("Cookie",c[0]);
//        Log.d("Cookie",c[1]);
    }

}
