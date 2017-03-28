package com.gyz.androidopensamples.timber;

import android.app.Application;

import com.gyz.androidopensamples.BuildConfig;

import java.sql.Time;

/**
 * Created by guoyizhe on 2017/3/27.
 * 邮箱:gyzboy@126.com
 */

public class TimberApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {

        }
    }
}
