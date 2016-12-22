package com.gyz.androidsamples.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gyz.androidsamples.R;
import com.gyz.androidsamples.custom.MatrixImageView;

/**
 * Created by guoyizhe on 16/9/27.
 * 邮箱:gyzboy@126.com
 */

public class ASMatrix extends Activity {

    private MatrixImageView image;


    //     [a b c]       [X]       [X1]
    // A = [d e f]   C = [Y]   R = [Y1]  = AC
    //     [g h i]       [L]       [L]

    //    a和e 控制缩放变换
    //    b和d 控制错切变换
    //    c和f 控制平移变换
    //    a，b，c，d 共同控制旋转变换

    //每个像素点的计算方法:
    //X1 = a * x + b * y + c
    //Y1 = d * x + e * y + c
    //L = g * x + h * y + c

    //一般,g = h = 0 , i = 1 , 这时L = g * x + h * y + i,L = i = 1
    //Matrix的初始化矩阵,对角线为1,其余为0

    //Matrix主要对图像做4中基本变换,主要作用对象是Bitmap,而不是Canvas


    //  Translate   平移变换
    //  [X]     [1   0   x][X0]
    //  [Y]  =  [0   1   y][Y0]
    //  [1]     [0   0   1][1]


    //  Rotate 旋转变换
    //  [X]     [cosX  -sinX  0][X0]
    //  [Y]  =  [sinX   cosX  0][Y0]
    //  [1]     [0       0    1][1]


    //  Scale 缩放变换
    //  [X]     [k1  0  0][X0]
    //  [Y]  =  [0  k1  0][Y0]
    //  [1]     [0  0   1][1]


    //  Skew 错切变换
    //  [X]     [1  k1  0][X0]
    //  [Y]  =  [k2  1  0][Y0]
    //  [1]     [0  0   1][1]


    //set 方法会重置矩阵中的所有值
    //pre前乘 对应线代中的右乘
    //post后乘 对应线代中的左乘

    //matrix.set(m1);               //matrix = m1
//    matrix.preConcat(m2);   //matrix = matrix * m2 = m1 * m2;
//    matrix.postConcat(m3); //matrix = m3 * matrix = m 3 * (m1* m2) = m3 * m1 *m2;

//    matrix.setConcat(m1, m2);      // matrix = m1 * m2;
//    matrix.setConcat(m3, matrix); //matrix = m3 * matrix = m3* (m1* m2) = m3 * m1 * m2;


    //m1.mapRect(m2,m3)     m1为要变化的矩阵  m3为变化的最终矩阵  m2为变化过程中进行相乘的矩阵

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_matrix);
        image = (MatrixImageView) findViewById(R.id.image);

        Button btn1 = (Button) findViewById(R.id.button1);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRect();
            }
        });

        Button btn2 = (Button) findViewById(R.id.button2);


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.reset();
            }
        });

    }

    //平移
    private void testTranslate() {
        Matrix matrix = new Matrix();
        int width = 50;
        int height = 50;
        matrix.setTranslate(width, height);
        matrix.preTranslate(width, height);
//        matrix.postTranslate(width, height);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    //围绕图片中心点旋转
    private void testRotate() {
        Matrix matrix = new Matrix();
        int width = image.getWidth() - image.getPaddingLeft() - image.getPaddingRight();
        int height = image.getHeight() - image.getPaddingTop() - image.getPaddingBottom();
        matrix.postRotate(45f, width / 2, height / 2);//默认左上角为旋转中心
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    //围绕原点旋转后平移
    //注意以下三行代码的执行顺序:
    //matrix.setRotate(45f);
    //matrix.preTranslate(-width, -height);
    //matrix.postTranslate(width, height);
    //先执行matrix.preTranslate(-width, -height);
    //后执行matrix.setRotate(45f);
    //再执行matrix.postTranslate(width, height);
    private void testRotateAndTranslate() {
        Matrix matrix = new Matrix();
        int width = image.getWidth();
        int height = image.getHeight();
        matrix.setRotate(45f);
        matrix.preTranslate(-width, -height);
        matrix.postTranslate(width, height);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    //缩放
    private void testScale() {
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    //水平倾斜
    private void testSkewX() {
        Matrix matrix = new Matrix();
        matrix.setSkew(0.5f, 0);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    // 垂直倾斜
    private void testSkewY() {
        Matrix matrix = new Matrix();
        matrix.setSkew(0, 0.5f);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    // 水平且垂直倾斜
    private void testSkewXY() {
        Matrix matrix = new Matrix();
        matrix.setSkew(0.5f, 0.5f);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    // 水平对称--图片关于X轴对称
    private void testSymmetryX() {
        Matrix matrix = new Matrix();
        int height = 100;
        float matrixValues[] = {1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
        matrix.setValues(matrixValues);
        //若是matrix.postTranslate(0, height);
        //表示将图片上下倒置
        matrix.postTranslate(0, height * 2);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    // 垂直对称--图片关于Y轴对称
    private void testSymmetryY() {
        Matrix matrix = new Matrix();
        int width = 100;
        float matrixValues[] = {-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
        matrix.setValues(matrixValues);
        //若是matrix.postTranslate(width,0);
        //表示将图片左右倒置
        matrix.postTranslate(width * 2, 0);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);

    }

    // 关于X=Y对称--图片关于X=Y轴对称
    private void testSymmetryXY() {
        Matrix matrix = new Matrix();
        int width = 100;
        int height = 100;
        float matrixValues[] = {0f, -1f, 0f, -1f, 0f, 0f, 0f, 0f, 1f};
        matrix.setValues(matrixValues);
        matrix.postTranslate(width + height, width + height);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    //通过改变参数，除了可以实现 平移，旋转，缩放，错切 ，还可以实现透视,这个方法主要是利用确定矩形4个顶点，根据4个顶点坐标的变化来对 bitmap 进行变换
    private void testPoly() {
        //最终的效果主要由 src 和 dst 两个数组进行控制，两个数组控制4个顶点的坐标， srcIndex,dstIndex 分别是 src 和 dst 的第一个值的角标，
        // pointCount 是4个顶点中要使用的个数，最大为4,0表示不进行操作变换
        //float[] dst = {f0, f1, f3, f3, f4, f5,f6, f7}  对应的顶点
        //左上 (f0,f1) 左下 (f2,f3) 右下 (f4,f5) 右上 (f6,f7)
        //dst 可以看做是底板，最终要显示的效果；
        //src 可以看做是要截取的 bitmap 的要显示的有效区域

        Matrix matrix = new Matrix();
        float bWidth = image.getWidth();
        float bHeight = image.getHeight();
        float[] src = {0, 0, 0, bHeight, bWidth, bHeight, bWidth, 0};
        float[] dst = {0 + 150, 0, 0, bHeight, bWidth, bHeight, bWidth - 150, 0};
        matrix.setPolyToPoly(src, 0, dst, 0, 4);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    //裁剪
    private void testRect() {
        //FILL: 可能会变换矩形的长宽比，保证变换和目标矩阵长宽一致。
        //START:保持坐标变换前矩形的长宽比，并最大限度的填充变换后的矩形。至少有一边和目标矩形重叠。左上对齐。
        //CENTER: 保持坐标变换前矩形的长宽比，并最大限度的填充变换后的矩形。至少有一边和目标矩形重叠。
        //END:保持坐标变换前矩形的长宽比，并最大限度的填充变换后的矩形。至少有一边和目标矩形重叠。右下对齐。

        Matrix matrix = new Matrix();
        float bWidth = image.getWidth();
        float bHeight = image.getHeight();
        RectF src = new RectF(0, 0, bWidth / 2, bHeight / 2);
        RectF dst = new RectF(0, 0, bWidth, bHeight);
        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.END);
        image.setImageMatrix(matrix);
        showMatrixEveryValue(matrix);
    }

    //获取变换矩阵Matrix中的每个值
    private void showMatrixEveryValue(Matrix matrix) {
        float matrixValues[] = new float[9];
        matrix.getValues(matrixValues);
        for (int i = 0; i < 3; i++) {
            String valueString = "";
            for (int j = 0; j < 3; j++) {
                valueString = matrixValues[3 * i + j] + "";
                System.out.println("第" + (i + 1) + "行的第" + (j + 1) + "列的值为" + valueString);
            }
        }
    }
}

