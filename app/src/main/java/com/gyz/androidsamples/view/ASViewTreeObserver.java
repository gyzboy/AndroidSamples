package com.gyz.androidsamples.view;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.gyz.androidopensamples.utilCode.LogUtils;
import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 2017/3/16.
 * 邮箱:gyzboy@126.com
 */

public class ASViewTreeObserver extends Activity {
    //ViewTreeObserver是view事件的观察者。这个观察者用来监听视图树，会监听视图树发生全局变化时发出的通知。
    // 这里指的全局事件包括而且不局限在以下几个：整个视图树的布局变化，开始绘制视图，触摸模式改变等等。
//ViewTreeObserver是不能被应用程序实例化的，因为它是由视图提供的，通过view.getViewTreeObserver()获取。


//    interface  ViewTreeObserver.OnGlobalFocusChangeListener
//    //当在一个视图树中的焦点状态发生改变时，所要调用的回调函数的接口类
//    interface  ViewTreeObserver.OnGlobalLayoutListener
//    //当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时，所要调用的回调函数的接口类
//    interface  ViewTreeObserver.OnPreDrawListener
//    //当一个视图树将要绘制时，所要调用的回调函数的接口类
//    interface  ViewTreeObserver.OnScrollChangedListener
//    //当一个视图树中的一些组件发生滚动时，所要调用的回调函数的接口类
//    interface  ViewTreeObserver.OnTouchModeChangeListener
//    //当一个视图树的触摸模式发生改变时，所要调用的回调函数的接口类

    private static final String TAG  = ASViewTreeObserver.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtreeobserver);

        final ImageView imageView = (ImageView) findViewById(R.id.vto_image);
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                LogUtils.d(TAG, "OnGlobalLayoutListener..myImageView " +
                        "height:" + imageView.getHeight() + "  ,width:" + imageView.getWidth());
            }
        });

        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这时已经测量结束并且view框架已经确定,用户可以确定滑动边界
                //返回true表示继续当前绘制,返回false表示cancel绘制
                LogUtils.d(TAG,"onPreDraw");
                return true;
            }
        });

        imageView.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
            @Override
            public void onWindowAttached() {
                LogUtils.d(TAG,"onWindowAttached");
            }

            @Override
            public void onWindowDetached() {
                LogUtils.d(TAG,"onWindowDetached");
            }
        });

        imageView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                LogUtils.d(TAG,"onDraw");
            }
        });

        imageView.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                LogUtils.d(TAG,"oldFocus" + oldFocus + " newFocus" + newFocus);
            }
        });

        imageView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                LogUtils.d(TAG,"onScrollChanged");
            }
        });

        imageView.getViewTreeObserver().addOnTouchModeChangeListener(new ViewTreeObserver.OnTouchModeChangeListener() {
            @Override
            public void onTouchModeChanged(boolean isInTouchMode) {
                LogUtils.d(TAG,"isInTouchMode = " + isInTouchMode);
            }
        });
        imageView.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(boolean hasFocus) {
                LogUtils.d(TAG,"hasFocus = " + hasFocus);
            }
        });
    }
}
