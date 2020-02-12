package com.ws.upc_schedule.navigation.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ws.upc_schedule.Login.LoginRepository;
import com.ws.upc_schedule.LoginActivity;
import com.ws.upc_schedule.R;
import com.ws.upc_schedule.data.Crawler;
import com.ws.upc_schedule.data.Parser;
import com.ws.upc_schedule.data.dhHelper;
import com.ws.upc_schedule.myDateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class SettingFragment extends Fragment {

    private SettingViewModel settingViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dhHelper.cdbh_init(getContext());
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        final Button logout = root.findViewById(R.id.Logout);
        final Button update = root.findViewById(R.id.update);
        final Button clear = root.findViewById(R.id.clear);
        final Button show_all = root.findViewById(R.id.show);
        final TextView textView = root.findViewById(R.id.CookieView);
//        String show = myDateUtils.getCurrentYMD()+"\n"+myDateUtils.getFirstDayofTerm();
//        textView.setText(show);
//for debug-------------------
//        textView.setText(myDateUtils.getCurrentFirstWeekDaysMonthDay());
        textView.setText("FirstDayofTerm\t"+myDateUtils.getFirstDayofTerm()+"\n"+
                "CurrentYDM\t"+myDateUtils.getCurrentYMD()+"\n"+
                "CSundayYDM"+myDateUtils.getCurrentFirstWeekDaysMonthDay()+"\n");
// -------------------------------------
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRepository.logout(getContext());
                dhHelper.cdbh_empty();
                Intent login = new Intent(getContext(), LoginActivity.class);
                startActivity(login);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhHelper.cdbh_empty();
                new Crawler().execute(getContext());
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dhHelper.cdbh_empty();
            }
        });
        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData();
            }
        });
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        settingViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
    public void viewData(){
        Cursor data = dhHelper.get_all_data();
        StringBuffer stringBuffer = new StringBuffer();
        while (data.moveToNext()){
            stringBuffer.append("\nINDEX: " + data.getString(0));
            stringBuffer.append("\nNAME: " + data.getString(1));
            stringBuffer.append("\nLOCATION: " + data.getString(2));
            stringBuffer.append("\nTEACHER: " + data.getString(3));
            stringBuffer.append("\nTOTALLENGTH: " + data.getString(4));
//            stringBuffer.append("\nPassword: " + data.getString(2) + "\n");
        }
        dialog("Data", stringBuffer.toString());
    }

    public void dialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}