package com.gyz.androidsamples.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoyizhe on 16/9/27.
 * 邮箱:gyzboy@126.com
 */

public class ASCustomView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //一个View显示在屏幕上需要经历三个流程：测量(onMeasure)，布局()，绘制
        final FlowLayout fl = new FlowLayout(this, null);
        final CustomView tv = new CustomView(this);
        tv.setBackgroundColor(Color.GRAY);
        fl.addView(tv);
        setContentView(fl);

        fl.post(new Runnable() {
            @Override
            public void run() {
                fl.setTouchDelegate(new TouchDelegate(new Rect(0,0,fl.getWidth() / 4,fl.getHeight() / 4),tv));
            }
        });

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ASCustomView.this,"layout clicked",Toast.LENGTH_SHORT).show();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ASCustomView.this,"clicked",Toast.LENGTH_SHORT).show();
            }
        });

    }
}

class CustomView extends View {

    private float currentX = 50;
    private float currentY = 50;

    private Paint mPaint;

    /**
     * 在代码中使用自定义控件必须实现此方法
     *
     * @param context
     */
    public CustomView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mPaint = new Paint();
    }

    /**
     * 要想在xml文件中使用自定义控件，必须实现此方法
     */
    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        TypedArray a = context.obtainStyledAttributes(attrs, null);
        int borderWidth = a.getInt(com.gyz.base.R.styleable.custom_boroderWidth, 0);
        //在TypedArray后调用recycle主要是为了缓存。当recycle被调用后，这就说明这个对象从现在可以被重用了。TypedArray 内部持有部分数组，它们缓存在Resources类中的静态字段中，这样就不用每次使用前都需要分配内存。
        a.recycle();
    }

    /**
     * 当绘制画面时调用
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String testString = "test";
        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(40);
        mPaint.setColor(Color.RED);
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect bounds = new Rect();
        mPaint.getTextBounds(testString, 0, testString.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(testString,getMeasuredWidth() / 2 - bounds.width() / 2, baseline, mPaint);
    }

    /**
     * 当控件的父元素正要放置该控件时调用. 父元素会问子控件一个问题，你想要用多大地方啊？”，
     * 然后传入两个参数――widthMeasureSpec和heightMeasureSpec.
     * <p>
     * 更好的方法是你传递View的高度和宽度到setMeasuredDimension方法里, 这样可以直接告诉父控件，需要多大地方放置子控件
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = measureHeight(heightMeasureSpec);
        int measuredWidth = measureWidth(widthMeasureSpec);

//        MeasureSpec是一个32位的int值，高2位为测量的模式，低30位为测量的大小。
//        测量的模式共有三种：
//
//        EXACTLY 精确模式，两种情况:
//        1.指定数值时，例如layout_width="100dp"
//        2.指定为match_parent

//        AT_MOST 最大值模式:
//        控件的layout_width，layout_height指定为wrap_content
//        控件大小一般会随着子空间的或内容的变化而变化，此时要求控件的尺寸只要不超过父类控件允许的最大尺寸即可

//        UNSPECIFIED 未指明模式:
//        不指定控件大小，View想多大就多大。
//
//        View类默认的onMeasure()方法只支持EXACTLY模式。自定义View时，需要重写onMeasure()方法后，才可以支持其他的模式。
// 假如想要自定义的控件支持wrap_content，就要在onMeasure()中告诉系统自定义控件wrap_content时的大小,否则wrap_content和match_parent效果是一样的。都是沾满全屏。
// 此时在Acitivity中拿到的MeasureView的大小和match_parent是一样的。

        // 必须实现，否则在运行时会产生异常
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureHeight(int measureSpec) {
        // 每个MeasureSpec代表一组宽度和高度的要求
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = 100;
        /**
         * 如果是AT_MOST，specSize 代表的是最大可获得的空间； 如果是EXACTLY，specSize 代表的是精确的尺寸；
         * 如果是UNSPECIFIED，对于控件尺寸来说，没有任何参考意义。
         */
        /**
         * 而当设置为 wrap_content时，容器传进去的是AT_MOST,
         * 表示子view的大小最多是多少，这样子view会根据这个上限来设置自己的尺寸。
         * 当子view的大小设置为精确值时，容器传入的是EXACTLY
         */
        if (specMode == MeasureSpec.AT_MOST) {

            result = Math.min(result, specSize);
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
//        return result;
        return 100;//这里为了测试
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        int result = 100;

        if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
//        return result;
        return 100;//这里为了测试

    }
}


class FlowLayout extends ViewGroup {

    private static final String TAG = "FlowLayout";


    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(
            ViewGroup.LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

    /**
     * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        Log.e(TAG, sizeWidth + "," + sizeHeight);

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        int lineWidth = 0;
        /**
         * 每一行的高度，累加至height
         */
        int lineHeight = 0;

        int cCount = getChildCount();

        // 遍历每个子元素
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到child的lp
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            lp.leftMargin = lp.topMargin = lp.rightMargin = lp.bottomMargin = 50;
            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;
            /**
             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行
             */
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(lineWidth, childWidth);// 取最大的
                lineWidth = childWidth; // 重新开启新行，开始记录
                // 叠加当前高度，
                height += lineHeight;
                // 开启记录下一行的高度
                lineHeight = childHeight;
            } else
            // 否则累加值lineWidth,lineHeight取最大高度
            {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight
                : height);

    }

    /**
     * 存储所有的View，按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的最大高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<View>();
        int cCount = getChildCount();
        // 遍历所有的孩子
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果已经需要换行
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0;// 重置行宽
                lineViews = new ArrayList<View>();
            }
            /**
             * 如果不需要换行，则累加
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }
        // 记录最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            // 每一行的所有的views
            lineViews = mAllViews.get(i);
            // 当前行的最大高度
            lineHeight = mLineHeight.get(i);

            Log.e(TAG, "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
            Log.e(TAG, "第" + i + "行， ：" + lineHeight);

            // 遍历当前行所有的View
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();

                //计算childView的left,top,right,bottom
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                Log.e(TAG, child + " , l = " + lc + " , t = " + t + " , r ="
                        + rc + " , b = " + bc);

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.rightMargin
                        + lp.leftMargin;
            }
            left = 0;
            top += lineHeight;
        }

    }
}
