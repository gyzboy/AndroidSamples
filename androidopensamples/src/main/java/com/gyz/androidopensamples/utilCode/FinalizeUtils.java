package com.gyz.androidopensamples.utilCode;

import android.util.Log;

import com.taobao.weex.devtools.common.LogUtil;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by guoyizhe on 2017/3/15.
 * 邮箱:gyzboy@126.com
 *
 * 这个工具类是模拟执行java的finalize行为
 */

public class FinalizeUtils {

    static Method mObjectClazz_FinalizeMethod;
    static Class<?> mThreadGroupClazz;
    static ThreadGroup mSystemThreadGroup;
    static Class<?> mFinalizerReferenceClazz;
    static Method mFinalizerReferenceClazz_RemoveMethod;
    static ReferenceQueue<Object> mQueue;
    static Class<?> mDaemonsClazz;
    static Class<?>[] mDaemonsInnerClazzes = new Class[5];
    static Object[] mDaemonsInnerClazzInstances = new Object[5];
    static Method[] mDaemonsInnerClazzStartMethods = new Method[5];
    static Method[] mDaemonsInnerClazzStopMethods = new Method[5];
    volatile boolean initSuccess = false;
    static StackTraceElement[] STACK_TRACE_ELEMENT = new StackTraceElement[0];
    private static final int NANOS_PER_MILLI = 1000000;
    private static final int NANOS_PER_SECOND = 1000000000;
    private static final long MAX_FINALIZE_NANOS = 10000000000L;

    public FinalizeUtils() {
        try {
            this.initialize();
            this.initSuccess = true;
        } catch (Exception var2) {
            LogUtil.e("FinalizeFake initialize", var2);
        }

    }

    public void startFakeFinalizerDaemon() {
        if(this.initSuccess) {
            try {
                try {
                    mDaemonsInnerClazzStopMethods[1].invoke(mDaemonsInnerClazzInstances[1], new Object[0]);
                } catch (Exception var2) {
                    if(!(var2 instanceof IllegalStateException) || !"not running".equals(var2.getMessage())) {
                        throw var2;
                    }
                }

                try {
                    mDaemonsInnerClazzStopMethods[2].invoke(mDaemonsInnerClazzInstances[2], new Object[0]);
                } catch (Exception var3) {
                    if(!(var3 instanceof IllegalStateException) || !"not running".equals(var3.getMessage())) {
                        throw var3;
                    }
                }

                FinalizeUtils.FakeFinalizerDaemon.INSTANCE.start();
                FinalizeUtils.FakeFinalizerWatchdogDaemon.INSTANCE.start();
            } catch (Exception var4) {
                this.resumeFinalizerDaemon();
            }

        }
    }

    public void resumeFinalizerDaemon() {
        if(this.initSuccess) {
            try {
                try {
                    FinalizeUtils.FakeFinalizerDaemon.INSTANCE.stop();
                    FinalizeUtils.FakeFinalizerWatchdogDaemon.INSTANCE.stop();
                } catch (Exception var2) {
                    ;
                }

                try {
                    mDaemonsInnerClazzStartMethods[1].invoke(mDaemonsInnerClazzInstances[1], new Object[0]);
                } catch (Exception var3) {
                    if(!(var3 instanceof IllegalAccessException) || !"already running".equals(var3.getMessage())) {
                        throw var3;
                    }
                }

                try {
                    mDaemonsInnerClazzStartMethods[2].invoke(mDaemonsInnerClazzInstances[2], new Object[0]);
                } catch (Exception var4) {
                    if(!(var4 instanceof IllegalAccessException) || !"already running".equals(var4.getMessage())) {
                        throw var4;
                    }
                }
            } catch (Exception var5) {
                ;
            }

        }
    }

    private void initialize() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchFieldException {
        mObjectClazz_FinalizeMethod = Object.class.getDeclaredMethod("finalize", new Class[0]);
        mObjectClazz_FinalizeMethod.setAccessible(true);
        mThreadGroupClazz = Class.forName("java.lang.ThreadGroup");

        Field queueField;
        try {
            queueField = mThreadGroupClazz.getDeclaredField("systemThreadGroup");
            queueField.setAccessible(true);
            mSystemThreadGroup = (ThreadGroup)queueField.get(mThreadGroupClazz);
        } catch (NoSuchFieldException var11) {
            Field clazzNames = mThreadGroupClazz.getDeclaredField("mSystem");
            clazzNames.setAccessible(true);
            mSystemThreadGroup = (ThreadGroup)clazzNames.get(mThreadGroupClazz);
        }

        mFinalizerReferenceClazz = Class.forName("java.lang.ref.FinalizerReference");
        queueField = mFinalizerReferenceClazz.getDeclaredField("queue");
        queueField.setAccessible(true);
        mQueue = (ReferenceQueue)queueField.get(mFinalizerReferenceClazz);
        mFinalizerReferenceClazz_RemoveMethod = mFinalizerReferenceClazz.getDeclaredMethod("remove", new Class[]{mFinalizerReferenceClazz});
        String[] var12 = new String[]{"java.lang.Daemons$ReferenceQueueDaemon", "java.lang.Daemons$FinalizerDaemon", "java.lang.Daemons$FinalizerWatchdogDaemon", "java.lang.Daemons$HeapTrimmerDaemon", "java.lang.Daemons$GCDaemon"};
        mDaemonsClazz = Class.forName("java.lang.Daemons");
        Class[] classes = mDaemonsClazz.getDeclaredClasses();
        Class[] arr$ = classes;
        int len$ = classes.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Class clazz = arr$[i$];

            for(int i = 0; i < var12.length; ++i) {
                if(null != clazz && clazz.getName().equals(var12[i])) {
                    mDaemonsInnerClazzes[i] = clazz;
                    Field instanceField = clazz.getDeclaredField("INSTANCE");
                    instanceField.setAccessible(true);
                    Object instance = instanceField.get(clazz);
                    mDaemonsInnerClazzInstances[i] = instance;
                    mDaemonsInnerClazzStartMethods[i] = clazz.getMethod("start", new Class[0]);
                    mDaemonsInnerClazzStopMethods[i] = clazz.getMethod("stop", new Class[0]);
                    break;
                }
            }
        }

    }

    private static class FakeFinalizerWatchdogDaemon extends FinalizeUtils.FakeDaemon {
        private static final FinalizeUtils.FakeFinalizerWatchdogDaemon INSTANCE = new FinalizeUtils.FakeFinalizerWatchdogDaemon();

        FakeFinalizerWatchdogDaemon() {
            super("FakeFinalizerWatchdogDaemon");
        }

        @Override
        public void run() {
            while(this.isRunning()) {
                boolean waitSuccessful = this.waitForObject();
                if(waitSuccessful) {
                    boolean finalized = this.waitForFinalization();
                    if(!finalized && !this.isDebuggerActive()) {
                        Object finalizedObject = FinalizeUtils.FakeFinalizerDaemon.INSTANCE.finalizingObject;
                        if(finalizedObject != null) {
                            FinalizeUtils.FakeFinalizerDaemon.INSTANCE.interrupt();
                        }
                    }
                }
            }

        }

        private boolean isDebuggerActive() {
            return VMRuntimeUtils.isDebuggerActive();
        }

        private boolean waitForObject() {
            while(true) {
                Object object = FinalizeUtils.FakeFinalizerDaemon.INSTANCE.finalizingObject;
                if(object != null) {
                    return true;
                }

                synchronized(this) {
                    try {
                        this.wait();
                    } catch (InterruptedException var5) {
                        return false;
                    }
                }
            }
        }

        private void sleepFor(long startNanos, long durationNanos) {
            while(true) {
                long elapsedNanos = System.nanoTime() - startNanos;
                long sleepNanos = durationNanos - elapsedNanos;
                long sleepMills = sleepNanos / 1000000L;
                if(sleepMills <= 0L) {
                    return;
                }

                try {
                    Thread.sleep(sleepMills);
                } catch (InterruptedException var12) {
                    if(!this.isRunning()) {
                        return;
                    }
                }
            }
        }

        private boolean waitForFinalization() {
            long startTime = FinalizeUtils.FakeFinalizerDaemon.INSTANCE.finalizingStartedNanos;
            this.sleepFor(startTime, 10000000000L);
            return FinalizeUtils.FakeFinalizerDaemon.INSTANCE.finalizingObject == null || FinalizeUtils.FakeFinalizerDaemon.INSTANCE.finalizingStartedNanos != startTime;
        }
    }

    private abstract static class FakeDaemon implements Runnable {
        private Thread thread;
        private String name;

        protected FakeDaemon(String name) {
            this.name = name;
        }

        public synchronized void start() {
            if(this.thread != null) {
                throw new IllegalStateException("already running");
            } else {
                this.thread = new Thread(FinalizeUtils.mSystemThreadGroup, this, this.name);
                this.thread.setDaemon(true);
                this.thread.start();
            }
        }

        @Override
        public abstract void run();

        protected synchronized boolean isRunning() {
            return this.thread != null;
        }

        public synchronized void interrupt() {
            this.interrupt(this.thread);
        }

        public synchronized void interrupt(Thread thread) {
            if(thread == null) {
                throw new IllegalStateException("not running");
            } else {
                thread.interrupt();
            }
        }

        public void stop() {
            Thread threadToStop;
            synchronized(this) {
                threadToStop = this.thread;
                this.thread = null;
            }

            if(threadToStop == null) {
                throw new IllegalStateException("not running");
            } else {
                this.interrupt(threadToStop);

                while(true) {
                    try {
                        threadToStop.join();
                        return;
                    } catch (InterruptedException var5) {
                        ;
                    }
                }
            }
        }

        public synchronized StackTraceElement[] getStackTrace() {
            return this.thread != null?this.thread.getStackTrace():FinalizeUtils.STACK_TRACE_ELEMENT;
        }
    }

    private static class FakeFinalizerDaemon extends FinalizeUtils.FakeDaemon {
        private static final FinalizeUtils.FakeFinalizerDaemon INSTANCE = new FinalizeUtils.FakeFinalizerDaemon();
        private final ReferenceQueue<Object> queue;
        private volatile Object finalizingObject;
        private volatile long finalizingStartedNanos;

        FakeFinalizerDaemon() {
            super("FakeFinalizerDaemon");
            this.queue = FinalizeUtils.mQueue;
        }

        @Override
        public void run() {
            while(this.isRunning()) {
                try {
                    this.doFinalize(this.queue.remove());
                } catch (InterruptedException var2) {
                    ;
                }
            }

        }

        private void doFinalize(Reference<?> reference) {
            try {
                FinalizeUtils.mFinalizerReferenceClazz_RemoveMethod.invoke(FinalizeUtils.mFinalizerReferenceClazz, new Object[]{reference});
                Object ex = reference.get();
                reference.clear();

                try {
                    this.finalizingStartedNanos = System.nanoTime();
                    this.finalizingObject = ex;
                    synchronized(FinalizeUtils.FakeFinalizerWatchdogDaemon.INSTANCE) {
                        FinalizeUtils.FakeFinalizerWatchdogDaemon.INSTANCE.notify();
                    }

                    FinalizeUtils.mObjectClazz_FinalizeMethod.invoke(ex, new Object[0]);
                } catch (Throwable var11) {
                    Log.e("GCMagic", "Uncaught exception thrown by (" + ex + ") finalizer", var11);
                } finally {
                    this.finalizingObject = null;
                }
            } catch (Throwable var13) {
                Log.e("GCMagic", "FinalizerReference remove exception by finalizer", var13);
            }

        }
    }

}
