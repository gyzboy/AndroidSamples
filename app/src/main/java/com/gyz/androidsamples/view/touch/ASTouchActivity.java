package com.gyz.androidsamples.view.touch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gyz.androidsamples.Constants;
import com.gyz.androidsamples.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ASTouchActivity extends Activity {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_touchtest);
        LinearLayout root = (LinearLayout) findViewById(R.id.root);
        ASTouchLayout layout = new ASTouchLayout(this);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
        layout.setLayoutParams(params);
        ASTouchView view = new ASTouchView(this);
        ViewGroup.LayoutParams params1 = new LinearLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
        view.setLayoutParams(params1);
        layout.addView(view);
        root.addView(layout);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.TOUCH_TAG, "view onClick: ");
            }
        });

        Dialog dialog = new Dialog(this);
        dialog.show();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(Constants.TOUCH_TAG,"activity dispatchTouchEvent " + Constants.actionToStr(ev.getAction()));
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(Constants.TOUCH_TAG,"activity onTouchEvent " + Constants.actionToStr(event.getAction()));
        return super.onTouchEvent(event);
    }

}
