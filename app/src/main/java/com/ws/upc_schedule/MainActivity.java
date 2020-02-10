package com.ws.upc_schedule;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.ws.upc_schedule.Login.LoginRepository;
import com.ws.upc_schedule.data.Crawler;
import com.ws.upc_schedule.data.dhHelper;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断是否登录过
        if( !LoginRepository.isLoggedIn(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),"未登录",Toast.LENGTH_SHORT).show();
            Intent login = new Intent(MainActivity.this, LoginActivity.class);

            startActivity(login);
            finish();
        }else{
            //初始化数据库
            dhHelper.cdbh_init(getApplicationContext());
            //navigation 初始化
            setContentView(R.layout.activity_main1);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"ccc",Toast.LENGTH_SHORT).show();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);

            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_setting,
                    R.id.nav_feedback, R.id.nav_about)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

        }

    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    @Override
//    protected void onResume(){
//        super.onResume();
//        if(LoginRepository.isLoggedIn(getApplicationContext()) && dhHelper.get_all_data().getCount()<=0){
//            Toast.makeText(getApplicationContext(),"数据库为空，正在自动更新",Toast.LENGTH_SHORT).show();
//            new Crawler().execute(getApplicationContext());
//        }
//    }

}
