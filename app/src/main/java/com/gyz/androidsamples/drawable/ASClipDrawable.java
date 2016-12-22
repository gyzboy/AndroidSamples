package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by guoyizhe on 16/8/23.
 * 邮箱:gyzboy@126.com
 */
public class ASClipDrawable extends Activity {

    private int i = 0;
    private ClipDrawable cd;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            cd.setLevel(300 * i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        <clip
//        xmlns:android="http://schemas.android.com/apk/res/android"
//        android:drawable="@drawable/drawable_resource"
//        android:clipOrientation=["horizontal" | "vertical"]
//        android:gravity=["top" | "bottom" | "left" | "right" | "center_vertical" |
//                "fill_vertical" | "center_horizontal" | "fill_horizontal" |
//                "center" | "fill" | "clip_vertical" | "clip_horizontal"] />

        //可作出画轴效果
        ImageView iv = new ImageView(this);
        cd = (ClipDrawable) getResources().getDrawable(R.drawable.clipdrawable);
        iv.setBackground(cd);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                i++;
                handler.sendEmptyMessage(0);
                if(300 * i >= 10000){//level最大值
                    cancel();
                }
            }
        },100,1000);
        setContentView(iv);
    }
}
