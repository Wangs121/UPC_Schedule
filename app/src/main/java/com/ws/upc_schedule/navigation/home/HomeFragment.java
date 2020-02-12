package com.ws.upc_schedule.navigation.home;

import android.content.Context;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ws.upc_schedule.R;
import com.ws.upc_schedule.data.Course;
import com.ws.upc_schedule.data.dhHelper;
import com.ws.upc_schedule.myDateUtils;

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

//    private List<Integer> MonthDayOfThisWeek ;
    private int currentWeek ;
    private int selectedWeek ;
    private List<Course> showWeekCourses;
    private String CurrentFirstWeekDaysMonthDay;
    private String selectedFirstWeekDaysMonthDay;
    private int dayofMonth ;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //数据初始化
        currentWeek = myDateUtils.getCurrentWeek();
        selectedWeek = currentWeek;
        //控件初始化
//        MonthDayOfThisWeek = myDateUtils.getCurrent7Days();
        //本周课程
        CurrentFirstWeekDaysMonthDay = myDateUtils.getCurrentFirstWeekDaysMonthDay();
        dayofMonth = Integer.parseInt(CurrentFirstWeekDaysMonthDay.substring(8));
        showWeekCourses = dhHelper.getCurrentWeekCourses();
//        Toast.makeText(getContext(),myDateUtils.getCurrentFirstWeekDaysMonthDay(),Toast.LENGTH_LONG).show();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mWeekView = (WeekView) root.findViewById(R.id.revolving_weekview);
        backButton = (Button) root.findViewById(R.id.back);
        nextWeekButton = (ImageButton) root.findViewById(R.id.nextWeek);
        previousWeekButton = (ImageButton) root.findViewById(R.id.previousWeek);
        term = (TextView) root.findViewById(R.id.term);
        YMdate = (TextView) root.findViewById(R.id.YMdate);
        week = (TextView) root.findViewById(R.id.week);
        backButton.setBackgroundColor(Color.TRANSPARENT);
        YMdate.setText(CurrentFirstWeekDaysMonthDay.substring(0,7));
        week.setText("第"+currentWeek+"周");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedWeek = currentWeek;
                selectedFirstWeekDaysMonthDay = myDateUtils.dd2YMD(0);
//                Log.d("date",selectedFirstWeekDaysMonthDay.substring(8));
                dayofMonth = Integer.parseInt(CurrentFirstWeekDaysMonthDay.substring(8));
                week.setText("第"+Integer.toString(selectedWeek)+"周");
                YMdate.setText(selectedFirstWeekDaysMonthDay.substring(0,7));
//                mWeekView.goToToday();
                mWeekView.goToDay(7);
            }
        });
        nextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedWeek<18){
                    selectedWeek +=1;
                }
                selectedFirstWeekDaysMonthDay = myDateUtils.dd2YMD((selectedWeek-currentWeek)*7);
                dayofMonth = Integer.parseInt(selectedFirstWeekDaysMonthDay.substring(8));
                week.setText("第"+Integer.toString(selectedWeek)+"周");
                YMdate.setText(selectedFirstWeekDaysMonthDay.substring(0,7));
//                mWeekView.goToToday();
                mWeekView.goToDay(7);
            }
        });
        previousWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedWeek >1){
                    selectedWeek -=1;
                }

                selectedFirstWeekDaysMonthDay = myDateUtils.dd2YMD((selectedWeek-currentWeek)*7);
                dayofMonth = Integer.parseInt(selectedFirstWeekDaysMonthDay.substring(8));
                week.setText("第"+Integer.toString(selectedWeek)+"周");
                YMdate.setText(selectedFirstWeekDaysMonthDay.substring(0,7));
//                mWeekView.goToToday();
                mWeekView.goToDay(7);
            }
        });
        mWeekView.setWeekViewLoader(new WeekView.WeekViewLoader() {

            @Override
            public List<? extends WeekViewEvent> onWeekViewLoad() {
                List<WeekViewEvent> events = new ArrayList<>();
                // Add some events
//
                if(selectedWeek==currentWeek){
                    showWeekCourses = dhHelper.getCurrentWeekCourses();
                }else{
                    showWeekCourses = dhHelper.get_one_weekCourse(selectedFirstWeekDaysMonthDay);
                }


                    for(Course c:showWeekCourses){
                        DayTime startTime = new DayTime(DayOfWeek.of(c.getDayofWeeks()),LocalTime.of(c.geStart2Time(),0));
                        DayTime endTime = new DayTime(startTime);
                        endTime.addHours(c.getLength());
                        WeekViewEvent event = new WeekViewEvent("0",
                                c.getName()+"\n"+c.getLocation()+"\n"+c.getTeacher(),startTime,endTime);
                        event.setColor(c.getColor());
                        events.add(event);
                    }
//                int MofD = Integer.parseInt(selectedFirstWeekDaysMonthDay.substring(8));
//                    Toast.makeText(getContext(),MofD+"",Toast.LENGTH_SHORT).show();
                return events;
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
                        +"\n" +(dayofMonth+date%7);
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
//    private List<WeekViewEvent> myEventLoader(){
//        List<WeekViewEvent> events = new ArrayList<>();
//        // Add some events
//        if(currentWeek==selectedWeek){
//            DayTime startTime = new DayTime(DayOfWeek.SATURDAY, LocalTime.of(8,0));
//            DayTime endTime = new DayTime(DayOfWeek.SATURDAY, LocalTime.of(10,0));
//            WeekViewEvent event = new WeekViewEvent("0","互换性\n西朗\n呸呸呸",startTime,endTime);
//            event.setColor(Color.argb(255,255,0,0));
//            events.add(event);
//
//        }else{
//            DayTime startTime = new DayTime(DayOfWeek.SATURDAY, LocalTime.of(8,0));
//            DayTime endTime = new DayTime(DayOfWeek.SATURDAY, LocalTime.of(10,0));
//            WeekViewEvent event = new WeekViewEvent("1","aaa\nbbb\nccc",startTime,endTime);
//            event.setColor(Color.argb(255,0,255,0));
//            events.add(event);
//        }
//        return events;
//    }
}