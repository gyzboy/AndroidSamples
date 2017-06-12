package com.gyz.androidsamples.view.drawtools;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
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


    private TextPaint paint;
    private float density = getResources().getDisplayMetrics().density;
    private float textHeight;

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



    //绘制坐标系
    private void drawAxis(Canvas canvas){
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(6 * density);

        //用绿色画x轴，用蓝色画y轴

        //第一次绘制坐标轴
        paint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, paint);//绘制x轴
        paint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, paint);//绘制y轴

        //对坐标系平移后，第二次绘制坐标轴
        canvas.translate(canvasWidth / 4, canvasWidth /4);//把坐标系向右下角平移
        paint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, paint);//绘制x轴
        paint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, paint);//绘制y轴

        //再次平移坐标系并在此基础上旋转坐标系，第三次绘制坐标轴
        canvas.translate(canvasWidth / 4, canvasWidth / 4);//在上次平移的基础上再把坐标系向右下角平移
        canvas.rotate(30);//基于当前绘图坐标系的原点旋转坐标系
        paint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, paint);//绘制x轴
        paint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, paint);//绘制y轴
    }

    private void drawARGB(Canvas canvas){
        canvas.drawARGB(255, 139, 197, 186);
    }

    private void drawText(Canvas canvas){
        int canvasWidth = canvas.getWidth();
        int halfCanvasWidth = canvasWidth / 2;
        float translateY = textHeight;

        //绘制正常文本
        canvas.save();
        canvas.translate(0, translateY);
        canvas.drawText("正常绘制文本", 0, 0, paint);
        canvas.restore();
        translateY += textHeight * 2;

        //绘制绿色文本
        paint.setColor(0xff00ff00);//设置字体为绿色
        canvas.save();
        canvas.translate(0, translateY);//将画笔向下移动
        canvas.drawText("绘制绿色文本", 0, 0, paint);
        canvas.restore();
        paint.setColor(0xff000000);//重新设置为黑色
        translateY += textHeight * 2;

        //设置左对齐
        paint.setTextAlign(Paint.Align.LEFT);//设置左对齐
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.drawText("左对齐文本", 0, 0, paint);
        canvas.restore();
        translateY += textHeight * 2;

        //设置居中对齐
        paint.setTextAlign(Paint.Align.CENTER);//设置居中对齐
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.drawText("居中对齐文本", 0, 0, paint);
        canvas.restore();
        translateY += textHeight * 2;

        //设置右对齐
        paint.setTextAlign(Paint.Align.RIGHT);//设置右对齐
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.drawText("右对齐文本", 0, 0, paint);
        canvas.restore();
        paint.setTextAlign(Paint.Align.LEFT);//重新设置为左对齐
        translateY += textHeight * 2;

        //设置下划线
        paint.setUnderlineText(true);//设置具有下划线
        canvas.save();
        canvas.translate(0, translateY);
        canvas.drawText("下划线文本", 0, 0, paint);
        canvas.restore();
        paint.setUnderlineText(false);//重新设置为没有下划线
        translateY += textHeight * 2;

        //绘制加粗文字
        paint.setFakeBoldText(true);//将画笔设置为粗体
        canvas.save();
        canvas.translate(0, translateY);
        canvas.drawText("粗体文本", 0, 0, paint);
        canvas.restore();
        paint.setFakeBoldText(false);//重新将画笔设置为非粗体状态
        translateY += textHeight * 2;

        //文本绕绘制起点顺时针旋转
        canvas.save();
        canvas.translate(0, translateY);
        canvas.rotate(20);
        canvas.drawText("文本绕绘制起点旋转20度", 0, 0, paint);
        canvas.restore();
    }

    private void drawPoint(Canvas canvas){
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int x = canvasWidth / 2;
        int deltaY = canvasHeight / 3;
        int y = deltaY / 2;
        paint.setColor(0xff8bc5ba);//设置颜色
        paint.setStrokeWidth(50 * density);//设置线宽，如果不设置线宽，无法绘制点

        //绘制Cap为BUTT的点
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(x, y, paint);

        //绘制Cap为ROUND的点
        canvas.translate(0, deltaY);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(x, y, paint);

        //绘制Cap为SQUARE的点
        canvas.translate(0, deltaY);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(x, y, paint);
    }

    private void drawLine(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        int canvasWidth = canvas.getWidth();
        int halfWidth = canvasWidth / 2;
        int deltaY = canvas.getHeight() / 5;
        int halfDeltaY = deltaY / 2;
        float[] pts = {
            50,0,halfWidth,halfDeltaY,
            halfWidth,halfDeltaY,canvasWidth-50,0
        };

        //绘制一条线段
        canvas.drawLine(5, 0, canvasWidth - 50, deltaY /2, paint);

        //绘制折线
        canvas.save();
        canvas.translate(0, deltaY);
        canvas.drawLines(pts, paint);
        canvas.restore();

        //设置线宽
        paint.setStrokeWidth(10 * density);

        //输出默认Cap
        Paint.Cap defaultCap = paint.getStrokeCap();

        //用BUTT作为Cap
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.save();
        canvas.translate(0, deltaY * 2);
        canvas.drawLine(50, 0, halfWidth, 0, paint);
        canvas.restore();

        //用ROUND作为Cap
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.save();
        canvas.translate(0, deltaY * 2 + 20 * density);
        canvas.drawLine(50, 0, halfWidth, 0, paint);
        canvas.restore();

        //用SQUARE作为Cap
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.save();
        canvas.translate(0, deltaY * 2 + 40 * density);
        canvas.drawLine(50, 0, halfWidth, 0, paint);
        canvas.restore();

        //恢复为默认的Cap
        paint.setStrokeCap(defaultCap);
    }

    private void drawRect(Canvas canvas){
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        //默认画笔的填充色是黑色
        int left1 = 10;
        int top1 = 10;
        int right1 = canvasWidth / 3;
        int bottom1 = canvasHeight /3;
        canvas.drawRect(left1, top1, right1, bottom1, paint);

        //修改画笔颜色
        paint.setColor(0xff8bc5ba);//A:ff,R:8b,G:c5,B:ba
        int left2 = canvasWidth / 3 * 2;
        int top2 = 10;
        int right2 = canvasWidth - 10;
        int bottom2 = canvasHeight / 3;
        canvas.drawRect(left2, top2, right2, bottom2, paint);
    }

    private void drawCircle(Canvas canvas){
        paint.setColor(0xff8bc5ba);//设置颜色
        paint.setStyle(Paint.Style.FILL);//默认绘图为填充模式

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int halfCanvasWidth = canvasWidth / 2;
        int count = 3;
        int D = canvasHeight / (count + 1);
        int R = D / 2;

        //绘制圆
        canvas.translate(0, D / (count + 1));
        canvas.drawCircle(halfCanvasWidth, R, R, paint);

        //通过绘制两个圆形成圆环
        //1. 首先绘制大圆
        canvas.translate(0, D + D / (count + 1));
        canvas.drawCircle(halfCanvasWidth, R, R, paint);
        //2. 然后绘制小圆，让小圆覆盖大圆，形成圆环效果
        int r = (int)(R * 0.75);
        paint.setColor(0xffffffff);//将画笔设置为白色，画小圆
        canvas.drawCircle(halfCanvasWidth, R, r, paint);

        //通过线条绘图模式绘制圆环
        canvas.translate(0, D + D / (count + 1));
        paint.setColor(0xff8bc5ba);//设置颜色
        paint.setStyle(Paint.Style.STROKE);//绘图为线条模式
        float strokeWidth = (float)(R * 0.25);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(halfCanvasWidth, R, R, paint);
    }

    private void drawOval(Canvas canvas){
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        float quarter = canvasHeight / 4;
        float left = 10 * density;
        float top = 0;
        float right = canvasWidth - left;
        float bottom= quarter;
        RectF rectF = new RectF(left, top, right, bottom);

        //绘制椭圆形轮廓线
        paint.setStyle(Paint.Style.STROKE);//设置画笔为画线条模式
        paint.setStrokeWidth(2 * density);//设置线宽
        paint.setColor(0xff8bc5ba);//设置线条颜色
        canvas.translate(0, quarter / 4);
        canvas.drawOval(rectF, paint);

        //绘制椭圆形填充面
        paint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        canvas.translate(0, (quarter + quarter / 4));
        canvas.drawOval(rectF, paint);

        //画两个椭圆，形成轮廓线和填充色不同的效果
        canvas.translate(0, (quarter + quarter / 4));
        //1. 首先绘制填充色
        paint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        canvas.drawOval(rectF, paint);//绘制椭圆形的填充效果
        //2. 将线条颜色设置为蓝色，绘制轮廓线
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        paint.setColor(0xff0000ff);//设置填充色为蓝色
        canvas.drawOval(rectF, paint);//设置椭圆的轮廓线
    }

    private void drawArc(Canvas canvas){
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int count = 5;
        float ovalHeight = canvasHeight / (count + 1);
        float left = 10 * density;
        float top = 0;
        float right = canvasWidth - left;
        float bottom= ovalHeight;
        RectF rectF = new RectF(left, top, right, bottom);

        paint.setStrokeWidth(2 * density);//设置线宽
        paint.setColor(0xff8bc5ba);//设置颜色
        paint.setStyle(Paint.Style.FILL);//默认设置画笔为填充模式

        //绘制用drawArc绘制完整的椭圆
        canvas.translate(0, ovalHeight / count);
        canvas.drawArc(rectF, 0, 360, true, paint);

        //绘制椭圆的四分之一,起点是钟表的3点位置，从3点绘制到6点的位置
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, true, paint);

        //绘制椭圆的四分之一,将useCenter设置为false
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, false, paint);

        //绘制椭圆的四分之一，只绘制轮廓线
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, true, paint);

        //绘制带有轮廓线的椭圆的四分之一
        //1. 先绘制椭圆的填充部分
        paint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, true, paint);
        //2. 再绘制椭圆的轮廓线部分
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        paint.setColor(0xff0000ff);//设置轮廓线条为蓝色
        canvas.drawArc(rectF, 0, 90, true, paint);
    }

    private void drawPath(Canvas canvas){
        int canvasWidth = canvas.getWidth();
        int deltaX = canvasWidth / 4;
        int deltaY = (int)(deltaX * 0.75);

        paint.setColor(0xff8bc5ba);//设置画笔颜色
        paint.setStrokeWidth(4);//设置线宽

        /*--------------------------用Path画填充面-----------------------------*/
        paint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        Path path = new Path();
        //向Path中加入Arc
        RectF arcRecF = new RectF(0, 0, deltaX, deltaY);
        path.addArc(arcRecF, 0, 135);
        //向Path中加入Oval
        RectF ovalRecF = new RectF(deltaX, 0, deltaX * 2, deltaY);
        path.addOval(ovalRecF, Path.Direction.CCW);
        //向Path中添加Circle
        path.addCircle((float)(deltaX * 2.5), deltaY / 2, deltaY / 2, Path.Direction.CCW);
        //向Path中添加Rect
        RectF rectF = new RectF(deltaX * 3, 0, deltaX * 4, deltaY);
        path.addRect(rectF, Path.Direction.CCW);
        canvas.drawPath(path, paint);

        /*--------------------------用Path画线--------------------------------*/
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, deltaY * 2);
        Path path2 = path;
        canvas.drawPath(path2, paint);

        /*-----------------使用lineTo、arcTo、quadTo、cubicTo画线--------------*/
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, deltaY * 2);
        Path path3 = new Path();
        //用pointList记录不同的path的各处的连接点
        List<Point> pointList = new ArrayList<Point>();
        //1. 第一部分，绘制线段
        path3.moveTo(0, 0);
        path3.lineTo(deltaX / 2, 0);//绘制线段
        pointList.add(new Point(0, 0));
        pointList.add(new Point(deltaX / 2, 0));
        //2. 第二部分，绘制椭圆右上角的四分之一的弧线
        RectF arcRecF1 = new RectF(0, 0, deltaX, deltaY);
        path3.arcTo(arcRecF1, 270, 90);//绘制圆弧
        pointList.add(new Point(deltaX, deltaY / 2));
        //3. 第三部分，绘制椭圆左下角的四分之一的弧线
        //注意，我们此处调用了path的moveTo方法，将画笔的移动到我们下一处要绘制arc的起点上
        path3.moveTo(deltaX * 1.5f, deltaY);
        RectF arcRecF2 = new RectF(deltaX, 0, deltaX * 2, deltaY);
        path3.arcTo(arcRecF2, 90, 90);//绘制圆弧
        pointList.add(new Point((int)(deltaX * 1.5), deltaY));
        //4. 第四部分，绘制二阶贝塞尔曲线
        //二阶贝塞尔曲线的起点就是当前画笔的位置，然后需要添加一个控制点，以及一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path3.moveTo(deltaX * 1.5f, deltaY);
        //绘制二阶贝塞尔曲线
        path3.quadTo(deltaX * 2, 0, deltaX * 2.5f, deltaY / 2);
        pointList.add(new Point((int)(deltaX * 2.5), deltaY / 2));
        //5. 第五部分，绘制三阶贝塞尔曲线，三阶贝塞尔曲线的起点也是当前画笔的位置
        //其需要两个控制点，即比二阶贝赛尔曲线多一个控制点，最后也需要一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path3.moveTo(deltaX * 2.5f, deltaY / 2);
        //绘制三阶贝塞尔曲线
        path3.cubicTo(deltaX * 3, 0, deltaX * 3.5f, 0, deltaX * 4, deltaY);
        pointList.add(new Point(deltaX * 4, deltaY));

        //Path准备就绪后，真正将Path绘制到Canvas上
        canvas.drawPath(path3, paint);

        //最后绘制Path的连接点，方便我们大家对比观察
        paint.setStrokeWidth(10);//将点的strokeWidth要设置的比画path时要大
        paint.setStrokeCap(Paint.Cap.ROUND);//将点设置为圆点状
        paint.setColor(0xff0000ff);//设置圆点为蓝色
        for(Point p : pointList){
            //遍历pointList，绘制连接点
            canvas.drawPoint(p.x, p.y, paint);
        }
    }

    private void drawBitmap(Bitmap bitmap,Canvas canvas){
        //如果bitmap不存在，那么就不执行下面的绘制代码
        if(bitmap == null){
            return;
        }

        //直接完全绘制Bitmap
        canvas.drawBitmap(bitmap, 0, 0, paint);

        //绘制Bitmap的一部分，并对其拉伸
        //srcRect定义了要绘制Bitmap的哪一部分
        Rect srcRect = new Rect();
        srcRect.left = 0;
        srcRect.right = bitmap.getWidth();
        srcRect.top = 0;
        srcRect.bottom = (int)(0.33 * bitmap.getHeight());
        float radio = (float)(srcRect.bottom - srcRect.top)  / bitmap.getWidth();
        //dstRecF定义了要将绘制的Bitmap拉伸到哪里
        RectF dstRecF = new RectF();
        dstRecF.left = 0;
        dstRecF.right = canvas.getWidth();
        dstRecF.top = bitmap.getHeight();
        float dstHeight = (dstRecF.right - dstRecF.left) * radio;
        dstRecF.bottom = dstRecF.top + dstHeight;
        canvas.drawBitmap(bitmap, srcRect, dstRecF, paint);
    }
}

