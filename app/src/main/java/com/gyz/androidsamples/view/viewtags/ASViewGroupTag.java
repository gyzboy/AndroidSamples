package com.gyz.androidsamples.view.viewtags;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gyz.androidsamples.R;

import java.security.acl.Group;

/**
 * Created by guoyizhe on 16/9/18.
 * 邮箱:gyzboy@126.com
 */
public class ASViewGroupTag extends Activity {

//    android:animateLayoutChanges="true"
//    布局改变时(添加\删除item)是否显示动画效果

//    android:clipChildren="false"
//    是否截取子view不超过父view的绘制区域,默认为true

//    android:clipToPadding="false"
//    如果子view绘制在padding区域是否截取子view,默认为true

//    android:layoutAnimation
//    第一次layout时的动画效果,在layout完成后自动开始

//    android:animationCache="false"
//    是否为child创建drawing cache,默认为true,虽然会消耗更多的内存和初始化时间,但提供了更好的性能

//    android:alwaysDrawnWithCache="false"
//    绘制child时是否一直使用cache,默认为true

//    android:addStatesFromChildren="false"
//    是否同子View状态相同,比如子View获取到了焦点,group同样获取焦点

//    android:descendantFocusability="blocksDescendants"
//    Group与子View的焦点获取状态:
//    beforeDescendants  group先获取焦点
//    afterDescendants  group后获取焦点
//    blocksDescendants group阻止子View获取焦点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtag);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.mipmap.ic_launcher);
        mToolbar.setTitle("gyzboy");

        ListView lv = (ListView) findViewById(R.id.listView);

        String[] data = new String[50];
        for (int i = 0; i < data.length; i++) {
            data[i] = "数据" + i;
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        lv.setAdapter(adapter);
    }
}
