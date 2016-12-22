package com.gyz.androidsamples.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/6/28.
 * 邮箱:gyzboy@126.com
 */
public class ViewTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




//        1、 android:layout_alignBaseline：
//        相对布局（RalativeLayout）中使用，设置当前组件与参照组件的基线对齐，该属性为参照组件的ID。
//        如果作为基线的控件的内容为多行，则以第一行作为基线。
//        2、android:baselineAligned：
//        线性布局（LinearLayout）中使用，设置是否允许用户调整它内容的基线。默认为ture，即LinearLayout中的控件的内容是基线对齐的。
//        3、android:baselineAlignedChildIndex：
//        线性布局（LinearLayout）中使用，设置LinearLayout中第几个（从0开始计数）子组件作为基线对齐的控件，来和LinearLayout外的基线对齐。


//        ColorFilter设置颜色变化

        setContentView(R.layout.activity_view);

        //splitMotionEvents多点触控
        final Button rotatingButton = (Button) findViewById(R.id.rotatingButton);
        SeekBar seekBar;
        seekBar = (SeekBar) findViewById(R.id.translationX);
        seekBar.setMax(400);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                rotatingButton.setTranslationX((float) progress);
            }
        });
        seekBar = (SeekBar) findViewById(R.id.translationY);
        seekBar.setMax(800);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                rotatingButton.setTranslationY((float) progress);
            }
        });
        seekBar = (SeekBar) findViewById(R.id.scaleX);
        seekBar.setMax(50);
        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                rotatingButton.setScaleX((float) progress / 10f);
            }
        });
        seekBar = (SeekBar) findViewById(R.id.scaleY);
        seekBar.setMax(50);
        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                rotatingButton.setScaleY((float) progress / 10f);
            }
        });
        seekBar = (SeekBar) findViewById(R.id.rotationX);
        seekBar.setMax(360);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // prevent seeking on app creation
                rotatingButton.setRotationX((float) progress);
            }
        });
        seekBar = (SeekBar) findViewById(R.id.rotationY);
        seekBar.setMax(360);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // prevent seeking on app creation
                rotatingButton.setRotationY((float) progress);
            }
        });
        seekBar = (SeekBar) findViewById(R.id.rotationZ);
        seekBar.setMax(360);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // prevent seeking on app creation
                rotatingButton.setRotation((float) progress);
            }
        });

        ImageButton btn_ColorFilter = (ImageButton) findViewById(R.id.btn_colorFilter);
        btn_ColorFilter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ((ImageButton)v).setColorFilter(Color.YELLOW);
                        break;
                    default:
                        ((ImageButton)v).clearColorFilter();
                        break;
                }
                return false;
            }
        });
    }
}
