package com.gyz.androidsamples.view.drawtools;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.SumPathEffect;
import android.os.Bundle;
import android.view.View;

/**
 * Created by guoyizhe on 2016/10/24.
 * 邮箱:gyzboy@126.com
 */
public class ASPath extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(new MyView(this));
        setContentView(new MyTextView(this));
    }

    class MyView extends View {

        float phase;
        Path path;
        PathEffect[] effects;
        Paint paint;
        int[] colors;

        public MyView(Context context) {
            super(context);
            effects = new PathEffect[7];// 路径效果
            path = new Path();
            colors = new int[]{};
            paint = new Paint();

            paint.setColor(Color.BLUE);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);

            path.moveTo(0, 0);

            for (int i = 0; i < 15; i++) {
                // 生成15个点，随机生成它们的Y座标。并将它们连成一条Path
                path.lineTo(i * 20, (float) Math.random() * 60);
            }
            // 初始化7个颜色
            colors = new int[]{Color.BLACK, Color.BLUE, Color.CYAN,
                    Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW};
            // -----------下面开始初始化7中路径效果----------
            // 不使用路径效果。
            effects[0] = null;
            // 使用CornerPathEffect路径效果
            effects[1] = new CornerPathEffect(10);
            // 初始化DiscretePathEffect
            effects[2] = new DiscretePathEffect(3.0f, 5.0f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // 将背景填充成白色
            canvas.drawColor(Color.WHITE);
            // 初始化DashPathEffect，DashPathEffect有动画效果
            effects[3] = new DashPathEffect(new float[]{20, 10, 5, 10},
                    phase);
            // 初始化PathDashPathEffect，PathDashPathEffect有动画效果
            Path p = new Path();
            p.addRect(0, 0, 8, 8, Path.Direction.CCW);
            effects[4] = new PathDashPathEffect(p, 12, phase,
                    PathDashPathEffect.Style.ROTATE);
            // 初始化PathDashPathEffect
            effects[5] = new ComposePathEffect(effects[2], effects[4]);
            effects[6] = new SumPathEffect(effects[4], effects[3]);
            // 对Canvas执行坐标变换：将画布“整体位移”到8、8处开始绘制
            canvas.translate(8, 8);
            // 依次使用7中不同路径效果、7种不同的颜色来绘制路径
            for (int i = 0; i < effects.length; i++) {
                paint.setPathEffect(effects[i]);
                paint.setColor(colors[i]);
                canvas.drawPath(path, paint);
                canvas.translate(0, 60);// 移动画布为了显示出不同的路径效果
            }
            // 改变phase值，形成动画效果,变化速度
            phase += 1;
            invalidate();
        }

    }

    class MyTextView extends View {
        final String DRAW_STR = "来一个测试";
        Path[] paths = new Path[3];
        Paint paint;

        public MyTextView(Context context) {
            super(context);
            paths[0] = new Path();
            paths[0].moveTo(0, 0);
            for (int i = 1; i <= 7; i++) {
                // 生成7个点，随机生成它们的Y座标。并将它们连成一条Path
                paths[0].lineTo(i * 30, (float) Math.random() * 30);
            }
            paths[1] = new Path();
            RectF rectF = new RectF(0, 0, 200, 120);
            paths[1].addOval(rectF, Path.Direction.CCW);//逆时针
            paths[2] = new Path();
            paths[2].addArc(rectF, 60, 180);
            // 初始化画笔
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.CYAN);
            paint.setStrokeWidth(1);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            canvas.translate(40, 40);
            // 设置从右边开始绘制（右对齐）
            paint.setTextAlign(Paint.Align.RIGHT);
            paint.setTextSize(20);
            // 绘制路径
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths[0], paint);
            // 沿着路径绘制一段文本。
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRAW_STR, paths[0], -8, 20, paint);
            // 对Canvas进行坐标变换：画布下移120
            canvas.translate(0, 60);
            // 绘制路径
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths[1], paint);
            // 沿着路径绘制一段文本。
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRAW_STR, paths[1], -20, 20, paint);
            // 对Canvas进行坐标变换： 画布下移120
            canvas.translate(0, 120);
            // 绘制路径
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(paths[2], paint);
            // 沿着路径绘制一段文本。
            paint.setStyle(Paint.Style.FILL);
            canvas.drawTextOnPath(DRAW_STR, paths[2], -10, 20, paint);
        }
    }
}

