package com.gyz.androidsamples.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.TextView;

/**
 * Created by guoyizhe on 16/9/19.
 * 邮箱:gyzboy@126.com
 */
public class ASVelocityTracker extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomView cv = new CustomView(this);
        setContentView(cv);
    }
}

class CustomView extends TextView {

        private static final String TAG = ASVelocityTracker.class.getSimpleName();
        public CustomView(Context context) {
            super(context);
        }

        public CustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

    VelocityTracker vTracker = null;
    StringBuilder sb = new StringBuilder();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (vTracker == null) {
                    vTracker = VelocityTracker.obtain();
                } else {
                    vTracker.clear();
                }
                sb.setLength(0);
                vTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                vTracker.addMovement(event);
                //设置单位，1000 表示每秒多少像素（pix/second),1代表每微秒多少像素（pix/millisecond)。
                vTracker.computeCurrentVelocity(1000);
                //从左向右划返回正数，从右向左划返回负数
                Log.e(TAG,"the x velocity is " + vTracker.getXVelocity());
                sb.append("the x velocity is " + vTracker.getXVelocity() + "\n");
                //从上往下划返回正数，从下往上划返回负数
                Log.e(TAG,"the y velocity is " + vTracker.getYVelocity());
                sb.append("the y velocity is " + vTracker.getYVelocity() + "\n");
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                vTracker.recycle();
                setText(sb.toString());
                break;
        }
        return true;
    }
}
