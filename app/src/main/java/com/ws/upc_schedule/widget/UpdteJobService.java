package com.ws.upc_schedule.widget;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class UpdteJobService extends JobService {

    @Override
    public void onCreate() {
//        LogUtil.e(this, "onCreate");
        super.onCreate();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
//        LogUtil.e(this, "UpdateJobService任务执行");
        WidgetUtils.updateWidget(getApplicationContext());
        jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    @Override
    public void onDestroy() {
//        LogUtil.e(this, "onDestroy");

        super.onDestroy();
    }
}
