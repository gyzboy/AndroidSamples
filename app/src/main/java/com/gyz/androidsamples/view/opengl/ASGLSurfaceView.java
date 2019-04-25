package com.gyz.androidsamples.view.opengl;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.gyz.androidsamples.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class ASGLSurfaceView extends Activity {
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glsurface);
        mGLView = (GLSurfaceView) findViewById(R.id.gl_surface);
        //GLContext设置为OpenGLES2.0
        mGLView.setEGLContextClientVersion(2);
        //在setRenderer之前，可以调用以下方法来进行EGL设置
        //mGLView.setEGLConfigChooser();    //颜色、深度、模板等等设置
        //mGLView.setEGLWindowSurfaceFactory(); //窗口设置
        //mGLView.setEGLContextFactory();   //EGLContext设置
        //设置渲染器，渲染主要就是由渲染器来决定
        mGLView.setRenderer(new GLSurfaceView.Renderer() {

            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                //todo surface被创建后需要做的处理
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                //todo 渲染窗口大小发生改变的处理
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                //todo 执行渲染工作
            }
        });
        /*渲染方式，RENDERMODE_WHEN_DIRTY表示被动渲染，只有在调用requestRender或者onResume等方法时才会进行渲染。RENDERMODE_CONTINUOUSLY表示持续渲染*/
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }
}

class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context) {
        super(context);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer(GLSurfaceView.Renderer renderer) {
        super.setRenderer(renderer);
        //设置渲染器，这个非常重要，渲染工作就依靠渲染器了
        //调用此方法会开启一个新的线程，即GL线程
    }

    @Override
    public void setRenderMode(int renderMode) {
        super.setRenderMode(renderMode);
        //设置渲染方式，有RENDERMODE_CONTINUOUSLY表示不断渲染
        //以及RENDERMODE_WHEN_DIRTY表示在需要的时候才会渲染
        //渲染的时候要求调用requestRender，必须在setRenderer后调用
    }

    @Override
    public void setDebugFlags(int debugFlags) {
        super.setDebugFlags(debugFlags);
        //调试用
    }

    @Override
    public void setPreserveEGLContextOnPause(boolean preserveOnPause) {
        super.setPreserveEGLContextOnPause(preserveOnPause);
        //设置是否在pause时保持EGLContext
    }

    @Override
    public void setEGLContextFactory(EGLContextFactory factory) {
        super.setEGLContextFactory(factory);
        //设置EGLContext工厂,不设置就使用默认的
    }

    @Override
    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory factory) {
        super.setEGLWindowSurfaceFactory(factory);
        //设置EGLSurface工厂,不设置就使用默认的
    }

    @Override
    public void setEGLConfigChooser(EGLConfigChooser configChooser) {
        super.setEGLConfigChooser(configChooser);
        //设置EGLConfig,一般颜色深度等,不设置就使用默认
    }

    @Override
    public void setEGLContextClientVersion(int version) {
        super.setEGLContextClientVersion(version);
        //设置EGLContextVersion
    }

    @Override
    public void onPause() {
        super.onPause();
        //生命周期,一般在Acitvity\Fragment的onPause中调用
    }

    @Override
    public void onResume() {
        super.onResume();
        //生命周期,一般在Activity\Fragment的onResume中调用
    }

    @Override
    public void queueEvent(Runnable r) {
        super.queueEvent(r);
        //在主线程像GL线程发送一个任务
    }

    class MyRender implements Renderer{

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //surface初始化,通常渲染初始化在此
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //surface大小改变时调用
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //执行渲染时调用,完成用户渲染工作
        }
    }

    //外部请求退出GL线

    /*外部请求在GL线程中处理的事件没有处理完时，就优先处理这些事件*/

    //暂停和恢复状态变化时，onResume和onPause状态变化

    // 需要释放EglContext时候执行的工作

    // EglContext丢失时，销毁EglSurface和EglContext

    //接收了暂停信号，而且当前EglSurface存在时，销毁EglSurface

    /*接收了暂停信号，而且当前EglContext存在时，根据用户设置，来决定是否销毁EglContext*/


    // 当前环境准备好了渲染执行，否则进入下一轮等待及判断

        // 没有EglContext就需要借助EglHelper来创建EglContex

        /*有了EglContext,但是没有EglSurface，就需要设置一些状态，以便后续操作*/

        /*有eglSurface时，需要判断是否需要执行surface sizechange*/

    /*外部请求在GL线程中处理的事件没有处理完时，就优先处理这些事件*/
    //每帧绘制
}