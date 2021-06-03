package com.gyz.androidsamples.utils;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author: guoyizhe
 * date  : 2020/5/26
 * desc  : hook 一些方法
 */
public class HookUtils {

    public void hookAM() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> amnClass = Class.forName("android.app.ActivityManagerNative");
        //获取单例对象 Singleton<IActivityManager> ，变量名 gDefault 私有
        Field gDefaultField = amnClass.getDeclaredField("gDefault");
        //禁止JAVA 进行语言访问检查，private 等修饰的就可以访问操作了
        gDefaultField.setAccessible(true);
        //静态属性，直接传入 Null。获取ActivityManagerNative 中的 gDefault
        Object gDefault = gDefaultField.get(null);

        Class<?> singletonClass = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);

        //调用 Singleton 的 get方法 取出 instance 对象
        //instance 对象即 ActivityManager
        Object instance = mInstanceField.get(gDefault);

        //创建代理 handler
        ActivityManagerHandler handler = new ActivityManagerHandler(instance);
        //反射 IActivityManager接口 Class 文件
        Class<?> amClass = Class.forName("android.app.IActivityManager");
        //创建代理对象
        Object amProxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{amClass}, handler);

        //将gDefault中的 Instance 置换为代理
        mInstanceField.set(gDefault,amProxy);
    }
    /**
     * 代理对象回调
     */
    private class ActivityManagerHandler implements InvocationHandler {
        private Object am;

        public ActivityManagerHandler(Object am) {
            this.am = am;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e("APIHook","正在调用的方法--->"+method.getName());
            return method.invoke(am,args);
        }
    }
}
