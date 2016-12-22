package com.gyz.androidsamples.managers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/10/11.
 * 邮箱:gyzboy@126.com
 */

public class ASNotificationManager extends Activity {

    private NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_manager);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Button button = (Button) findViewById(R.id.btn_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder builder = create();
                notificationManager.notify(0, builder.build());
            }
        });

        Button button2 = (Button) findViewById(R.id.btn_update);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder builder = create2();
                notificationManager.notify(0, builder.build());
            }
        });
        Button button3 = (Button) findViewById(R.id.btn_delete);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notificationManager.cancel(0);
//                notificationManager.cancelAll();//删除所有
            }
        });

        Button button4 = (Button) findViewById(R.id.btn_flow);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder builder = create2();
                notificationManager.notify(0, builder.build());
            }
        });

        Button button5 = (Button) findViewById(R.id.btn_custom);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder builder = createCustom();
                notificationManager.notify(0, builder.build());
            }
        });

        Button button6 = (Button) findViewById(R.id.btn_custom);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder builder = createExpland();
                notificationManager.notify(0, builder.build());
            }
        });
    }


    private NotificationCompat.Builder create() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ASNotificationManager.this);

        // 设置通知的基本信息：icon、标题、内容
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("My notification");
        builder.setContentText("Hello World!");

        // 设置通知的点击行为：这里启动一个 Activity
        Intent intent = new Intent(ASNotificationManager.this, ASNotificationManager.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        return builder;
    }

    private NotificationCompat.Builder create2() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ASNotificationManager.this);

        // 设置通知的基本信息：icon、标题、内容
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("This is update");
        builder.setContentText("更新");
        builder.setAutoCancel(true);//点击后自动消失

        // 设置通知的点击行为：这里启动一个 Activity
        Intent intent = new Intent(ASNotificationManager.this, ASNotificationManager.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        return builder;
    }

    private NotificationCompat.Builder createFlow() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ASNotificationManager.this);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("My notification");
        builder.setContentText("Hello World!");
// 设置通知的优先级
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
// 设置通知的提示音
        builder.setSound(alarmSound);
        return builder;
    }

    private NotificationCompat.Builder createExpland() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ASNotificationManager.this);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("My notification");
        builder.setContentText("Hello World!");

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("邮件标题：");
        for (int i = 0; i < 5; i++) {
            inboxStyle.addLine("邮件内容" + i);
        }
        builder.setStyle(inboxStyle);
        return builder;
    }

    private NotificationCompat.Builder createCustom() {
        RemoteViews bigView;
        RemoteViews smallView;

// 构建 bigView 和 smallView。
        bigView = new RemoteViews(getPackageName(), R.layout.item_rv);
        smallView = new RemoteViews(getPackageName(), R.layout.item_rv);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ASNotificationManager.this);

// 设置自定义 RemoteViews
        builder.setContent(smallView).setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = builder.build();

// 如果系统版本 >= Android 4.1，设置大视图 RemoteViews
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.bigContentView = bigView;
        }
        return builder;
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(getApplicationContext(), "通知进入", Toast.LENGTH_SHORT).show();
    }
}
