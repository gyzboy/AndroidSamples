package com.gyz.androidsamples.activity.process;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.widget.TextView;

import com.gyz.androidsamples.utils.ActivityInfo;

/**
 * Created by guoyizhe on 16/9/13.
 * 邮箱:gyzboy@126.com
 */
public class ASProcessTwo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        String processName = "";
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ActivityInfo.getRunningProcess(this)) {
            if (runningAppProcessInfo.processName.contains(":processtest")) {
                processName = runningAppProcessInfo.processName;
            }
        }
        tv.setText("手动设置进程标签process\n"
        + "当前进程名为:" + processName);
        setContentView(tv);
    }
}
