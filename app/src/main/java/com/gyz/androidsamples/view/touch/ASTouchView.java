package com.gyz.androidsamples.view.touch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gyz.androidsamples.Constants;

public class ASTouchView extends View {
    public ASTouchView(Context context) {
        super(context);
    }

    public ASTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(Constants.TOUCH_TAG,"view dispatchTouchEvent " + Constants.actionToStr(ev.getAction()));
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            getParent().requestDisallowInterceptTouchEvent(true);
//        }
//        if(ev.getAction() == MotionEvent.ACTION_MOVE){
//            getParent().requestDisallowInterceptTouchEvent(false);
//        }
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(Constants.TOUCH_TAG,"view onTouchEvent " + Constants.actionToStr(ev.getAction()));
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

}
