package com.gyz.androidsamples.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by guoyizhe on 16/9/19.
 * 邮箱:gyzboy@126.com
 */
public class ASViewConfiguration extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this);
        StringBuilder sb = new StringBuilder();
        sb.append("ViewConfiguration这个类主要提供了一些自定义控件用到的标准常量，譬如尺寸、滑动距离、敏感度");

//        "public static ViewConfiguration get(Context context) {}"
//        "//获取水平滚动条的宽或垂直滚动条的高"
//        "public int getScaledScrollBarSize() {}"
//
//        "//滚动条褪去消失的持续时间"
//        "public static int getScrollBarFadeDuration() {}"
//
//        "//滚动条消失的延迟时间"
//        "public static int getScrollDefaultDelay() {}"
//
//        "//不推荐使用，推荐getScaledFadingEdgeLength()代替；褪去边缘的长度"
//        "public static int getFadingEdgeLength() {}"
//        "public int getScaledFadingEdgeLength() {}"
//
//        "//按下的持续时间长度"
//        "public static int getPressedStateDuration() {}"
//
//        "//按住状态转变为长按状态需要的时间"
//        "public static int getLongPressTimeout() {}"
//
//        "//重新按键判断时间"
//        "public static int getKeyRepeatTimeout() {}"
//
//        "//重复按键延迟的时间"
//        "public static int getKeyRepeatDelay() {}"
//
//        "//判断是单击还是滚动的时间，在这个时间内没有移动则是单击，否则是滚动"
//        "public static int getTapTimeout() {}"
//
//        "//在这个时间内没有完成这个点击，那么就认为是一个点击事件"
//        "public static int getJumpTapTimeout() {}"
//
//        "//得到双击间隔时间，在这个时间内是双击，否则是单击"
//        "public static int getDoubleTapTimeout() {}"
//
//        "//不推荐使用，推荐getScaledEdgeSlop()代替；判断是否滑动事件"
//        "public static int getEdgeSlop() {}"
//        "public int getScaledEdgeSlop() {}"
//
//        "//不推荐使用，推荐getScaledTouchSlop()代替；滑动的时候，手的移动要大于这个距离才算移动
//        "public static int getTouchSlop() {}
//        "public int getScaledTouchSlop() {}
//
//        "//触摸边沿padding区域的判断
//        "public int getScaledPagingTouchSlop() {}
//
//        "//不推荐使用，推荐getScaledDoubleTapSlop()代替；判断是否双击的阈值
//        "public static int getDoubleTapSlop() {}
//        "public int getScaledDoubleTapSlop() {}
//
//        "//不推荐使用，推荐getScaledWindowTouchSlop()代替；触摸窗体边沿区域判断
//        "public static int getWindowTouchSlop() {}
//        "public int getScaledWindowTouchSlop() {}
//
//        "//不推荐使用，推荐getScaledMinimumFlingVelocity()代替；得到滑动的最小速度, 以像素/每秒来进行计算
//        "public static int getMinimumFlingVelocity() {}
//        "public int getScaledMinimumFlingVelocity() {}
//
//        "//不推荐使用，推荐getScaledMaximumFlingVelocity()代替；得到滑动的最大速度, 以像素/每秒来进行计算
//        "public static int getMaximumFlingVelocity() {}
//        "public int getScaledMaximumFlingVelocity() {}
//
//        ""//获取最大的图形可缓存大小，单位bytes
//        "public int getScaledMaximumDrawingCacheSize() {}

        tv.setText(sb.toString());
        setContentView(tv);
    }
}