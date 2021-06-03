package com.gyz.androidsamples.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gyz.androidsamples.activity.launchMode.ActivityB;
import com.gyz.androidsamples.activity.launchMode.ActivityC;
import com.gyz.androidsamples.activity.lifecycle.ActivityA;

/**
 * Created by gyzboy on 2017/9/12.
 */

public class ASService extends Service {

    public static final String TAG = ASService.class.getSimpleName();

    private MyBinder mBinder = new MyBinder();

    private Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"service oncreate");
        //只在第一次创建的时候调用,证明service是运行在主线程中的
        Log.e(TAG,"service thread" + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(final Intent intent,
                              int flags, int startId) {
        Log.e(TAG,"service onstartCommand");
//        Intent[] intent1 = new Intent[3];
//        intent1[0] = new Intent(ASService.this, ActivityA.class);
//        intent1[0].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent1[1] = new Intent(ASService.this, ActivityB.class);
//        intent1[1].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent1[2] = new Intent(ASService.this, ActivityC.class);
//        intent1[2].setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivities(intent1);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent();
                intent1.setClass(ASService.this, ActivityC.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        },2000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"service onbind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"service unbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"service ondestroy");
        super.onDestroy();
    }

    class MyBinder extends Binder {

        public void startDownload() {
            Log.d("TAG", "startDownload() executed");
            // 执行具体的下载任务
        }
    }
}
