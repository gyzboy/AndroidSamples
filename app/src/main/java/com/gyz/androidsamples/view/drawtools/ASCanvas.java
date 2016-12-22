package com.gyz.androidsamples.view.drawtools;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by guoyizhe on 2016/10/24.
 * 邮箱:gyzboy@126.com
 */

/**
 * Paint类介绍
 * <p>
 * Paint即画笔，在绘图过程中起到了极其重要的作用，画笔主要保存了颜色， 样式等绘制信息，指定了如何绘制文本和图形，画笔对象有很多设置方法，
 * 大体上可以分为两类，一类与图形绘制相关，一类与文本绘制相关。
 * <p>
 * 1.图形绘制 setARGB(int a,int r,int g,int b); 设置绘制的颜色，a代表透明度，r，g，b代表颜色值。
 * <p>
 * setAlpha(int a); 设置绘制图形的透明度。
 * <p>
 * setColor(int color); 设置绘制的颜色，使用颜色值来表示，该颜色值包括透明度和RGB颜色。
 * <p>
 * setAntiAlias(boolean aa); 设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。
 * <p>
 * setDither(boolean dither); 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
 * <p>
 * setFilterBitmap(boolean filter); 如果该项设置为true，则图像在动画进行中会滤掉对Bitmap图像的优化操作，加快显示
 * 速度，本设置项依赖于dither和xfermode的设置
 * <p>
 * setMaskFilter(MaskFilter maskfilter);
 * 设置MaskFilter，可以用不同的MaskFilter实现滤镜的效果，如滤化，立体等 * setColorFilter(ColorFilter
 * colorfilter); 设置颜色过滤器，可以在绘制颜色时实现不用颜色的变换效果
 * <p>
 * setPathEffect(PathEffect effect); 设置绘制路径的效果，如点画线等
 * <p>
 * setShader(Shader shader); 设置图像效果，使用Shader可以绘制出各种渐变效果
 * <p>
 * setShadowLayer(float radius ,float dx,float dy,int color);
 * 在图形下面设置阴影层，产生阴影效果，radius为阴影的角度，dx和dy为阴影在x轴和y轴上的距离，color为阴影的颜色
 * <p>
 * setStyle(Paint.Style style); 设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE
 * <p>
 * setStrokeCap(Paint.Cap cap); 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
 * Cap.ROUND,或方形样式Cap.SQUARE
 * <p>
 * setSrokeJoin(Paint.Join join); 设置绘制时各图形的结合方式，如平滑效果等
 * <p>
 * setStrokeWidth(float width); 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的粗细度
 * <p>
 * setXfermode(Xfermode xfermode); 设置图形重叠时的处理方式，如合并，取交集或并集，经常用来制作橡皮的擦除效果
 * <p>
 * 2.文本绘制 setFakeBoldText(boolean fakeBoldText); 模拟实现粗体文字，设置在小字体上效果会非常差
 * <p>
 * setSubpixelText(boolean subpixelText); 设置该项为true，将有助于文本在LCD屏幕上的显示效果
 * <p>
 * setTextAlign(Paint.Align align); 设置绘制文字的对齐方向
 * <p>
 * setTextScaleX(float scaleX); 设置绘制文字x轴的缩放比例，可以实现文字的拉伸的效果
 * <p>
 * setTextSize(float textSize); 设置绘制文字的字号大小
 * <p>
 * setTextSkewX(float skewX); 设置斜体文字，skewX为倾斜弧度
 * <p>
 * setTypeface(Typeface typeface); 设置Typeface对象，即字体风格，包括粗体，斜体以及衬线体，非衬线体等
 * <p>
 * setUnderlineText(boolean underlineText); 设置带有下划线的文字效果
 * <p>
 * setStrikeThruText(boolean strikeThruText); 设置带有删除线的效果
 */
public class ASCanvas extends View {
    public Paint mPaint;
    private Path mPath;
    // 定义一个内存中的图片，该图片将作为缓冲区
    private Bitmap cacheBitmap = null;
    // 定义cacheBitmap上的Canvas对象
    private Canvas cacheCanvas = null;
    float preX = 0;
    float preY = 0;

    /**
     * 为了实现保存用户之前绘制的内容使用双缓冲技术，程序先不绘制在view上，先绘制在一个内存中的bitmap上，
     * 当绘制完成后将bitmap绘制在view上
     *
     * @param context
     * @param attrs
     */
    public ASCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);// 父类构造器必须放到最前面
        mPaint = new Paint();
        mPath = new Path();
        cacheBitmap = Bitmap.createBitmap(320, 480, Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);// 将图片画到缓存中
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);// 反锯齿
        mPaint.setDither(true);// 与DITHER_FLAG对应，相当于开关
    }

    // @Override
    // protected void onDraw(Canvas canvas) {
    //
    // // 将整个桌布绘制成白色 canvas还有rotate旋转、scale缩放、skew倾斜、translate平移属性
    // canvas.drawColor(Color.WHITE);
    //
    // Paint paint = new Paint();
    // paint.setAntiAlias(true);// 去锯齿
    // paint.setColor(Color.BLUE);
    // paint.setStyle(Paint.Style.STROKE);// 设置为空心
    // paint.setStrokeWidth(5);
    //
    // /**
    // * 参数依次为x，y坐标，半径
    // */
    // canvas.drawCircle(40, 40, 30, paint);// 画圆
    // /**
    // * 同Rect
    // */
    // canvas.drawRect(40, 40, 140, 180, paint);// 绘制矩形
    // canvas.drawRect(10, 50, 60, 100, paint);// 正方形
    // /**
    // * 矩形左边的X坐标 150 矩形顶部的Y坐标 75 矩形右边的X坐标 260 矩形底部的Y坐标 120
    // * 说白了就是左上角的坐标是（150,75），右下角的坐标是（260,120）
    // */
    // RectF rf1 = new RectF(150, 20, 200, 120);// rect为整型,rectF为浮点数
    // /**
    // * x,y方向的圆化角度
    // */
    // canvas.drawRoundRect(rf1, 15, 50, paint);
    // RectF re11 = new RectF(10, 240, 70, 270);
    // // 绘制椭圆
    // canvas.drawOval(re11, paint);
    // /*
    // * 最重要的就是movtTo和close,如果是Style.FILL的话，不设置close,也没有区别，可是如果是STROKE模式，
    // * 如果不设置close,图形不封闭。
    // *
    // * 当然，你也可以不设置close，再添加一条线，效果一样。
    // */
    // Path path1 = new Path();
    // path1.moveTo(10, 340); // 路径绘制的开始点
    // path1.lineTo(70, 340);
    // path1.lineTo(40, 290);
    // path1.close();// 绘制完成，形成封闭图形,相当于画了一条连接到开头的直线
    // canvas.drawPath(path1, paint);
    //
    // /**
    // * 参数一为渐变起初点坐标x位置，参数二为y轴位置，参数三和四分辨对应渐变终点 其中参数new int[]{startColor,
    // * midleColor,endColor}是参与渐变效果的颜色集合， 其中参数new float[]{0 , 0.5f,
    // * 1.0f}是定义每个颜色处于的渐变相对位置， 这个参数可以为null，如果为null表示所有的颜色按顺序均匀的分布
    // */
    // // 为Paint设置渐变器
    // Shader mShader = new LinearGradient(0, 0, 40, 60, new int[] {
    // Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW }, null,
    // Shader.TileMode.REPEAT);
    // // Shader.TileMode三种模式
    // // REPEAT:沿着渐变方向循环重复
    // // CLAMP:如果在预先定义的范围外画的话，就重复边界的颜色
    // // MIRROR:与REPEAT一样都是循环重复，但这个会对称重复
    // paint.setShader(mShader);
    // // 设置阴影
    // paint.setShadowLayer(45, 10, 10, Color.GRAY);
    // paint.setStyle(Paint.Style.FILL);// 设置为实心
    // // 绘制圆形
    // canvas.drawCircle(200, 40, 30, paint);
    // // 绘制字体
    // paint.setTextSize(40);
    // paint.setShader(mShader);
    // canvas.drawText("Circle", 100, 200, paint);
    //
    // super.onDraw(canvas);
    // }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.quadTo(preX, preY, x, y);// 绘制曲线
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(mPath, mPaint);// 绘制
                // mPath.reset();//清空路径
                break;
        }
        invalidate();
        // 返回true表明处理方法已经处理该事件,否则会继续传递
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint bitmapPaint = new Paint();
        bitmapPaint.setColor(Color.BLACK);
        // 将cacheBitmap绘制到该View组件上
        canvas.drawBitmap(cacheBitmap, 50, 50, bitmapPaint);
        // 沿着path绘制
        canvas.drawPath(mPath, mPaint);
    }
}

