package com.gyz.androidsamples.view;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Created by guoyizhe on 16/7/29.
 * 邮箱:gyzboy@126.com
 */
public class    ASSurfaceView extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SurfaceViewTest(this));
    }

    /**
     * 1. SurfaceView允许其他线程更新视图对象(执行绘制方法)而View不允许这么做，它只允许UI线程更新视图对象。
     * 2. SurfaceView是放在其他最底层的视图层次中，所有其他视图层都在它上面，所以在它之上可以添加一些层，而且它不能是透明的。
     * 3. 它执行动画的效率比View高，而且你可以控制帧数。
     * 4. 因为它的定义和使用比View复杂，占用的资源也比较多，除非使用View不能完成，再用SurfaceView否则最好用View就可以。(贪吃蛇，俄罗斯方块，棋牌类这种帧数比较低的可以使用View做就好)
     *
     */
    public class SurfaceViewTest extends SurfaceView implements Callback ,SurfaceHolder.Callback2{

        LoopThread thread;

        public SurfaceViewTest(Context context) {
            super(context);

            init(); // 初始化,设置生命周期回调方法

        }

        private void init() {

            SurfaceHolder holder = getHolder();// SurfaceHolder保存了对SurfaceView的引用，允许你控制surface的大小、像素、格式等
            holder.addCallback(this); // 设置Surface生命周期回调
            holder.setFixedSize(300,300);
            holder.setFormat(PixelFormat.OPAQUE);//默认的
            holder.setKeepScreenOn(true);
            thread = new LoopThread(holder, getContext());
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        /**
         * 当SurfaceView显示时调用的方法，在此开启绘制线程
         */
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            thread.isRunning = true;
            thread.start();
        }

        /**
         * SurfaceDestroyed方法是当SurfaceView被隐藏会销毁时调用的方法，在这里你可以关闭绘制的线程
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            thread.isRunning = false;
            try {
                //“等待该线程终止。”解释一下，是主线程等待子线程的终止。
                //也就是在子线程调用了join()方法后面的代码，只有等到子线程结束了才能执行
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 当SurfaceView需要重绘时调用
         * @param holder
         */
        @Override
        public void surfaceRedrawNeeded(SurfaceHolder holder) {

        }

        /**
         * 执行绘制的绘制线程
         *
         * @author Administrator
         */
        class LoopThread extends Thread {

            SurfaceHolder surfaceHolder;
            Context context;
            boolean isRunning;
            float radius = 10f;
            Paint paint;

            public LoopThread(SurfaceHolder surfaceHolder, Context context) {

                this.surfaceHolder = surfaceHolder;
                this.context = context;
                isRunning = false;

                paint = new Paint();
                paint.setColor(Color.YELLOW);
                paint.setStyle(Paint.Style.STROKE);
            }

            @Override
            public void run() {

                Canvas c = null;

                while (isRunning) {

                    try {
                        //因为SurfaceView允许自定义的线程操作Surface对象执行绘制方法，而你可能同时定义多个线程执行绘制，
                        //所以当你获取 SurfaceHolder中的Canvas对象时记得加同步操作，避免两个不同的线程同时操作同一个Canvas对象，
                        //当操作完成后记得调用 SurfaceHolder.unlockCanvasAndPost方法释放掉Canvas锁。
                        synchronized (surfaceHolder) {

                            c = surfaceHolder.lockCanvas(null);// 加上画布锁
                            doDraw(c);
                            // 通过它来控制帧数执行一次绘制后休息50ms
                            Thread.sleep(50);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }

                }

            }

            public void doDraw(Canvas c) {

                // 这个很重要，清屏操作，清楚掉上次绘制的残留图像
                c.drawColor(Color.BLACK);

                c.translate(200, 200);
                c.drawCircle(0, 0, radius++, paint);

                if (radius > 100) {
                    radius = 10f;
                }

            }

        }

    }
}
