package com.ws.upc_schedule.data;
//用于储存SharedPreference数据库（表）名及key名
public class SP_name {
    //判断是否登录成功
    private static String Logged_SPname = "LoggedSP";
    private static String Logged_SPkey = "logged";
    public static String get_Logged_SPname(){
        return Logged_SPname;
    }
    public static String get_Logged_SPkey(){
        return Logged_SPkey;
    }
    //Cookies
    private static String CookiesSPname = "Cookies";
    private static String CookiesSPKey1 = "eai-sess";
    private static String CookiesSPKey2 = "UUkey";
    public static String get_CookiesSPname(){
        return CookiesSPname;
    }
    public static String get_CookiesSPKey1(){
        return CookiesSPKey1;
    }
    public static String get_CookiesSPKey2(){
        return CookiesSPKey2;
    }

}
