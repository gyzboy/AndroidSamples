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
        System.out.println("lifeCallback activityCreated");//ActivityLifecycleCallbacks 中所有方法的调用时机都是在 Activity 对应生命周期的 Super 方法中进行的
    }

    @Override
    public void onActivityStarted(Activity activity) {
        appCount++;
        System.out.println("lifeCallback activityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        System.out.println("lifeCallback activityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        System.out.println("lifeCallback activityPaused");
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
