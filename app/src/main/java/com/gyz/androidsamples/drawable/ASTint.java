package com.gyz.androidsamples.drawable;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

public class ASTint extends Activity {

//    android:tint：给图标着色的属性，值为所要着色的颜色值，没有版本限制；通常用于给透明通道的 png 图标或者点九图着色。
//    android:tintMode：图标着色模式，值为枚举类型，共有 六种可选值（add、multiply、screen、src_over、src_in、src_atop），仅可用于 API 21 及更高版本。

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tint);
        ImageView mIvTint = (ImageView) findViewById(R.id.iv_tint);

        Drawable originalDrawable = mIvTint.getDrawable();
        //wrap()方法,为了在不同的系统 API 上使用 DrawableCompat.setTint() 做图标的着色处理，必须使用这个方法处理现有的 drawable 对象。并且，要将处理结果重新通过 setImageDrawable() 或者 setBackground() 赋值给 View 才能见效
        //mutate()方法,复制一份 constant state，允许你随意改变属性，同时不对其他 drawable 有任何影响
        Drawable tintDrawable = DrawableCompat.wrap(originalDrawable).mutate();
        DrawableCompat.setTint(tintDrawable, Color.BLUE);
        mIvTint.setImageDrawable(tintDrawable);
    }
}
