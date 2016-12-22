package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/9/2.
 * 邮箱:gyzboy@126.com
 */
public class ASLayerDrawable extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ImageView iv = new ImageView(this);
        iv.post(new Runnable() {
            @Override
            public void run() {
                iv.getLayoutParams().width = 500;
                iv.getLayoutParams().height = 500;
            }
        });
        iv.setBackground(getResources().getDrawable(R.drawable.layerdrawable));

        setContentView(iv);
    }
}
