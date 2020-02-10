package com.ws.upc_schedule.navigation.home;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ws.upc_schedule.R;
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
    private Button back_button;
    private TextView years;
    private TextView term;
    private TextView week;

    private List<Integer> title_month_of_day ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        title_month_of_day = myDateUtils.get_title_day();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mWeekView = (WeekView) root.findViewById(R.id.revolving_weekview);
        // Set an WeekViewLoader to draw the events on load.
        back_button = root.findViewById(R.id.back);
        back_button.setBackgroundColor(Color.TRANSPARENT);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();
            }
        });

        mWeekView.setWeekViewLoader(new WeekView.WeekViewLoader() {

            @Override
            public List<? extends WeekViewEvent> onWeekViewLoad() {
                List<WeekViewEvent> events = new ArrayList<>();
                // Add some events
                DayTime startTime = new DayTime(DayOfWeek.SATURDAY, LocalTime.of(8,0));
                DayTime endTime = new DayTime(DayOfWeek.SATURDAY, LocalTime.of(10,0));
                WeekViewEvent event = new WeekViewEvent("0","互换性\n西朗\n呸呸呸",startTime,endTime);
                event.setColor(Color.argb(255,1,0,0));
                events.add(event);
                return events;
            }
        });
        mWeekView.setHorizontalFlingEnabled(false);
//        mWeekView.setScrollListener(new WeekView.ScrollListener(){
//            @Override
//            public void onFirstVisibleDayChanged(DayOfWeek newFirstVisibleDay, DayOfWeek oldFirstVisibleDay){
//
//            }
//        });
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
                String show = DayOfWeek.of(date).getDisplayName(TextStyle.SHORT, Locale.getDefault());
                show += "\n";
                show += Integer.toString(title_month_of_day.get(date%7));
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
}