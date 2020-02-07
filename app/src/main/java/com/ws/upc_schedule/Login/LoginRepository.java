package com.ws.upc_schedule.Login;

import android.content.SharedPreferences;


import androidx.appcompat.app.AppCompatActivity;

import com.ws.upc_schedule.data.SP_name;


import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    public static boolean isLoggedIn(AppCompatActivity activity) {
        final SharedPreferences loggedSP = activity.getSharedPreferences(SP_name.get_Logged_SPname(), MODE_PRIVATE);
        return loggedSP.getBoolean(SP_name.get_Logged_SPkey(),false);
    }

    public void logout(AppCompatActivity activity) {
        final SharedPreferences loggedSP = activity.getSharedPreferences(SP_name.get_Logged_SPname(), MODE_PRIVATE);
        final SharedPreferences.Editor LoggedSP_edit = loggedSP.edit();
        LoggedSP_edit.clear();
        LoggedSP_edit.commit();
    }

    public static void loggedIn(AppCompatActivity activity){
        final SharedPreferences loggedSP = activity.getSharedPreferences(SP_name.get_Logged_SPname(), MODE_PRIVATE);
        final SharedPreferences.Editor LoggedSP_edit = loggedSP.edit();
        LoggedSP_edit.putBoolean("logged",true);
        LoggedSP_edit.commit();
        //清除cookies
    }

    public static void WriteCookies(String eai_sess,String UUkey,AppCompatActivity activity){
        final SharedPreferences loggedSP = activity.getSharedPreferences(SP_name.get_CookiesSPname(), MODE_PRIVATE);
        final SharedPreferences.Editor LoggedSP_edit = loggedSP.edit();
        LoggedSP_edit.putString(SP_name.get_CookiesSPKey1(),eai_sess);
        LoggedSP_edit.putString(SP_name.get_CookiesSPKey2(),UUkey);
        LoggedSP_edit.commit();
    }

    public static String[] ReadCookies(AppCompatActivity activity){
        String[] C = new String[2];
        final SharedPreferences loggedSP = activity.getSharedPreferences(SP_name.get_CookiesSPname(), MODE_PRIVATE);
        C[0] = loggedSP.getString(SP_name.get_CookiesSPKey1(),"");
        C[1] = loggedSP.getString(SP_name.get_CookiesSPKey2(),"");
        return C;
    }

}
