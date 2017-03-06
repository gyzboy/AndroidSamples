package com.gyz.androidopensamples.optimization.startup;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.TraceCompat;
import android.util.Log;
import android.util.Printer;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyz.androidopensamples.R;

/**
 * Created by guoyizhe on 2017/3/3.
 * 邮箱:gyzboy@126.com
 */

public class StartUpActivity extends Activity {
    private static final int DEALY_TIME = 300;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    private Handler myHandler = new Handler();
    private Runnable mLoadingRunnable = new Runnable() {

        @Override
        public void run() {
            updateText();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        imageView1 = (ImageView) findViewById(R.id.image1);
        imageView2 = (ImageView) findViewById(R.id.image2);
        imageView3 = (ImageView) findViewById(R.id.image3);

        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);

//  第一种写法:直接Post
//        myHandler.post(mLoadingRunnable);

//  第二种写法:直接PostDelay 300ms.
//  myHandler.postDelayed(mLoadingRunnable, DEALY_TIME);

//  第三种写法:优化的DelayLoad
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                myHandler.post(mLoadingRunnable);
            }
        });

        // Dump当前的MessageQueue信息.
        getMainLooper().dump(new Printer() {

            @Override
            public void println(String x) {
                Log.i("Gracker", x);
            }
        }, "onCreate");
    }

    private void updateText() {
        TraceCompat.beginSection("updateText");
        textView1.setText("image1 : w=" + imageView1.getWidth() +
                " h =" + imageView1.getHeight());
        textView2.setText("image2 : w=" + imageView2.getWidth() +
                " h =" + imageView2.getHeight());
        textView3.setText("image3 : w=" + imageView3.getWidth() +
                " h =" + imageView3.getHeight());
        TraceCompat.endSection();
    }
}
