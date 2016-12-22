package com.gyz.androidsamples.callbacks;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;

/**
 * Created by guoyizhe on 16/6/12.
 * 邮箱:gyzboy@126.com
 */

/**
 * 可以通过上下文注册的callback，可以监听configuration变化和lowMemory
 */
public class ASComponentCallbacks implements ComponentCallbacks {
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }
}
