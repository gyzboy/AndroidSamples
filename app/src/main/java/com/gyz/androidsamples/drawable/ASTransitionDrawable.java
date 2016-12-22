package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/24.
 * 邮箱:gyzboy@126.com
 */
public class ASTransitionDrawable extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageButton ib = new ImageButton(this);
        ib.setImageResource(R.drawable.transdrawable);
        //支持不超过两个的item，带渐变效果，属于layerDrawable
        final TransitionDrawable tb = (TransitionDrawable) ib.getDrawable();
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tb.startTransition(500);//前进一个item
                tb.reverseTransition(500);//后退一个item
            }
        });
        setContentView(ib);
    }
}
