package com.gyz.androidopensamples.crashconsume;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by guoyizhe on 2017/3/1.
 * 邮箱:gyzboy@126.com
 */

public class CCHandler {
    public interface ExceptionHandler {

        void handlerException(Thread thread, Throwable throwable);
    }

    public CCHandler() {
    }

    private static ExceptionHandler sExceptionHandler;
    private static Thread.UncaughtExceptionHandler sUncaughtExceptionHandler;
    private static boolean sInstalled = false;//标记位，避免重复安装卸载

    /**
     * 当主线程或子线程抛出异常时会调用exceptionHandler.handlerException(Thread thread, Throwable throwable)
     * <p>
     * exceptionHandler.handlerException可能运行在非UI线程中。
     * <p>
     * 若设置了Thread.setDefaultUncaughtExceptionHandler则可能无法捕获子线程异常。
     *
     * @param exceptionHandler
     */
    public static synchronized void install(ExceptionHandler exceptionHandler) {
        if (sInstalled) {
            return;
        }
        sInstalled = true;
        sExceptionHandler = exceptionHandler;

//原理很简单，就是通过Handler往主线程的queue中添加一个Runnable，当主线程执行到该Runnable时，会进入我们的while死循环，
// 如果while内部是空的就会导致代码卡在这里，最终导致ANR，但我们在while死循环中又调用了Looper.loop()，
// 这就导致主线程又开始不断的读取queue中的Message并执行，这样就可以保证以后主线程的所有异常都会从我们手动调用的Looper.loop()处抛出，
// 一旦抛出就会被try{}catch捕获，这样主线程就不会crash了，如果没有这个while的话那么主线程下次抛出异常时我们就又捕获不到了，
// 这样APP就又crash了，所以我们要通过while让每次crash发生后都再次进入消息循环，while的作用仅限于每次主线程抛出异常后迫使主线程再次进入消息循环。
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
//                        Binder.clearCallingIdentity();
                        if (e instanceof CCException) {
                            return;
                        }
                        if (sExceptionHandler != null) {
                            sExceptionHandler.handlerException(Looper.getMainLooper().getThread(), e);
                        }
                    }
                }
            }
        });

        sUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (sExceptionHandler != null) {
                    sExceptionHandler.handlerException(t, e);
                }
            }
        });

    }

    public static synchronized void uninstall() {
        if (!sInstalled) {
            return;
        }
        sInstalled = false;
        sExceptionHandler = null;
        //卸载后恢复默认的异常处理逻辑，否则主线程再次抛出异常后将导致ANR，并且无法捕获到异常位置
        Thread.setDefaultUncaughtExceptionHandler(sUncaughtExceptionHandler);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                throw new CCException("Quit .....");//主线程抛出异常，迫使 while (true) {}结束
            }
        });

    }
}
