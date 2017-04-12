package com.gyz.androidopensamples.crashconsume;

import android.os.Looper;

import com.gyz.androidopensamples.utilCode.StringUtils;
import com.taobao.weex.devtools.common.LogUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by guoyizhe on 2017/3/15.
 * 邮箱:gyzboy@126.com
 */

public class UncaughtExceptionCatcher implements Thread.UncaughtExceptionHandler {
    volatile boolean enable;
    CopyOnWriteArrayList<UncaughtExceptionIgnore> mIgnoreList = new CopyOnWriteArrayList();
    UncaughtExceptionLinsterAdapterCopyOnWriteArrayList mLinsterList = new UncaughtExceptionLinsterAdapterCopyOnWriteArrayList();
    Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    UncaughtExceptionCatcher() {
    }

    public void enable() {
        if (!this.enable) {
            this.mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (!"com.android.internal.osRuntimeInit$UncaughtHandler".equals(this.mDefaultUncaughtExceptionHandler.getClass().getName())) {
                ;
            }

            Thread.setDefaultUncaughtExceptionHandler(this);
            this.enable = true;
        }
    }

    public void disable() {
        if (this.enable) {
            if (null != this.mDefaultUncaughtExceptionHandler) {
                Thread.setDefaultUncaughtExceptionHandler(this.mDefaultUncaughtExceptionHandler);
            }

            this.enable = false;
        }
    }

    public boolean addIgnore(UncaughtExceptionIgnore ignore) {
        return null != ignore && !StringUtils.isBlank(ignore.getName()) ? this.mIgnoreList.add(ignore) : false;
    }

    public boolean addLinster(UncaughtExceptionLinster linster) {
        return null != linster ? this.mLinsterList.add(linster) : false;
    }

    public List<UncaughtExceptionLinster> getAllLinster() {
        return this.mLinsterList;
    }

    private void onUncaughtException(Thread thread, Throwable throwable, boolean ignore) {
        HashMap extraInfo = new HashMap();
        if (ignore) {
            extraInfo.put("REPORT_IGNORE", "true");
        }

        try {
            if (throwable instanceof OutOfMemoryError) {
                //TODO OOM exception
            } else {
                Iterator crashReport = this.mLinsterList.iterator();

                while (crashReport.hasNext()) {
                    UncaughtExceptionLinster linster = (UncaughtExceptionLinster) crashReport.next();

                    try {
                        extraInfo.putAll(linster.onUncaughtException(thread, throwable));
                    } catch (Throwable var8) {
                        LogUtil.e("call linster onUncaughtException", var8);
                    }
                }
            }
        } catch (Throwable var9) {
            LogUtil.e("externalData", var9);
        }
//TODO SendReport
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        long start = System.currentTimeMillis();

        try {
            try {
                LogUtil.d(String.format("catch uncaught exception:%s on thread:%s.", new Object[]{thread.getName(), throwable.toString()}));
                Boolean end = thread == Looper.getMainLooper().getThread();
                if (null != end && !end) {
                    Iterator i = this.mIgnoreList.iterator();

                    while (i.hasNext()) {
                        UncaughtExceptionIgnore ignore = (UncaughtExceptionIgnore) i.next();
                        if (ignore.uncaughtExceptionIgnore(thread, throwable)) {
                            this.onUncaughtException(thread, throwable, true);
                            return;
                        }
                    }
                }
            } catch (Exception var8) {
                LogUtil.e("ignore uncaught exception.", var8);
            }

            this.onUncaughtException(thread, throwable, false);
        } catch (Throwable var9) {
            LogUtil.e("uncaught exception.", var9);
        }

        long end1 = System.currentTimeMillis();
        LogUtil.d("catch uncaught exception complete elapsed time:" + (end1 - start) + ".ms");
        if (null != this.mDefaultUncaughtExceptionHandler) {
            this.mDefaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
        }

    }
}

class UncaughtExceptionLinsterAdapterCopyOnWriteArrayList extends CopyOnWriteArrayList<UncaughtExceptionLinster> {
    private static final long serialVersionUID = 4393313111950638180L;

    UncaughtExceptionLinsterAdapterCopyOnWriteArrayList() {
    }

    @Override
    public synchronized boolean remove(Object o) {
        Iterator i = this.iterator();

        UncaughtExceptionLinster linster;
        do {
            if (!i.hasNext()) {
                return false;
            }

            linster = (UncaughtExceptionLinster) i.next();
        } while (!linster.originalEquals(o));

        return super.remove(linster);
    }
}

interface UncaughtExceptionLinster {
    boolean originalEquals(Object var1);

    Map<String, Object> onUncaughtException(Thread var1, Throwable var2);
}

interface UncaughtExceptionIgnore {
    String getName();

    boolean uncaughtExceptionIgnore(Thread var1, Throwable var2);
}
