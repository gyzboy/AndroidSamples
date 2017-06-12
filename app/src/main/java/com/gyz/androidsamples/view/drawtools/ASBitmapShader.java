package com.gyz.androidsamples.view.drawtools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.gyz.androidsamples.R;

/**
 * Created by gyzboy on 2017/6/6.
 */

public class ASBitmapShader extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundColor(Color.BLUE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        Paint paint = new Paint();
        //BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //CLAMP表示，当所画图形的尺寸大于Bitmap的尺寸的时候，会用Bitmap四边的颜色填充剩余空间。当所画图形的尺寸小于Bitmap的尺寸的时候,会对Bitmap进行裁剪

        //BitmapShader bitmapShader = new BitmapShader(bitmap, TileMode.REPEAT, TileMode.REPEAT);
        //REPEAT表示，当我们绘制的图形尺寸大于Bitmap尺寸时，会用Bitmap重复平铺整个绘制的区域。

        BitmapShader bitmapShader = new BitmapShader(bitmap, TileMode.MIRROR, TileMode.MIRROR);
        //与REPEAT类似，当绘制的图形尺寸大于Bitmap尺寸时，MIRROR也会用Bitmap重复平铺整个绘图区域，与REPEAT不同的是，两个相邻的Bitmap互为镜像。
        paint.setShader(bitmapShader);

        setContentView(new ShaderView(ASBitmapShader.this, bitmap, paint));
    }
}

class ShaderView extends View {

    Bitmap bitmap;
    Paint paint;

    public ShaderView(Context context, Bitmap bitmap, Paint paint) {
        super(context);
        init(context, bitmap, paint);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, Bitmap bitmap, Paint paint) {
        this.bitmap = bitmap;
        this.paint = paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, bitmap.getWidth() * 2, bitmap.getHeight() * 2, paint);
    }
}
