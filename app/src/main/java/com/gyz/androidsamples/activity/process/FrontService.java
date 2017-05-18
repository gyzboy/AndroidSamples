package com.gyz.androidsamples.activity.process;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import com.gyz.androidsamples.R;

/**
 * Created by gyzboy on 2017/5/12.
 */

public class FrontService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        Intent nfIntent = new Intent(this, ASProcessOne.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
            .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
            .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
            .setContentText("前台service");
        Notification notification = builder.build(); // 获取构建好的Notification
        startForeground(110, notification);
    }

    @Override
    public int onStartCommand(Intent intent,
                                  int flags, int startId) {
        //TODO 要运行的东西
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
