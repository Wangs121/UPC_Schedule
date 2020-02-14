package com.ws.upc_schedule.navigation.home;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.ws.upc_schedule.Login.LoginRepository;
import com.ws.upc_schedule.R;
import com.ws.upc_schedule.data.Course;
import com.ws.upc_schedule.data.dateUtils;
import com.ws.upc_schedule.data.dbHelper;
import com.ws.upc_schedule.widget.WidgetUtils;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.jlurena.revolvingweekview.DayTime;
import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;


public class HomeFragment extends Fragment{

    private WeekView mWeekView;
    private Button backButton;
    private TextView YMdate;
    private TextView term;
    private TextView week;
    private ImageButton nextWeekButton;
    private ImageButton previousWeekButton;

    private int currentWeek ;
    private int selectedWeek;

    private String currentYMD;
    private String selectedYMD;
    private int[] weekMonthDays;
    private List<Course> courses;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //初始化数据库
        dbHelper.initilize(getContext());
//        更新小部件
        WidgetUtils.updateWidget(getContext());
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mWeekView = (WeekView) root.findViewById(R.id.revolving_weekview);
        backButton = (Button) root.findViewById(R.id.back);
        nextWeekButton = (ImageButton) root.findViewById(R.id.nextWeek);
        previousWeekButton = (ImageButton) root.findViewById(R.id.previousWeek);
        term = (TextView) root.findViewById(R.id.term);
        YMdate = (TextView) root.findViewById(R.id.YMdate);
        week = (TextView) root.findViewById(R.id.week);
        backButton.setBackgroundColor(Color.TRANSPARENT);

        currentWeek = dateUtils.getCurrentWeek();
        selectedWeek = currentWeek;
        currentYMD = dateUtils.getFirstDayofWeek(currentWeek);
        selectedYMD = currentYMD;
        weekMonthDays = dateUtils.getWeekMonthDays(currentYMD);
        courses = dbHelper.getCurrentCourses();


        term.setText(dateUtils.getStuYear());
        week.setText("第"+currentWeek+"周");
        YMdate.setText(currentYMD.substring(0,7));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedWeek = currentWeek;
                selectedYMD = currentYMD;
                weekMonthDays = dateUtils.getWeekMonthDays(currentYMD);
                courses = dbHelper.getCurrentCourses();
                refresh();
            }
        });
        nextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedWeek<18){
                    selectedWeek +=1;
                }
                selectedYMD = dateUtils.getFirstDayofWeek(selectedWeek);
                weekMonthDays = dateUtils.getWeekMonthDays(selectedYMD);
                courses = dbHelper.getOneWeekCoueses(selectedWeek);
                refresh();
            }
        });
        previousWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedWeek>1){
                    selectedWeek -=1;
                }
                selectedYMD = dateUtils.getFirstDayofWeek(selectedWeek);
                weekMonthDays = dateUtils.getWeekMonthDays(selectedYMD);
                courses = dbHelper.getOneWeekCoueses(selectedWeek);
                refresh();
            }
        });
        mWeekView.setWeekViewLoader(new WeekView.WeekViewLoader() {

            @Override
            public List<? extends WeekViewEvent> onWeekViewLoad() {
                List<WeekViewEvent> events = new ArrayList<>();
//                if(selectedWeek==currentWeek){
//                    showWeekCourses = dhHelper.getCurrentWeekCourses();
//                }else{
//                    showWeekCourses = dhHelper.get_one_weekCourse(selectedFirstWeekDaysMonthDay);
//                }
//

                for(Course c:courses){
                    int day = c.getDay();
                    if (day==0){
                        day=7;
                    }
//                    Log.d("Courses","week before"+c.getDay());
//                    Log.d("Courses","week after"+week);
                    DayTime startTime = new DayTime(DayOfWeek.of(day), LocalTime.of(c.geStart2Time(),0));
                    DayTime endTime = new DayTime(startTime);
                    endTime.addHours(c.getLength());
                    WeekViewEvent event = new WeekViewEvent("0",
                            c.getName()+"\n\n"+c.getLocation()+"\n"+c.getTeacher(),startTime,endTime);
                    event.setColor(c.getColor());
                    events.add(event);
                }

                return events;
            }
        });
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                dialog(event.getName());
            }
        });
        setupDateTimeInterpreter();
        return root;
    }



    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     */
    private void setupDateTimeInterpreter() {
        mWeekView.setDayTimeInterpreter(new WeekView.DayTimeInterpreter() {
            @Override
            public String interpretDay(int date) {
//                date = DayOfWeek.getValue();

                String show = DayOfWeek.of(date).getDisplayName(TextStyle.SHORT, Locale.getDefault())
                        +"\n" +(weekMonthDays[date%7]);
                return show;
            }
            @Override
            public String interpretTime(int hour, int minutes) {
                if (hour > 11) {
                    return (hour == 12 ? "12"  : (hour - 12) ) + " PM";
                } else {
                    if (hour == 0) {
                        return "12" +" AM";
                    } else {
                        return hour + " AM";
                    }
                }
//                return hour+" ";
            }
        });
    }
    public void dialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        builder.setTitle("课程详情");
        builder.setCancelable(true);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void refresh(){
        mWeekView.goToDay(7);
        this.week.setText("第"+selectedWeek+"周");
        YMdate.setText(selectedYMD.substring(0,7));
    }
}