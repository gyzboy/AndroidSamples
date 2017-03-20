package com.gyz.androidsamples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by guoyizhe on 2017/3/16.
 * 邮箱:gyzboy@126.com
 *
 * @author guoyizhe
 * @date 2017/03/16
 */

public class LaucherActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.big);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaucherActivity.this,MainActivity.class));
                finish();
            }
        },300);
    }
}
