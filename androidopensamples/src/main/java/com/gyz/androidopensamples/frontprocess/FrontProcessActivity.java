package com.gyz.androidopensamples.frontprocess;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gyz.androidopensamples.R;

/**
 * Created by guoyizhe on 2016/11/9.
 * 邮箱:gyzboy@126.com
 */

public class FrontProcessActivity extends Activity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontprocess);

        button1 = (Button) findViewById(R.id.btn_getRunningTasks);
        button2 = (Button) findViewById(R.id.btn_getRunningAppProcesses);
        button3 = (Button) findViewById(R.id.btn_ActivityLifecycleCallbacks);
        button4 = (Button) findViewById(R.id.btn_UsageStatsManager);
        button5 = (Button) findViewById(R.id.btn_getLinuxCoreInfo);
        tv_content = (TextView) findViewById(R.id.tv_content);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText("RunningTasks isFront = " + ProcessUtils.getRunningTask(FrontProcessActivity.this,getPackageName()));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText("RunningAppProcess isFront = " + ProcessUtils.getRunningAppProcesses(FrontProcessActivity.this,FrontProcessActivity.this.getPackageName()));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText("ActivityLifecycleCallbacks isFront = " + ProcessUtils.getApplicationValue(FrontProcessActivity.this));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText("UsageStatsManager isFront = " + ProcessUtils.queryUsageStats(FrontProcessActivity.this,FrontProcessActivity.this.getPackageName()));
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText("LinuxCoreInfo isFront = " + ProcessUtils.getLinuxCoreInfo(FrontProcessActivity.this,FrontProcessActivity.this.getPackageName()));
            }
        });
    }
}
