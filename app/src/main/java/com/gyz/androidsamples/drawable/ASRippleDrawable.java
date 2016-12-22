package com.gyz.androidsamples.drawable;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/24.
 * 邮箱:gyzboy@126.com
 */
public class ASRippleDrawable extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= 21){
            setContentView(R.layout.activity_ripple);
        }
    }
}
