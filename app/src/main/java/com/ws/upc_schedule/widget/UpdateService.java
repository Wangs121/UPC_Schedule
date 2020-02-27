package com.ws.upc_schedule.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.RequiresApi;

import com.ws.upc_schedule.R;
import com.ws.upc_schedule.data.ClassesDataBase;
import com.ws.upc_schedule.data.Course;
import com.ws.upc_schedule.data.dateUtils;

import java.util.ArrayList;
import java.util.List;

public class UpdateService extends RemoteViewsService {


    private List<Course> mCourses;
    private int maxNodeSize = 12;
//    private int mCurrentWeek = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initData(Context context) {
        //添加课程
//        int week = dateUtils.getCurrentWeek();
            //        if(week == buffer.getCurrentWeek()){
        Log.d("widget","get Courses");
            mCourses = getCurrentCoueses(context);
        Log.d("widget",mCourses.toString());
//        }else{
//            mCourses = buffer.getNextCourses();
//        }



    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class ListRemoteViewsFactory implements RemoteViewsFactory {

        private final Context mContext;

        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;

            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
        }

        @Override
        public void onCreate() {
//            LogUtil.e(UpdateJobService.class, "onCreate");
            WidgetUtils.startWidgetJobService(getApplicationContext());
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 1;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public RemoteViews getViewAt(int position) {
//            LogUtil.e(this, "getViewAt");
            initData(mContext);

            final RemoteViews bigRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.list_demo_item);
            bigRemoteViews.removeAllViews(R.id.item_node_group);
            bigRemoteViews.removeAllViews(R.id.item_weekday_day_1);
            bigRemoteViews.removeAllViews(R.id.item_weekday_day_2);
            bigRemoteViews.removeAllViews(R.id.item_weekday_day_3);
            bigRemoteViews.removeAllViews(R.id.item_weekday_day_4);
            bigRemoteViews.removeAllViews(R.id.item_weekday_day_5);
            bigRemoteViews.removeAllViews(R.id.item_weekday_day_6);
            bigRemoteViews.removeAllViews(R.id.item_weekday_day_7);

//            Intent intent = new Intent(mContext, CourseActivity.class);

//            bigRemoteViews.setOnClickFillInIntent(R.id.item_weekday_layout, intent);

            for (int i = 1; i <= maxNodeSize; i++) {
                RemoteViews nodeRemoteViews = new RemoteViews(getPackageName(), R.layout.widget_node);
                nodeRemoteViews.setTextViewText(R.id.widget_box_0, i + "");
                bigRemoteViews.addView(R.id.item_node_group, nodeRemoteViews);
            }

            for (int row = 1; row <= 7; row++) {
                for (int col = 1; col <= maxNodeSize; col++) {
                    Course course = getCourseByRowCol(row, col);
                    RemoteViews dayRemoteViews = null;
                    if (course == null) {
                        dayRemoteViews = new RemoteViews(getPackageName(), R.layout.widget_cell_1);
                        dayRemoteViews.setTextViewText(R.id.widget_cell_1, "");
                    } else {
                        col = col + course.getLength() - 1;
                        int layout = -1;
                        int id = -1;

                        switch (course.getLength()) {
                            case 1:
                                layout = R.layout.widget_cell_1;
                                id = R.id.widget_cell_1;
                                break;
                            case 2:
                                layout = R.layout.widget_cell_2;
                                id = R.id.widget_cell_2;
                                break;
                            case 3:
                                layout = R.layout.widget_cell_3;
                                id = R.id.widget_cell_3;
                                break;
                            case 4:
                                layout = R.layout.widget_cell_4;
                                id = R.id.widget_cell_4;
                                break;
                            case 5:
                                layout = R.layout.widget_cell_5;
                                id = R.id.widget_cell_5;
                                break;
                            case 6:
                                layout = R.layout.widget_cell_6;
                                id = R.id.widget_cell_6;
                                break;
                            case 7:
                                layout = R.layout.widget_cell_7;
                                id = R.id.widget_cell_7;
                                break;
                            case 8:
                                layout = R.layout.widget_cell_8;
                                id = R.id.widget_cell_8;
                                break;
                            case 9:
                                layout = R.layout.widget_cell_9;
                                id = R.id.widget_cell_9;
                                break;
                            case 10:
                                layout = R.layout.widget_cell_10;
                                id = R.id.widget_cell_10;
                                break;
                            case 11:
                                layout = R.layout.widget_cell_11;
                                id = R.id.widget_cell_11;
                                break;
                            case 12:
                                layout = R.layout.widget_cell_12;
                                id = R.id.widget_cell_12;
                                break;
                            default:
                                break;
                        }

                        if (id != -1) {
                            dayRemoteViews = new RemoteViews(getPackageName(), layout);
                            dayRemoteViews.setTextViewText(id, course.getName() + "\n" + course.getLocation());

//                            if (course.getActiveStatus()) {
                            dayRemoteViews.setInt(id, "setBackgroundColor", course.getColor());
                            dayRemoteViews.setInt(id, "setTextColor", 0xFFFFFFFF);
//                            } else {
//                                dayRemoteViews.setInt(id, "setBackgroundColor", 0xFFE3EEF5);
//                                dayRemoteViews.setInt(id, "setTextColor", 0xFFbadac9);
//                            }
                        }
                    }

                    switch (row) {
                        case 1:
                            bigRemoteViews.addView(R.id.item_weekday_day_1, dayRemoteViews);
                            break;
                        case 2:
                            bigRemoteViews.addView(R.id.item_weekday_day_2, dayRemoteViews);
                            break;
                        case 3:
                            bigRemoteViews.addView(R.id.item_weekday_day_3, dayRemoteViews);
                            break;
                        case 4:
                            bigRemoteViews.addView(R.id.item_weekday_day_4, dayRemoteViews);
                            break;
                        case 5:
                            bigRemoteViews.addView(R.id.item_weekday_day_5, dayRemoteViews);
                            break;
                        case 6:
                            bigRemoteViews.addView(R.id.item_weekday_day_6, dayRemoteViews);
                            break;
                        case 7:
                            bigRemoteViews.addView(R.id.item_weekday_day_7, dayRemoteViews);
                            break;
                    }

                }
            }
            return bigRemoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    private Course getCourseByRowCol(int row, int col) {
        Course result = null;
        for (Course course : mCourses) {
            if ((course.getDay() + 1) == row && course.getStart() == col) {
                result = course;
            }
        }
        return result;
    }
    //获取week周的所有课程
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Course> getCurrentCoueses(Context context) {
        int week = dateUtils.getCurrentWeek();
        ClassesDataBase dbHelper = new ClassesDataBase(context);
        List<Course> courses = new ArrayList<>();
        String index;
        for (int days = 0; days < 7; days++) {
            for (int classes = 1; classes < 12; classes++) {
                if (week < 10) {
                    if (classes < 10) {
                        index = "0" + week + "-" + days + "-0" + classes;
                    } else {
                        index = "0" + week + "-" + days + "-" + classes;
                    }
                } else {
                    if (classes < 10) {
                        index = week + "-" + days + "-0" + classes;
                    } else {
                        index = week + "-" + days + "-" + classes;
                    }
                }
//                Log.d("Courses",index);
                Cursor data = dbHelper.getOneData(index);
                if (data.getCount() > 0) {
                    data.moveToNext();
//                    Log.d("Courses",data.getString(0)+data.getString(1)+
//                            data.getString(2)+ data.getString(3)+
//                            data.getInt(4));
                    courses.add(new Course(data.getString(0), data.getString(1),
                            data.getString(2), data.getString(3),
                            data.getInt(4)));
                }
            }
        }
        dbHelper.close();
        return courses;
    }

}
