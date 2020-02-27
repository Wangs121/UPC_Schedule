package com.ws.upc_schedule.widget;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class WidgetUtils {
    /**
     * 更新widget组件
     */
    public static void updateWidget(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.ws.upc_schedule.action.APPWIDGET_UPDATE");
        intent.setComponent(new ComponentName("com.ws.upc_schedule", "com.ws.upc_schedule.widget.ScheduleWidget"));
        context.sendBroadcast(intent);
    }

    public static int UPDATE_WIDGET_JOB_ID = 1;

    /**
     * 启动桌面小部件更新服务
     */
    public static void startWidgetJobService(Context context) {
        if (!isJobPollServiceOn(context)) {
//            LogUtil.i(AppUtils.class.toString(), "安排widget更新任务");
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(UPDATE_WIDGET_JOB_ID,
                    new ComponentName(context, UpdteJobService.class));
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
            builder.setPersisted(true);// 设置设备重启时，执行该任务
            builder.setRequiresCharging(true);
            builder.setPeriodic(2 * 60 * 60 * 1000); //两小时更新一次
//            builder.setPeriodic(5*60*1000,5*60*1000); //5min更新一次
            builder.setPersisted(true);
            jobScheduler.schedule(builder.build());
        } else {
//            LogUtil.i(AppUtils.class, "widget更新任务已经安排");
        }
    }


    private static boolean isJobPollServiceOn(Context context) {
        JobScheduler scheduler = (JobScheduler) context
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);

        for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == UPDATE_WIDGET_JOB_ID) {
                return true;
            }
        }
        return false;
    }

    /**
     * 取消widget更新任务
     */
    public static void cancelUpdateWidgetService(Context context) {
//        LogUtil.e(AppUtils.class, "cancelUpdateWidgetService");
        JobScheduler scheduler = (JobScheduler) context
                .getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancel(UPDATE_WIDGET_JOB_ID);
    }
}
