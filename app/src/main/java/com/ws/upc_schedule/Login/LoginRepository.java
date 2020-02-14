package com.ws.upc_schedule.Login;

import android.content.Context;
import android.content.SharedPreferences;

import com.ws.upc_schedule.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    public static String getFirstDayofTerm(Context context) {
        final SharedPreferences termSP = context.getSharedPreferences(String.valueOf(R.string.FirstDayofTerm), MODE_PRIVATE);
        return termSP.getString(String.valueOf(R.string.FirstDayofTermKey), "2019-09-09");
    }

    public static void setFirstDayofTerm(Context context, String term) {
        final SharedPreferences termSP = context.getSharedPreferences(String.valueOf(R.string.FirstDayofTerm), MODE_PRIVATE);
        final SharedPreferences.Editor TermdSP_edit = termSP.edit();
        TermdSP_edit.putString(String.valueOf(R.string.FirstDayofTermKey), term);
        TermdSP_edit.commit();
    }

    public static String getTerm(Context context) {
        final SharedPreferences termSP = context.getSharedPreferences(String.valueOf(R.string.Term), MODE_PRIVATE);
        return termSP.getString(String.valueOf(R.string.TermKey), null);
    }

    public static void clearTerm(Context context) {
        final SharedPreferences termSP = context.getSharedPreferences(String.valueOf(R.string.Term), MODE_PRIVATE);
        final SharedPreferences.Editor TermdSP_edit = termSP.edit();
        TermdSP_edit.clear();
        TermdSP_edit.commit();
    }
    public static void setTerm(Context context, String term) {
        final SharedPreferences termSP = context.getSharedPreferences(String.valueOf(R.string.Term), MODE_PRIVATE);
        final SharedPreferences.Editor TermdSP_edit = termSP.edit();
        TermdSP_edit.putString(String.valueOf(R.string.TermKey), term);
        TermdSP_edit.commit();
    }

    public static boolean isLoggedIn(Context context) {
        final SharedPreferences loggedSP = context.getSharedPreferences(String.valueOf(R.string.Logged_SPname), MODE_PRIVATE);
        return loggedSP.getBoolean(String.valueOf(R.string.Logged_SPkey), false);
    }

    public static void logout(Context context) {
        final SharedPreferences loggedSP = context.getSharedPreferences(String.valueOf(R.string.Logged_SPname), MODE_PRIVATE);
        final SharedPreferences.Editor LoggedSP_edit = loggedSP.edit();
        LoggedSP_edit.clear();
        LoggedSP_edit.commit();
    }

    public static void loggedIn(Context context) {
        final SharedPreferences loggedSP = context.getSharedPreferences(String.valueOf(R.string.Logged_SPname), MODE_PRIVATE);
        final SharedPreferences.Editor LoggedSP_edit = loggedSP.edit();
        LoggedSP_edit.putBoolean(String.valueOf(R.string.Logged_SPkey), true);
        LoggedSP_edit.commit();
        //清除cookies
    }

    public static void WriteCookies(String eai_sess, String UUkey, Context context) {
        final SharedPreferences loggedSP = context.getSharedPreferences(String.valueOf(R.string.Cookies_SPname), MODE_PRIVATE);
        final SharedPreferences.Editor LoggedSP_edit = loggedSP.edit();
        LoggedSP_edit.putString(String.valueOf(R.string.Cookies_SPkey1), eai_sess);
        LoggedSP_edit.putString(String.valueOf(R.string.Cookies_SPkey2), UUkey);
        LoggedSP_edit.commit();
    }

    public static String[] ReadCookies(Context context) {
        String[] C = new String[2];
        final SharedPreferences loggedSP = context.getSharedPreferences(String.valueOf(R.string.Cookies_SPname), MODE_PRIVATE);
        C[0] = loggedSP.getString(String.valueOf(R.string.Cookies_SPkey1), "");
        C[1] = loggedSP.getString(String.valueOf(R.string.Cookies_SPkey2), "");
        return C;
    }


}
