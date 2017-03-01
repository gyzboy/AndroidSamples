package com.gyz.androidsamples.managers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gyz.androidsamples.R;

import java.util.List;

/**
 * Created by guoyizhe on 16/6/15.
 * 邮箱:gyzboy@126.com
 */

/**
 * 获取正在运行中的Activity信息
 */
public class ASActivityManager extends Activity {
    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_text_jump);

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setMovementMethod(new ScrollingMovementMethod());//textview可滑动
        Button button = (Button) findViewById(R.id.jump);
        button.setVisibility(View.GONE);

        StringBuilder sb = new StringBuilder();
        List<ActivityManager.AppTask> tasks = am.getAppTasks();
//获得系统运行的进程
        sb.append("获得系统运行的进程:");
        List<ActivityManager.RunningAppProcessInfo> appList1 = am
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo running : appList1) {
            sb.append("\nrunning.processName:" + running.processName);
            sb.append("\nrunning.pid:" + running.pid);
            sb.append("\nrunning.uid:" + running.uid);
            sb.append("\nrunning.pkgList:" + running.pkgList);
            sb.append("\n=================");
        }

//获得当前正在运行的service
        sb.append("\n获得当前正在运行的service:");
        List<ActivityManager.RunningServiceInfo> appList2 = am
                .getRunningServices(100);
        for (ActivityManager.RunningServiceInfo running : appList2) {
            sb.append("\n" + running.service.getClassName());
        }

//获得当前正在运行的activity
        sb.append("\n获得当前正在运行的activity栈信息:");
        List<ActivityManager.RunningTaskInfo> appList3 = am
                .getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo running : appList3) {
            sb.append("\n栈底Activity:" + running.baseActivity.getClassName());
            sb.append("\n栈顶Activity:" + running.topActivity.getClassName());
            sb.append("\n栈中Activity数:" + running.numActivities);
            sb.append("\n栈中Activity运行数:" + running.numRunning);
            sb.append("\n=================");
        }

//获得最近运行的应用
        sb.append("\n获得最近运行的应用:");
        List<ActivityManager.RecentTaskInfo> appList4 = am
                .getRecentTasks(100, ActivityManager.RECENT_WITH_EXCLUDED);
        for (ActivityManager.RecentTaskInfo running : appList4) {
            sb.append("\n" + running.description);
        }
        tv.setText(sb.toString());

        //Activity不显示在recent列表中.
//        Intent intent = new Intent(this, TaskTestActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//        startActivity(intent);
    }
}
