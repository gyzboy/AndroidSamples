package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/24.
 * 邮箱:gyzboy@126.com
 */
public class ASInsetDrawable extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        <inset
//        xmlns:android="http://schemas.android.com/apk/res/android"
//        android:drawable="@drawable/drawable_resource"
//        android:insetTop="dimension"
//        android:insetRight="dimension"
//        android:insetBottom="dimension"
//        android:insetLeft="dimension" />

        //适用于要使用的背景比实际尺寸小

        InsetDrawable id = (InsetDrawable) getResources().getDrawable(R.drawable.insetdrawable);
        ImageView iv = new ImageView(this);
        iv.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));
        iv.setImageDrawable(id);

        setContentView(iv);
    }
}
