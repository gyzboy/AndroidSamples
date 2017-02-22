package com.gyz.javasamples.object;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by guoyizhe on 2017/2/17.
 * 邮箱:gyzboy@126.com
 */

public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader myClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String s) throws ClassNotFoundException {
                try {
                    String fileName = s.substring(s.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(s);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(s, b, 0, b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return super.loadClass(s);
            }
        };

//        Object obj = myClassLoader.loadClass("com.gyz.javasamples.object.ClassLoaderTest").newInstance();
//        System.out.println(obj.getClass());
//        System.out.println(obj instanceof com.gyz.javasamples.object.ClassLoaderTest);//false,虚拟机中存在了两个ClassLoader类,一个由系统应用程序类加载器加载的,另外一个是由我们定义的类加载器加载的,属于两个不同的类

        System.out.println("MainClass getClassLoader: " + ClassLoaderTest.class.getClassLoader());//默认是appClassLoader,加载位于classPath下的类
        System.out.println("MainClass getContextClassLoader: " + Thread.currentThread().getContextClassLoader());//不设置的话ContextClassLoader默认就是appClassLoader
        Thread innerThread1 = new InnerThread1();
        innerThread1.start();
    }


}

class InnerThread1 extends Thread {
    @Override
    public void run() {
        try {
            URL[] urls = new URL[1];
            urls[0] = new URL("jar:file:/E:/GoogleCode/platform-components/trunk/SourceCode/component-core/target/component-core-1.0.jar!/");
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class<?> clazz = urlClassLoader.loadClass("com.gyz.javasamples.object.ClassLoaderTest");
            System.out.println(clazz.newInstance());

            System.out.println("InnerThread1 getClassLoader: " + clazz.getClassLoader());
            System.out.println("InnerThread1 getContextClassLoader: " + Thread.currentThread().getContextClassLoader());

            this.setContextClassLoader(urlClassLoader);//在线程启用前调用,设置新线程使用指定classLoader加载class

            Thread innerThread2 = new InnerThread2();
            innerThread2.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

class InnerThread2 extends Thread{
    @Override
    public void run() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            classLoader.loadClass("com.gyz.javasamples.object.ClassLoaderTest");
            System.out.println("InnerThread2 getContextClassLoader: " + Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
