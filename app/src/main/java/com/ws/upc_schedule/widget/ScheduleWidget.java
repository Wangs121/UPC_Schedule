package com.ws.upc_schedule.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

import com.ws.upc_schedule.Login.LoginRepository;
import com.ws.upc_schedule.MainActivity;
import com.ws.upc_schedule.R;
import com.ws.upc_schedule.data.dateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class ScheduleWidget extends AppWidgetProvider {

    private ComponentName thisWidget;
    private RemoteViews remoteViews;
    private static final String ACTION_SIMPLEAPPWIDGET = "ACTION_UPDATE_CLICK";

    /** AppWidgetProvider 继承自 BroadcastReceiver */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.d("widget","on Receive");
        if (ACTION_SIMPLEAPPWIDGET.equals(intent.getAction())) {
            updateAction(context);
        }
        if (intent != null && intent.getAction() != null) {
//            Log.e(this.toString(), intent.getAction());
            if (intent.getAction().equals("com.ws.upc_schedule.action.APPWIDGET_UPDATE")) {
                updateAction(context);
            }
        }

        super.onReceive(context, intent);
    }

    /**
     * 根据 updatePeriodMillis 定义的定期刷新操作会调用该函数，此外当用户添加 Widget 时appwidget-provider>
     * 也会调用该函数，可以在这里进行必要的初始化操作。但如果在<
     * 中声明了 android:configure 的 Activity，在用户添加 Widget 时，不会调用 onUpdate()，
     * 需要由 configure Activity 去负责去调用
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        Log.d(this.toString(), "onUpdate" + appWidgetIds[0]);
        Log.d("widget","on Update");
        updateAction(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateAction(Context context) {
        Log.d("widget","on UpdateAction");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetId = appWidgetManager.getAppWidgetIds(new ComponentName(context, ScheduleWidget.class));

        thisWidget = new ComponentName(context, ScheduleWidget.class);
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_all);

        int week = getCurrentWeek(context);
        remoteViews.setTextViewText(R.id.tv_month,week+"\n周");

        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        //配置适配器
        remoteViews.setRemoteAdapter(R.id.widget_list, intent);

        Intent intent1 = new Intent(context, MainActivity.class);

        PendingIntent pendingIntentTemplate = PendingIntent.getActivity(
                context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        ////拼接PendingIntent
        remoteViews.setPendingIntentTemplate(R.id.widget_list, pendingIntentTemplate);

        //点击标题栏更新wigdet
        Intent update = new Intent(context,ScheduleWidget.class);
        update.setAction(ACTION_SIMPLEAPPWIDGET);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, update,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_all,pendingIntent);
        //更新remoteViews
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
    }

    /** onDeleted()：当 Widget 被删除时调用该方法。 */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
//        Log.d(this.toString(), "onDeleted");

    }


    /**
     * 当 Widget 第一次被添加时调用，例如用户添加了两个你的 Widget，
     * 那么只有在添加第一个 Widget 时该方法会被调用。
     * 所以该方法比较适合执行你所有 Widgets 只需进行一次的操作。
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
//        Log.d(this.toString(), "onEnabled");
    }

    /**
     * 与 onEnabled 恰好相反，当你的最后一个 Widget 被删除时调用该方法，
     * 所以这里用来清理之前在 onEnabled() 中进行的操作。
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
//        Log.d(this.toString(), "onDisabled");
//        AppUtils.cancelUpdateWidgetService(context.getApplicationContext());
        WidgetUtils.cancelUpdateWidgetService(context.getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int getCurrentWeek(Context context) {
        int currentWeek = 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String n = sdf.format(now);
        String termBeginDay = LoginRepository.getFirstDayofTerm(context);
        LocalDate d1 = LocalDate.parse(termBeginDay, formatter);
        LocalDate d2 = LocalDate.parse(n, formatter);
        int delta = (int) ChronoUnit.DAYS.between(d1, d2);
        currentWeek = (int) (delta / 7) + 1;
        if (currentWeek < 1) {
            currentWeek = 1;
        }
        if (currentWeek > 18) {
            currentWeek = 18;
        }
        return currentWeek;
    }
}

