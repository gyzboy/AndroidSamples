package com.gyz.javasamples.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Date;

/**
 * Created by guoyizhe on 16/9/5.
 * 邮箱:gyzboy@126.com
 */
public class ReferenceTest {
    public static void main(String[] args) throws InterruptedException {
        //softReference 软引用，当内存不足时会回收软引用对象,通过引用返回对象返回null
        //weakReference 弱引用，每次gc都会回收弱引用对象(有强引用指向除外)，会给对象留一个null，与weakHashMap不同
        //phantomReference 虚引用，必须与引用对象绑定使用
        //ReferenceQueue，引用队列，当引用锁指向对象被回收后，该引用就会进入与其绑定的引用队列，用以释放引用所占内存
        //weakHashMap，用来保存weakReference，在put的时候自动包装为weakReference，引发清理的条件是key不再被使用了


        //只有当内存不够的时候，才回收这类内存，因此在内存足够的时候，它们通常不被回收
//        Object obj = new Object();
//        ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
//        SoftReference<Object> softRef = new SoftReference<Object>(obj, refQueue);
//        System.out.println(softRef.get()); // java.lang.Object@f9f9d8
//        System.out.println(refQueue.poll());// null
//
//        // 清除强引用,触发GC
//        obj = null;
//        System.gc();
//
//        System.out.println(softRef.get());
//
//        Thread.sleep(200);
//        System.out.println(refQueue.poll());

/*
        //=================

        //弱引用: 当发生GC的时候,Weak引用对象总是会内回收回收。因此Weak引用对象会更容易、更快被GC回收。
        //Weak引用对象常常用于Map数据结构中，引用占用内存空间较大的对象
        WeakReference<Object> weakRef = new WeakReference<Object>(obj, refQueue);
        System.out.println(weakRef.get()); // java.lang.Object@f9f9d8
        System.out.println(refQueue.poll());// null

        // 清除强引用,触发GC
        obj = null;
        System.gc();

        System.out.println(weakRef.get());

        // 这里特别注意:poll是非阻塞的,remove是阻塞的.
        // JVM将弱引用放入引用队列需要一定的时间,所以这里先睡眠一会儿
        // System.out.println(refQueue.poll());// 这里有可能是null

        Thread.sleep(200);
        System.out.println(refQueue.poll());
        // System.out.println(refQueue.poll());//这里一定是null,因为已经从队列中移除

        // System.out.println(refQueue.remove());

*/
        //=================

//当GC一但发现了虚引用对象，将会将PhantomReference对象插入ReferenceQueue队列.
//而此时PhantomReference所指向的对象并没有被GC回收，而是要等到ReferenceQueue被你真正的处理后才会被回收.
//        PhantomReference<Object> phantom = new PhantomReference<Object>(obj,
//                refQueue);
//        System.out.println(phantom.get()); // java.lang.Object@f9f9d8
//        System.out.println(refQueue.poll());// null
//
//        obj = null;
//        System.gc();

        // 调用phanRef.get()不管在什么情况下会一直返回null
//        System.out.println(phantom.get());

        // 当GC发现了虚引用，GC会将phanRef插入进我们之前创建时传入的refQueue队列
        // 注意，此时phanRef所引用的obj对象，并没有被GC回收，在我们显式地调用refQueue.poll返回phanRef之后
        // 当GC第二次发现虚引用，而此时JVM将phanRef插入到refQueue会插入失败，此时GC才会对obj进行回收
//        Thread.sleep(200);
//        System.out.println(refQueue.poll());


//1.
//        MyDate date = new MyDate();
//        date = null;
//        System.gc();//显示调用gc，使JVM进行垃圾回收,finalize方法被执行
//        ReferenceTest.drainMemory();//JVM的垃圾回收机制，在内存充足的情况下，除非你显式调用System.gc()，否则它不会进行垃圾回收；在内存不足的情况下，垃圾回收将自动运行


//2.在内存不足时，软引用被终止。软引用被禁止时，
//        SoftReference ref = new SoftReference(new MyDate());
//        ReferenceTest.drainMemory();
//
//   等价于
//        MyDate date = new MyDate();
//   由JVM决定运行
//        If(JVM.内存不足()) {
//            date = null;
//            System.gc();
//        }
//        SoftReference ref = new SoftReference(new MyDate());
//        ReferenceTest.drainMemory();


//3.在JVM垃圾回收运行时，弱引用被终止.
//        WeakReference ref = new WeakReference(new MyDate());
//        System.gc();
//        等同于：
//        MyDate date = new MyDate();
// 垃圾回收
//        If(JVM.内存不足()) {
//            date = null;
//            System.gc();
//        }
//        WeakReference ref = new WeakReference(new MyDate());
//        System.gc();


//4.假象引用，在实例化后，就被终止了。
//
//        ReferenceQueue queue = new ReferenceQueue();
//        PhantomReference ref = new PhantomReference(new MyDate(), queue);
//        System.gc();
//
//        等同于：
//
//        MyDate date = new MyDate();
//        date = null;

        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference ref = new PhantomReference(new MyDate(), queue);
        System.gc();
    }


    public static void drainMemory() {
        String[] array = new String[1024 * 10];
        for (int i = 0; i < 1024 * 10; i++) {
            for (int j = 'a'; j <= 'z'; j++) {
                array[i] += (char) j;
            }
        }
    }
}

class MyDate extends Date {

    /**
     * Creates a new instance of MyDate
     */
    public MyDate() {
    }

    // 覆盖finalize()方法
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("obj [Date: " + this.getTime() + "] is gc");
    }

    public String toString() {
        return "Date: " + this.getTime();
    }
}
