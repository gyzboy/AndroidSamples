package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/17.
 * 邮箱:gyzboy@126.com
 */
public class ASShapeDrawable extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * <shape
         xmlns:android="http://schemas.android.com/apk/res/android"
         android:shape=["rectangle" | "oval" | "line" | "ring"]
         android:innerRadius  当shape为ring时的内圈半径
         android:thickness    当shape为ring时的层数
         android:useLevel     当为true时表示作为levellist使用
         >
         <corners   圆角  只有shape为rectangle时起效
         android:radius="integer"
         android:topLeftRadius="integer"
         android:topRightRadius="integer"
         android:bottomLeftRadius="integer"
         android:bottomRightRadius="integer" />
         <gradient   渐变色
         android:angle="integer"  渐变方向,90为从下到上,270为从上到下
         android:centerX="integer"
         android:centerY="integer"
         android:centerColor="integer"
         android:endColor="color"
         android:gradientRadius="integer"
         android:startColor="color"
         android:type=["linear" | "radial" | "sweep"]
         android:useLevel=["true" | "false"] />
         <padding   内边距
         android:left="integer"
         android:top="integer"
         android:right="integer"
         android:bottom="integer" />
         <size   大小
         android:width="integer"
         android:height="integer" />  当使用imageview时，要使size起效需要设置scaleType为center
         <solid   内部填充色
         android:color="color" />
         <stroke  描边属性
         android:width="integer"
         android:color="color"
         android:dashWidth="integer"
         android:dashGap="integer" />
         </shape>
         */
        final TextView tv = new TextView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.VERTICAL);

        OvalShape os = new OvalShape();
        Drawable sd = getResources().getDrawable(R.drawable.shapegradient);
        sd.setLevel(3);
        tv.setText("3323342");
        tv.setCompoundDrawablesWithIntrinsicBounds(sd,null,sd,null);
        tv.setBackground(sd);
        tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//3.0以后默认开启了硬件加速,然而虚线的绘制需要关闭之

        layout.addView(tv);
        setContentView(layout);
    }
}
