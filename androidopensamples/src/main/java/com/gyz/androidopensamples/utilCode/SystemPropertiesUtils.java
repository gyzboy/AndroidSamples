package com.gyz.androidopensamples.utilCode;

import com.taobao.weex.devtools.common.LogUtil;

import java.lang.reflect.Method;

/**
 * Created by guoyizhe on 2017/3/15.
 * 邮箱:gyzboy@126.com
 */

public class SystemPropertiesUtils {
    static Class<?> mSystemPropertiesClazz;
    static Method mSystemPropertiesClazz_getMethod;
    static Method mSystemPropertiesClazz_setMethod;
    static boolean initSuccess = false;

    public SystemPropertiesUtils() {
    }

    public static String get(String key) {
        if(!initSuccess) {
            return null;
        } else if(StringUtils.isBlank(key)) {
            return null;
        } else {
            try {
                return (String)mSystemPropertiesClazz_getMethod.invoke(mSystemPropertiesClazz, new Object[]{key});
            } catch (Exception var2) {
                LogUtil.e("invoke system properties get", var2);
                return null;
            }
        }
    }

    public static void set(String key, String val) {
        if(initSuccess) {
            if(!StringUtils.isBlank(key)) {
                if(!StringUtils.isBlank(val)) {
                    try {
                        mSystemPropertiesClazz_setMethod.invoke(mSystemPropertiesClazz, new Object[]{key, val});
                    } catch (Exception var3) {
                        LogUtil.e("invoke system properties set", var3);
                    }

                }
            }
        }
    }

    static {
        try {
            mSystemPropertiesClazz = Class.forName("android.os.SystemProperties");
            mSystemPropertiesClazz_getMethod = mSystemPropertiesClazz.getMethod("get", new Class[]{String.class});
            mSystemPropertiesClazz_setMethod = mSystemPropertiesClazz.getMethod("set", new Class[]{String.class, String.class});
            initSuccess = true;
        } catch (Exception var1) {
            LogUtil.e("init system properties utils");
        }

    }
}
