package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/17.
 * 邮箱:gyzboy@126.com
 */
public class ASAnimationDrawable extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView img = new ImageView(this);
        img.setBackgroundResource(R.drawable.animlist);

        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

        frameAnimation.start();
        setContentView(img);
    }
}
