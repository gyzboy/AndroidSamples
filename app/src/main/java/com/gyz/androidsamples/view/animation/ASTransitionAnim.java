package com.gyz.androidsamples.view.animation;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.gyz.androidsamples.R;

public class ASTransitionAnim extends Activity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        Button button = (Button) findViewById(R.id.btn_change);
        FrameLayout frameLayout1 = (FrameLayout) findViewById(R.id.fl_container1);
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.fl_container2);
        Scene scene1 = new Scene(frameLayout1);
        final Scene scene2 = new Scene(frameLayout2);
        TransitionManager.go(scene1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(scene2);
            }
        });
    }
}
