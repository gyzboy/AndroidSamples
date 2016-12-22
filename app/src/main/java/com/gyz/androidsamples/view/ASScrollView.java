package com.gyz.androidsamples.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/7/15.
 * 邮箱:gyzboy@126.com
 */
public class ASScrollView extends Activity {

    //Scrollview是一个frameLayout，所以在其中必须有child充当content存在
    //不建议scrollview连同listview或者textview一起使用，因为在其内部已经拥有了滑动的逻辑

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
        ScrollView sv = (ScrollView) findViewById(R.id.scroll);
        sv.setFillViewport(true);//true表示拉伸内容高度来适应视口边界，默认false
    }
}
