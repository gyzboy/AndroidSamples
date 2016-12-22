package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by guoyizhe on 16/8/17.
 * 邮箱:gyzboy@126.com
 */
public class ASDrawerArrowDrawable extends Activity {

    private boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DrawerArrowDrawable dad = new DrawerArrowDrawable(this);
        dad.setDirection(DrawerArrowDrawable.ARROW_DIRECTION_RIGHT);
        ImageView iv = new ImageView(this);
        iv.setImageDrawable(dad);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isClicked){
                    dad.setProgress(0.8f);
                }else{
                    dad.setProgress(0.5f);
                }
                isClicked = !isClicked;
            }
        });
        setContentView(iv);
    }
}
