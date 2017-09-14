package com.gyz.androidsamples.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by gyzboy on 2017/9/12.
 */

public class ASService extends Service {

    public static final String TAG = ASService.class.getSimpleName();

    private MyBinder mBinder = new MyBinder();


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"service oncreate");
        //只在第一次创建的时候调用,证明service是运行在主线程中的
        Log.e(TAG,"service thread" + Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(Intent intent,
                                  int flags, int startId) {
        Log.e(TAG,"service onstartCommand");
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
