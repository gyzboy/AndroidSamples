package com.gyz.androidsamples.view.touch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.gyz.androidsamples.Constants;

public class ASTouchLayout extends LinearLayout {
    public ASTouchLayout(Context context) {
        super(context);
    }

    public ASTouchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(Constants.TOUCH_TAG,"layout dispatchTouchEvent " + Constants.actionToStr(ev.getAction()));
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            return true;
//        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(Constants.TOUCH_TAG,"layout onTouchEvent " + Constants.actionToStr(event.getAction()));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(Constants.TOUCH_TAG,"layout onInterceptTouchEvent " + Constants.actionToStr(ev.getAction()));
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            return false;
//        }
        return false;
    }
}
