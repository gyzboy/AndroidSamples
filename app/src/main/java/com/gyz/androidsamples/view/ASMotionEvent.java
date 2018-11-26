package com.gyz.androidsamples.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.gyz.androidsamples.R;

/**
 * Created by gyzboy on 2017/5/18.
 */

public class ASMotionEvent extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getActionMasked()	与 getAction() 类似，多点触控必须使用这个方法获取事件类型。
        //getActionIndex()	获取该事件是哪个指针(手指)产生的,只在 down 和 up 时有效，move 时是无效的
        //getPointerCount()	获取在屏幕上手指的个数。
        //getPointerId(int pointerIndex)	获取一个指针(手指)的唯一标识符ID，在手指按下和抬起之间ID始终不变。
        //findPointerIndex(int pointerId)	通过PointerId获取到当前状态下PointIndex，之后通过PointIndex获取其他内容。
        //getX(int pointerIndex)	获取某一个指针(手指)的X坐标
        //getY(int pointerIndex)	获取某一个指针(手指)的Y坐标

        //getDownTime()	获取手指按下时的时间。
        //getEventTime()	获取当前事件发生的事件。
        //getHistoricalEventTime(int pos)	获取历史事件发生的事件。
        setContentView(new MotionEventView(this));

    }

    static class MotionEventView extends android.support.v7.widget.AppCompatTextView {

        private StringBuilder sb = new StringBuilder();

        public MotionEventView(Context context) {
            super(context);
        }

        public MotionEventView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public MotionEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        float x;
        float y;

        @Override
        public boolean onTouchEvent(MotionEvent event) {


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //第一个 手指 初次接触到屏幕 时触发。
                    sb.append("actionDown------->\n");
                    x = event.getX();
                    y = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //手指 在屏幕上滑动 时触发，会多次触发

                    //历史数据只针对ACTION_MOVE而言
                    //getHistorySize()	获取历史事件集合大小
                    //getHistoricalX(int pos)	获取第pos个历史事件x坐标 (pos < getHistorySize())
                    //getHistoricalY(int pos)	获取第pos个历史事件y坐标 (pos < getHistorySize())
                    //getHistoricalX (int pin, int pos)	获取第pin个手指的第pos个历史事件x坐标 (pin < getPointerCount(), pos <
                    // getHistorySize() )
                    //getHistoricalY (int pin, int pos)	获取第pin个手指的第pos个历史事件y坐标

                    printSamples(event, sb);

                    if (event.getX() - x > 0) {
                        //向左滑
                    } else if (event.getX() - x < 0) {
                        //向右滑
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    //最后一个 手指 离开屏幕 时触发。
                    sb.append("actionUp------->\n");
                    setText(sb.toString());
                    sb.setLength(0);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    //有非主要的手指按下(即按下之前已经有手指在屏幕上)。
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    //有非主要的手指抬起(即抬起之后仍然有手指在屏幕上)。
                    break;
                case MotionEvent.ACTION_CANCEL:
                    //只有上层 View 回收事件处理权的时候，ChildView 才会收到一个 ACTION_CANCEL 事件
                    //比如,上层 View 是一个 RecyclerView，它收到了一个 ACTION_DOWN 事件，由于这个可能是点击事件，
                    // 所以它先传递给对应 ItemView，询问 ItemView 是否需要这个事件，然而接下来又传递过来了一个 ACTION_MOVE 事件，
                    // 且移动的方向和 RecyclerView 的可滑动方向一致，所以 RecyclerView 判断这个事件是滚动事件，于是要收回事件处理权，
                    // 这时候对应的 ItemView 会收到一个 ACTION_CANCEL ，并且不会再收到后续事件。

                    //父视图onInterceptTouchEvent (MotionEvent ev)先返回false，后返回true的情况下，子视图收不到后续的事件，而只是在父视图由返回false改成返回true（拦截）的时候收到ACTION_CANCEL事件
                    break;
                case MotionEvent.ACTION_OUTSIDE:
//当使用WindowManager动态的显示一个Modal视图时，可以在显示视图时，指定布局参数的flags为FLAG_WATCH_OUTSIDE_TOUCH，
// 这样当点击事件发生在这个Modal视图之外时，Modal视图就可以接收到ACTION_OUTSIDE事件
                    break;
                default:

                    break;
            }
            return true;
        }

        void printSamples(MotionEvent ev, StringBuilder sb) {
            final int historySize = ev.getHistorySize();
            final int pointerCount = ev.getPointerCount();
            for (int h = 0; h < historySize; h++) {
                sb.append("At time :" + ev.getHistoricalEventTime(h) + "\n");
                for (int p = 0; p < pointerCount; p++) {
                    sb.append("  pointer : " +
                            ev.getPointerId(p) + ev.getHistoricalX(p, h) + ev.getHistoricalY(p, h) + "\n");
                }
            }
            sb.append("At time :" + ev.getEventTime() + "\n");
            for (int p = 0; p < pointerCount; p++) {
                sb.append("  pointer:" +
                        ev.getPointerId(p) + ev.getX(p) + ev.getY(p) + "\n");
            }
        }
    }

}
