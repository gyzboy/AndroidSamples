package com.gyz.androidopensamples.urljump;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by guoyizhe on 2016/11/7.
 * 邮箱:gyzboy@126.com
 */

public class ASUrlJump3 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("route3");
        setContentView(tv);
    }
}
