package com.gyz.androidsamples.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by guoyizhe on 16/6/13.
 * 邮箱:gyzboy@126.com
 */
public class ActivityInfo {
    public static ActivityManager.RunningTaskInfo getTaskInfo(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos != null)
            return runningTaskInfos.get(0);
        else
            return null;
    }

    public static List<ActivityManager.RunningAppProcessInfo> getRunningProcess(Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return manager.getRunningAppProcesses();
    }
}
