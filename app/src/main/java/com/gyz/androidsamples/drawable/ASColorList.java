package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

public class ASColorList extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tint);
        ImageView mIvTint = (ImageView) findViewById(R.id.iv_tint);

        Drawable originalDrawable = mIvTint.getDrawable();
        int[] colors = new int[] { ContextCompat.getColor(this, android.R.color.black), ContextCompat.getColor(this, android.R.color.holo_blue_light)};
        int[][] states = new int[2][];
        states[0] = new int[] { android.R.attr.state_pressed,android.R.attr.state_focused};
        states[1] = new int[] {};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        Drawable tintDrawable = DrawableCompat.wrap(originalDrawable).mutate();
        DrawableCompat.setTintList(tintDrawable, colorStateList);
        mIvTint.setImageDrawable(tintDrawable);
    }
}
