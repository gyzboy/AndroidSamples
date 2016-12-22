package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/17.
 * 邮箱:gyzboy@126.com
 */
public class ASLevelListDrawable extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LevelListDrawable lld = (LevelListDrawable) getResources().getDrawable(R.drawable.levellist);
        ImageView iv = new ImageView(this);
        iv.setImageDrawable(lld);
        iv.setImageLevel(2);
        setContentView(iv);
    }
}
