package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/24.
 * 邮箱:gyzboy@126.com
 */
public class ASScaleDrawable extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.scaledrawable);
        ScaleDrawable sd = (ScaleDrawable) iv.getDrawable();
        sd.setLevel(1);//这里必须设置level是因为在ScaleDrawable的实现中onDraw方法必须是level ！= 0 才调用，而默认的level为0
        setContentView(iv);
    }
}
