package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/17.
 * 邮箱:gyzboy@126.com
 */
public class ASBitmapDrawable extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //bitmap->drawable
        /**
         <?xml version="1.0" encoding="utf-8"?>
         <bitmap
         xmlns:android="http://schemas.android.com/apk/res/android"
         android:src="@[package:]drawable/drawable_resource"
         android:antialias=["true" | "false"]
         android:dither=["true" | "false"]
         android:filter=["true" | "false"]
         android:gravity=["top" | "bottom" | "left" | "right" | "center_vertical" |
         "fill_vertical" | "center_horizontal" | "fill_horizontal" |
         "center" | "fill" | "clip_vertical" | "clip_horizontal"]
         android:mipMap=["true" | "false"]
         android:tileMode=["disabled" | "clamp" | "repeat" | "mirror"] />
         */

        int[] colors = new int[]{Color.BLUE,Color.BLUE,Color.BLUE,Color.BLUE};//颜色数组长度必须大于width * height
        ImageView iv = new ImageView(this);
//        iv.setImageResource(R.drawable.bitmap);
        Drawable drawable = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        iv.setImageDrawable(new BitmapDrawable(
                getApplicationContext().getResources(),
                Bitmap.createBitmap(colors,1, 1, Bitmap.Config.ARGB_8888)));//可以构造个1像素的透明activity,(*^__^*) 嘻嘻……
        iv.setImageDrawable(drawable);
        setContentView(iv);

    }
}
