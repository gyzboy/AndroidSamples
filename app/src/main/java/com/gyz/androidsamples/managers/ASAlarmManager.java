package com.gyz.androidsamples.managers;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androidsamples.R;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by guoyizhe on 16/6/14.
 * 邮箱:gyzboy@126.com
 */

//1、AlarmManager，顾名思义，就是“提醒”，是Android中常用的一种系统级别的提示服务，在特定的时刻为我们广播一个指定的Intent,会一直保持CPU的唤醒状态直到onReceive执行,具体实现在IAlarmManager中
// (可用作设置不死服务，定时检测，定时重启，不手动设置cancel的情况下每次重启时会使manager无效)
// 简单的说就是我们设定一个时间，然后在该时间到来时，AlarmManager为我们广播一个我们设定的Intent,通常我们使用 PendingIntent，
// PendingIntent可以理解为Intent的封装包，简单的说就是在Intent上在加个指定的动作。在使用Intent的时候，我们还需要在执行startActivity、startService或sendBroadcast才能使Intent有用。而PendingIntent的话就是将这个动作包含在内了。
// 定义一个PendingIntent对象。
// PendingIntent pi = PendingIntent.getBroadcast(this,0,intent,0);

//2、AlarmManager的常用方法有三个：
// (1）set(int type，long startTime，PendingIntent pi)；
//  该方法用于设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
//（2）setRepeating(int type，long startTime，long intervalTime，PendingIntent pi)；
//  该方法用于设置重复闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟首次执行时间，第三个参数表示闹钟两次执行的间隔时间，第三个参数表示闹钟响应动作。
// (3）setInexactRepeating（int type，long startTime，long intervalTime，PendingIntent pi）；
//  该方法也用于设置重复闹钟，与第二个方法相似，不过其两个闹钟执行的间隔时间不是固定的而已。

//3、三个方法各个参数详悉：
//（1）int type： 闹钟的类型，常用的有5个值：AlarmManager.ELAPSED_REALTIME、 AlarmManager.ELAPSED_REALTIME_WAKEUP、AlarmManager.RTC、 AlarmManager.RTC_WAKEUP、AlarmManager.POWER_OFF_WAKEUP。
//  AlarmManager.ELAPSED_REALTIME表示闹钟在手机睡眠状态下不可用，该状态下闹钟使用相对时间（相对于系统启动开始），状态值为3；
//  AlarmManager.ELAPSED_REALTIME_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟也使用相对时间，状态值为2；
//  AlarmManager.RTC表示闹钟在睡眠状态下不可用，该状态下闹钟使用绝对时间，即当前系统时间，状态值为1；
//  AlarmManager.RTC_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间，状态值为0；
//  AlarmManager.POWER_OFF_WAKEUP表示闹钟在手机关机状态下也能正常进行提示功能，所以是5个状态中用的最多的状态之一，该状态下闹钟也是用绝对时间，状态值为4；不过本状态好像受SDK版本影响，某些版本并不支持；
// (2）long startTime： 闹钟的第一次执行时间，以毫秒为单位，可以自定义时间，不过一般使用当前时间。需要注意的是，本属性与第一个属性（type）密切相关，如果第一个参数对 应的闹钟使用的是相对时间（ELAPSED_REALTIME和ELAPSED_REALTIME_WAKEUP），那么本属性就得使用相对时间（相对于 系统启动时间来说），比如当前时间就表示为：SystemClock.elapsedRealtime()；如果第一个参数对应的闹钟使用的是绝对时间 （RTC、RTC_WAKEUP、POWER_OFF_WAKEUP），那么本属性就得使用绝对时间，比如当前时间就表示 为：System.currentTimeMillis()。
//（3）long intervalTime：对于后两个方法来说，存在本属性，表示两次闹钟执行的间隔时间，也是以毫秒为单位。
//（4）PendingIntent pi： 绑定了闹钟的执行动作，比如发送一个广播、给出提示等等。PendingIntent是Intent的封装类。
// 需要注意的是，如果是通过启动服务来实现闹钟提 示的话，PendingIntent对象的获取就应该采用Pending.getService(Context c,int i,Intent intent,int j)方法；如果是通过广播来实现闹钟提示的话，PendingIntent对象的获取就应该采用 PendingIntent.getBroadcast(Context c,int i,Intent intent,int j)方法；如果是采用Activity的方式来实现闹钟提示的话，PendingIntent对象的获取就应该采用 PendingIntent.getActivity(Context c,int i,Intent intent,int j)方法。如果这三种方法错用了的话，虽然不会报错，但是看不到闹钟提示效果。

public class ASAlarmManager extends Activity {
    private Calendar calendar;
    private AlarmManager am;
    private PendingIntent pendingIntent;
    private IntentFilter filter;
    Intent intent;
    TextView tv;
    StringBuilder sb = new StringBuilder();
    int i = 0;
    boolean isRegistered = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_text_jump);
        tv = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.jump);
        button.setText("开启alarm服务");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerReceiver(TimeReceiver, filter);
                isRegistered = true;
            }
        });
//        calendar = Calendar.getInstance();
//
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        // set(f, value) changes field f to value. 设置监听时间的时分秒
//        int hourOfDay = 23, minute = 59;
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
        filter = new IntentFilter();
        filter.addAction("com.gyz.androidsamples.alarm");

        intent = new Intent();
        intent.setAction("com.gyz.androidsamples.alarm");
        intent.putExtra("time", i);
        pendingIntent = PendingIntent.getBroadcast(ASAlarmManager.this, UUID.randomUUID().hashCode(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 获取系统进程
        am = (AlarmManager) getSystemService(ALARM_SERVICE);
        //第一个参数设置模式
        //第二个参数设置起始时间
        //第三个参数设置周期
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, 0,
                3 * 1000, pendingIntent);
//        String tmps = "设置闹钟时间为：" + format(hourOfDay) + ":" + format(minute);
//        Toast.makeText(this,tmps,Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onDestroy() {
        am.cancel(pendingIntent);
        if (isRegistered) {
            unregisterReceiver(TimeReceiver);
        }
        super.onDestroy();
    }

    private BroadcastReceiver TimeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.gyz.androidsamples.alarm")) {
                sb.append("到时间了:" + intent.getIntExtra("time",-1) + "\n");
                tv.setText(sb.toString());
                i++;
            }
        }
    };

    /**
     * 8:8 format 08:08
     */
    private String format(int x) {
        String s = "" + x;
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }
}
