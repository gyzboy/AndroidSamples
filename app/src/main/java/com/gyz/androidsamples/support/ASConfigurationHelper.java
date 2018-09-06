package com.gyz.androidsamples.support;

import android.content.Context;
import android.support.v4.content.res.ConfigurationHelper;

public class ASConfigurationHelper {
    public static int getSmallestWidth(Context context){
        return ConfigurationHelper.getSmallestScreenWidthDp(context.getResources());
    }

    public static int getDpi(Context context){
        return ConfigurationHelper.getDensityDpi(context.getResources());
    }
}
