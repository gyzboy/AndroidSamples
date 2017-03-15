package com.gyz.androidopensamples.utilCode;

import com.taobao.weex.devtools.common.LogUtil;

import java.lang.reflect.Method;

/**
 * Created by guoyizhe on 2017/3/15.
 * 邮箱:gyzboy@126.com
 */

public class VMRuntimeUtils {

    static Class<?> mVMRuntimeClazz;
    static Object mRuntime;
    static Method mVMRuntimeClazz_IsDebuggerActiveMethod;
    static Method mVMRuntimeClazz_StartJitCompilationMethod;
    static Method mVMRuntimeClazz_DisableJitCompilationMethod;
    static boolean initSuccess = false;

    public VMRuntimeUtils() {
    }

    public static boolean isDebuggerActive() {
        if (initSuccess) {
            try {
                Object e = mVMRuntimeClazz_IsDebuggerActiveMethod.invoke(mRuntime, new Object[0]);
                return ((Boolean) e).booleanValue();
            } catch (Exception var1) {
                LogUtil.e("isDebuggerActive", var1);
            }
        }

        return false;
    }

    public static boolean startJitCompilation() {
        if (initSuccess) {
            try {
                mVMRuntimeClazz_StartJitCompilationMethod.invoke(mRuntime, new Object[0]);
                return true;
            } catch (Exception var1) {
                LogUtil.e("startJitCompilation", var1);
            }
        }

        return false;
    }

    public static boolean disableJitCompilation() {
        if (initSuccess) {
            try {
                mVMRuntimeClazz_DisableJitCompilationMethod.invoke(mRuntime, new Object[0]);
                return true;
            } catch (Exception var1) {
                LogUtil.e("disableJitCompilation", var1);
            }
        }

        return false;
    }

    static {
        try {
            mVMRuntimeClazz = Class.forName("dalvik.system.VMRuntime");
            Method e = mVMRuntimeClazz.getMethod("getRuntime", new Class[0]);
            mRuntime = e.invoke(mVMRuntimeClazz, new Object[0]);
            mVMRuntimeClazz_IsDebuggerActiveMethod = mVMRuntimeClazz.getMethod("isDebuggerActive", new Class[0]);
            mVMRuntimeClazz_StartJitCompilationMethod = mVMRuntimeClazz.getMethod("startJitCompilation", new Class[0]);
            mVMRuntimeClazz_DisableJitCompilationMethod = mVMRuntimeClazz.getMethod("disableJitCompilation", new Class[0]);
            initSuccess = true;
        } catch (Exception var1) {
            LogUtil.e("init system properties utils");
        }

    }
}
