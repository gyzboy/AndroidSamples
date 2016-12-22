package com.gyz.androidsamples.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/1.
 * 邮箱:gyzboy@126.com
 */
public class ASImageView extends Activity {

//    android:adjustViewBounds
//    设置该属性为真可以在 ImageView 调整边界时保持图片的纵横比例。（注：需要与maxWidth、MaxHeight一起使用，否则单独使用没有效果。）
//
//    android:baseline
//    视图内基线的偏移量
//
//    android:baselineAlignBottom
//    如果为true，图像视图将基线与父控件底部边缘对齐。
//
//    android:cropToPadding
//    如果为真，会剪切图片以适应内边距的大小。（注：是否截取指定区域用空白代替。单独设置无效果，需要与scrollY一起使用）
//
//    android:maxHeight
//    为视图提供最大高度的可选参数。（注：单独使用无效，需要与setAdjustViewBounds一起使用。如果想设置图片固定大小，又想保持图片宽高比，需要如下设置：
//    1） 设置setAdjustViewBounds为true；
//    2） 设置maxWidth、MaxHeight；
//    3） 设置设置layout_width和layout_height为wrap_content。）
//
//    android:maxWidth
//    为视图提供最大宽度的可选参数。
//
//    android:tint
//    为图片设置着色颜色。将图片渲染成指定的颜色。


//    setImageResource\setImageURI
//    该操作读取位图，并在 UI 线程中解码，因此可能导致反应迟缓。 如果反应迟缓，可以考虑用 setImageDrawable(Drawable)、 setImageBitmap(Bitmap) 或者 BitmapFactory 代替

    private static final String sModeExampleNames[] = {
            "PorterDuff.Mode.SRC", "PorterDuff.Mode.CLEAR", "PorterDuff.Mode.DST", "PorterDuff.Mode.SRC_OVER",
            "PorterDuff.Mode.DST_OVER", "PorterDuff.Mode.SRC_IN", "PorterDuff.Mode.DST_IN", "PorterDuff.Mode.SRC_OUT",
            "PorterDuff.Mode.DST_OUT", "PorterDuff.Mode.SRC_ATOP", "PorterDuff.Mode.DST_ATOP", "PorterDuff.Mode.XOR", "PorterDuff.Mode.DARKEN", "PorterDuff.Mode.LIGHTEN"
            , "PorterDuff.Mode.MULTIPLY", "PorterDuff.Mode.SCREEN"
    };

    private static final PorterDuff.Mode sModeNames[] = {
            PorterDuff.Mode.SRC, PorterDuff.Mode.CLEAR, PorterDuff.Mode.DST, PorterDuff.Mode.SRC_OVER,
            PorterDuff.Mode.DST_OVER, PorterDuff.Mode.SRC_IN, PorterDuff.Mode.DST_IN, PorterDuff.Mode.SRC_OUT,
            PorterDuff.Mode.DST_OUT, PorterDuff.Mode.SRC_ATOP, PorterDuff.Mode.DST_ATOP, PorterDuff.Mode.XOR, PorterDuff.Mode.DARKEN, PorterDuff.Mode.LIGHTEN
            , PorterDuff.Mode.MULTIPLY, PorterDuff.Mode.SCREEN
    };

    private static final String sModeIntro[] = {"显示上层绘制图片","所绘制不会提交到画布上","显示下层绘制图片","正常绘制显示，上下层绘制叠盖"
    ,"上下层都显示。下层居上显示","取两层绘制交集。显示上层","取两层绘制交集。显示下层","取上层绘制非交集部分","取下层绘制非交集部分","取下层非交集部分与上层交集部分"
            ,"取上层非交集部分与下层交集部分","异或：去除两图层交集部分","取两图层全部区域，交集部分颜色加深","取两图层全部，点亮交集部分颜色","取两图层交集部分叠加后颜色"
            ,"取两图层全部区域，交集部分变为透明色"
    };

    private Spinner mSpinner;

    private PorterDuff.Mode mode;
    private TextView tv_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sModeExampleNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = sModeNames[position];
                tv_intro.setText(sModeIntro[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ImageButton iv_colorFilter = (ImageButton) findViewById(R.id.btn_colorFilter);
        iv_colorFilter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((ImageButton) v).setColorFilter(Color.YELLOW, mode);//添加滤镜
                        break;
                    default:
                        ((ImageButton) v).clearColorFilter();
                        break;
                }
                return false;
            }
        });
    }
}
