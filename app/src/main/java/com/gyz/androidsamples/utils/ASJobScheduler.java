package com.gyz.androidsamples.utils;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyz.androidsamples.service.ASService;

/**
 * Created by guoyizhe on 2018/1/25.
 */

public class ASJobScheduler extends Activity {
    JobScheduler scheduler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName jobService = new ComponentName(this, ASService.class);

        JobInfo jobInfo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            jobInfo = new JobInfo.Builder(123, jobService) //任务Id等于123
                    .setMinimumLatency(5000)// 任务最少延迟时间
                    .setOverrideDeadline(60000)// 任务deadline，当到期没达到指定条件也会开始执行
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)// 网络条件，默认值NETWORK_TYPE_NONE
                    .setRequiresCharging(true)// 是否充电
                    .setRequiresDeviceIdle(false)// 设备是否空闲
//                    .setPersisted(true) //设备重启后是否继续执行
                    .setBackoffCriteria(3000,JobInfo.BACKOFF_POLICY_LINEAR) //设置退避/重试策略
                    .build();
            scheduler.schedule(jobInfo);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scheduler.cancel(123); //取消jobId=123的任务
//            scheduler.cancelAll(); //取消当前uid下的所有任务
        }
    }
}
