package com.gyz.androidsamples.application;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.Toast;

import com.gyz.androidopensamples.crashconsume.CCHandler;
import com.gyz.androidopensamples.onlinemonitor.ActivityLifecycleCallback;
import com.gyz.androidopensamples.onlinemonitor.LoadTimeCalculate;
import com.gyz.androidopensamples.onlinemonitor.OnLineMonitor;
import com.gyz.androidopensamples.onlinemonitor.OnLineMonitorApp;
import com.gyz.androidopensamples.onlinemonitor.SmoothCalculate;
import com.gyz.androidopensamples.utilCode.ProcessUtils;
import com.gyz.androidopensamples.weex.ImageAdapter;
import com.gyz.androidopensamples.weex.extend.PlayDebugAdapter;
import com.gyz.androidopensamples.weex.extend.component.RichText;
import com.gyz.androidopensamples.weex.extend.module.GeolocationModule;
import com.gyz.androidopensamples.weex.extend.module.MyModule;
import com.gyz.androidopensamples.weex.extend.module.RenderModule;
import com.gyz.androidopensamples.weex.extend.module.WXEventModule;
import com.gyz.androidsamples.callbacks.ASActivityLifeCallback;
import com.gyz.androidsamples.callbacks.ASComponentCallbacks;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

import net.danlew.android.joda.JodaTimeAndroid;

import java.lang.reflect.Field;


/**
 * Created by guoyizhe on 16/6/12.
 * 邮箱:gyzboy@126.com
 */
public class ASApplication extends Application {

    private int appCount = 0;

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);
    }

    //最早了
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if(getPackageName().equals(ProcessUtils.getCurProcessName(base))) {//是否是当前主线程
            try {
                String[] e = new String[]{"com.gyz.androidsamples.LaucherActivity", "com.gyz.androidsamples.MainActivity"};
                long launchTime = System.currentTimeMillis();

                try {
                    Class e1 = Class.forName("com.gyz.androidsamples.BuildConfig");
                    Field fieldLaunchTime = e1.getDeclaredField("launchTime");
                    fieldLaunchTime.setAccessible(true);
                    launchTime = ((Long)fieldLaunchTime.get(e1)).longValue();
                } catch (Exception var7) {
                    var7.printStackTrace();
                }
                OnLineMonitorApp.setBootInfo(e,launchTime);
                OnLineMonitorApp.init(this, base, (ActivityLifecycleCallback)null, (LoadTimeCalculate)null, (SmoothCalculate)null);
                OnLineMonitor.start();
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }
    }

    //这个函数是当我们的应用开始之时就被调用了，比应用中的其他对象创建的早，这个实现尽可能的快一点，
    //因为这个时间直接影响到我们第一个activity/service/receiver。如果你要重写这个方法必须调用super.onCreate()
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ASActivityLifeCallback(appCount));
        registerComponentCallbacks(new ASComponentCallbacks());

        initWeex();
        JodaTimeAndroid.init(this);

        if (!isDebug(this)) {
            CCHandler.install(new CCHandler.ExceptionHandler(){

                // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

                @Override
                public void handlerException(final Thread thread, final Throwable throwable) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.d("Cockroach", thread + "\n" + throwable.toString());
                                throwable.printStackTrace();
                                Toast.makeText(ASApplication.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException("..."+(i++));
                            } catch (Throwable e) {

                            }
                        }
                    });
                }
            });
        }

    }

    private void initWeex() {
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");
        WXSDKEngine.initialize(this,
                new InitConfig.Builder()
                        //.setImgAdapter(new FrescoImageAdapter())// use fresco adapter
                        .setImgAdapter(new ImageAdapter())
                        .setDebugAdapter(new PlayDebugAdapter())
                        .build()
        );


        try {
            WXSDKEngine.registerComponent("richtext", RichText.class);
            WXSDKEngine.registerModule("render", RenderModule.class);
            WXSDKEngine.registerModule("event", WXEventModule.class);
            WXSDKEngine.registerModule("myModule", MyModule.class);
            WXSDKEngine.registerModule("geolocation", GeolocationModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    //在你的app生命周期的任何阶段，onTrimMemory回调方法同样可以告诉你整个设备的内存资源已经开始紧张,此时还有后台进程
    //你应该根据onTrimMemory方法中的内存级别来进一步决定释放哪些资源。
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_UI_HIDDEN:
                //UI隐藏时调用，在此释放UI资源，与onStop不同，onStop是activity实例隐藏时执行，在这里释放activity的资源
                //例如网络连接，unregister广播接收者，除非接到onTrimMemory回调，否则不应释放UI资源以免返回到activity时UI资源不可用
                break;
            case TRIM_MEMORY_RUNNING_MODERATE:
                //你的app正在运行并且不会被列为可杀死的。但是设备此时正运行于低内存状态下，系统开始触发杀死LRU Cache中的Process的机制
                break;
            case TRIM_MEMORY_RUNNING_LOW:
                //你的app正在运行且没有被列为可杀死的。但是设备正运行于更低内存的状态下，你应该释放不用的资源用来提升系统性能（但是这也会直接影响到你的app的性能）。
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:
                //的app仍在运行，但是系统已经把LRU Cache中的大多数进程都已经杀死，因此你应该立即释放所有非必须的资源
                break;
            case TRIM_MEMORY_BACKGROUND:
                //你的app处于最不容易杀死的位置
                break;
            case TRIM_MEMORY_MODERATE:
                //你的app处于中部位置
                break;
            case TRIM_MEMORY_COMPLETE:
                //你的app处于最容易杀死的位置
                break;
        }
    }

    //OnLowMemory是Android提供的API，在系统内存不足，
    //所有后台程序（优先级为background的进程，不是指后台运行的进程）都被杀死时，系统会调用OnLowMemory
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //OnLowMemory是Android提供的API，在系统内存不足，所有后台程序（优先级为background的进程，不是指后台运行的进程）都被杀死时，系统会调用OnLowMemory。系统提供的回调有：
        //Application.onLowMemory()
        //Activity.OnLowMemory()
        //Fragement.OnLowMemory()
        //Service.OnLowMemory()
        //ContentProvider.OnLowMemory()

        //除了上述系统提供的API，还可以自己实现ComponentCallbacks，通过API注册，这样也能得到OnLowMemory回调。例如：
        /*
        public static class MyCallback implements ComponentCallbacks {
            @Override
            public void onConfigurationChanged(Configuration arg) {
            }

            @Override
            public void onLowMemory() {
                //do release operation
            }
        }
        */
        //然后，通过Context.registerComponentCallbacks() 在合适的时候注册回调就可以了。通过这种自定义的方法，可以在很多地方注册回调，
        //而不需要局限于系统提供的组件。
        //onLowMemory 当后台程序已经终止资源还匮乏时会调用这个方法。
        //好的应用程序一般会在这个方法里面释放一些不必要的资源来应付当后台程序已经终止，前台应用程序内存还不够时的情况。

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        } else {

        }
    }

    //onTerminate(): 这个函数是模拟一个过程环境，在真机中永远也不会被调用
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public int getAppCount() {
        return appCount;
    }

    public void setAppCount(int appCount) {
        this.appCount = appCount;
    }


    /**
     * 是否是debug模式,比单纯的使用BuildConfig文件中的debug准确
     * @param context
     * @return
     */
    private static Boolean isDebug(Context context){
        return  context.getApplicationInfo() != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
