package com.ws.upc_schedule.Login;

import android.content.Context;

import com.ws.upc_schedule.data.ClassesDataBase;

public class LogindbHelper {
    private ClassesDataBase classesDataBase;

    public LogindbHelper(Context context) {
        this.classesDataBase = new ClassesDataBase(context);
    }

    private void insertData(String index, String name, String location, String teacher, int totallength) {
        classesDataBase.insertData(index, name, location, teacher, totallength);
    }
}
