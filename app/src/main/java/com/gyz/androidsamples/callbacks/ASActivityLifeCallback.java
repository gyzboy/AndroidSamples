package com.gyz.androidsamples.callbacks;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

/**
 * Created by guoyizhe on 16/6/12.
 * 邮箱:gyzboy@126.com
 */

/**
 * 这个所有的activity都会调用，省去了在每个activity中去注册
 */
public class ASActivityLifeCallback implements ActivityLifecycleCallbacks {

    private int appCount = 0;

    public ASActivityLifeCallback(int count) {
        appCount = count;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        appCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        appCount--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


}
