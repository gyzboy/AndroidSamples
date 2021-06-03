package com.gyz.androidsamples.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

        //DecorView : Window上的根视图，ViewTree中的根，最顶层视图
        //ContentView: 客户端程序员定义的所有视图的父节点，如Actvity中常见的setContentView(view)

        //View rootView = ((ViewGroup) (getWindow().getDecorView().findViewById(android.R.id.content))).getChildAt(0);


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

        //drawingCache可用来获取ImageView对象中的图像，在保存数据的时候，再次从该ImageView对象中获取图像，然后比较先后所得到的像个图像是否一致，并进行进一步的处理。
//        ImageView iv_drawingcache = (ImageView)findViewById(R.id.iv_drawingcache);
//        iv_drawingcache.setDrawingCacheEnabled(true);//使用drawingcache之前一定要先调用这个方法
//        Bitmap bm = Bitmap.createBitmap(iv_drawingcache.getDrawingCache());
//        iv_drawingcache.setDrawingCacheEnabled(false);//从ImaggeView对象中获取图像后，要记得调用setDrawingCacheEnabled(false)清空画图缓冲区，否则，下一次用getDrawingCache()方法回去图像时，还是原来的图像

        int count = getViewTree(getWindow().getDecorView().getRootView());
        System.out.println("gyzzz" + count);
    }

    public int getViewTree(View rootView){
        int count = 0;
        if (rootView instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) rootView;
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i) instanceof ViewGroup) {
                    count += getViewTree(group.getChildAt(i));
                }else{
                    count++;
                }
            }
        }
        return count;
    }
}
