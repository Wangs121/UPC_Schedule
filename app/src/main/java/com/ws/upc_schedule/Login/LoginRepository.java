package com.ws.upc_schedule.Login;

import android.content.SharedPreferences;
import android.util.Log;

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
    }


    public static Result<String> login_test(String username, String password) {

        if (username.equals("ws") ) {
            return new Result.Success<>("Success!");
        }
        return new Result.Error(new IOException("Error logging in"));
    }


}
