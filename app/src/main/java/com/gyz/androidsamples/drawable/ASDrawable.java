package com.gyz.androidsamples.drawable;

/**
 * Created by guoyizhe on 16/8/16.
 * 邮箱:gyzboy@126.com
 */
public class ASDrawable {
    //直接子类
    //AnimatedVectorDrawable, BitmapDrawable, ColorDrawable, DrawableContainer(包含AD,LLD,SLD), DrawerArrowDrawable,
    //GradientDrawable, LayerDrawable, NinePatchDrawable, PictureDrawable, RoundedBitmapDrawable,
    // ShapeDrawable, VectorDrawable


    //间接子类
    //AnimatedStateListDrawable, AnimationDrawable, ClipDrawable, InsetDrawable, LevelListDrawable,
    //PaintDrawable, RippleDrawable, RotateDrawable, ScaleDrawable, StateListDrawable, TransitionDrawable


//    aapt在打包资源文件的时候，会将图片资源进行相应的压缩以便占有更少的内存，例如不需要一个超过256色的真彩色的png图片
//    系统会自动将其转为8bit的png，如果不想让系统转化，可以将资源文件放到raw文件夹下


    //大部分控件的setDrawable或者类似设置drawable的方法，内部都会调用各种drawable的onDraw方法，所以具体的绘制实现都在各种
    //drawable的onDraw方法中(这是在练习scaledrawable时发现的)

    //Android 系统为了减少内存消耗，将应用中所用到的相同 drawable （可以理解为相同资源）共享同一个 state，并称之为 constant state。这里用图表演示一下，
    // 两个 View 加载同一个图片资源，创建两个 drawables 对象，但是共享同一个 constant state
}
