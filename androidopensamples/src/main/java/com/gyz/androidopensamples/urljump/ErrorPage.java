package com.gyz.androidopensamples.urljump;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by guoyizhe on 2016/11/8.
 * 邮箱:gyzboy@126.com
 */

public class ErrorPage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("This is ErrorPage");
        setContentView(tv);
    }
}
