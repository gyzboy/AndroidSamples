package com.gyz.androidsamples.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

/**
 * Created by guoyizhe on 16/9/19.
 * 邮箱:gyzboy@126.com
 */
public class ASGestureDetector extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GestureDetectorView gdv = new GestureDetectorView(this);
        setContentView(gdv);
    }
}

class GestureDetectorView extends TextView {

    private GestureDetector mDetector = new GestureDetector(new MyGestureListener());

    public GestureDetectorView(Context context) {
        super(context);
    }

    public GestureDetectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureDetectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}

class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final String TAG = ASGestureDetector.class.getSimpleName();

    //手指离开触摸屏的那一刹那。
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return super.onSingleTapUp(e);
    }

    //手指按在持续一段时间，并且没有松开。
    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
    }

    //手指在触摸屏上滑动。
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    //手指按在触摸屏上，它的时间范围在按下起效，在长按之前。
    @Override
    public void onShowPress(MotionEvent e) {
        super.onShowPress(e);
    }

    //双击的第二下ACTION_DOWN时触发
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return super.onDoubleTap(e);
    }

    //双击的第二下ACTION_DOWN和ACTION_UP都会触发，e.getAction()区别
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return super.onDoubleTapEvent(e);
    }

    //ACTION_DOWN后没有滑动（onScroll）且没有长按（onLongPress）接着ACTION_UP时触发
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return super.onSingleTapConfirmed(e);
    }
    //context点击触发，与View#onGenericMotionEvent(MotionEvent)相关，不常用
    @Override
    public boolean onContextClick(MotionEvent e) {
        return super.onContextClick(e);
    }

    //刚刚手指接触到触摸屏的那一刹那，就是触的那一下。
    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(TAG, "onDown: " + event.toString());
        return true;
    }

    //手指在触摸屏上迅速移动，并松开的动作。
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }
}
