package com.gyz.androidsamples.drawable;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/16.
 * 邮箱:gyzboy@126.com
 */
public class ASAnimatedVectorDrawable extends Activity {
    //这里使用ObjectAnimator和AnimatorSet来实现vectorDrawable的动画效果
    //target-21

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //step 1:新建vectordrawable.xml
        //step 2:定义vectordrawable动画,avd.xml
        ImageView iv = new ImageView(this);
        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.avd);
        iv.setBackground(avd);
        avd.start();
        setContentView(iv);
    }
}
