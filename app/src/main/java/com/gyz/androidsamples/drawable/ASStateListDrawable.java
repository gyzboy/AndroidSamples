package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/17.
 * 邮箱:gyzboy@126.com
 */
public class ASStateListDrawable extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * <selector xmlns:android="http://schemas.android.com/apk/res/android"
         android:constantSize=["true" | "false"]
         android:dither=["true" | "false"]
         android:variablePadding=["true" | "false"] >
         <item
         android:drawable="@[package:]drawable/drawable_resource"
         android:state_pressed=["true" | "false"]
         android:state_focused=["true" | "false"]
         android:state_hovered=["true" | "false"]
         android:state_selected=["true" | "false"]
         android:state_checkable=["true" | "false"]
         android:state_checked=["true" | "false"]
         android:state_enabled=["true" | "false"]
         android:state_activated=["true" | "false"]
         android:state_window_focused=["true" | "false"] />
         </selector>
         */
        StateListDrawable sld = (StateListDrawable) getResources().getDrawable(R.drawable.selectdrawable);
        Button btn = new Button(this);
        btn.setBackground(sld);
        setContentView(btn);
    }
}
