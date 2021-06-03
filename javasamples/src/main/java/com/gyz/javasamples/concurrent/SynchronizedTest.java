package com.gyz.javasamples.concurrent;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */
public class SynchronizedTest {
    public static void main(String[] args) {
        //1.当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，其他线程对“该对象”的该“synchronized方法”或者“synchronized代码块”的访问将被阻塞。
//        Runnable demo = new MyRunable();     // 新建“Runnable对象”
//        Thread t1 = new Thread(demo, "t1");  // 新建“线程t1”, t1是基于demo这个Runnable对象
//        Thread t2 = new Thread(demo, "t2");  // 新建“线程t2”, t2是基于demo这个Runnable对象
//        t2.start();
//        t1.start();                          // 启动“线程t1”

        //2.当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，其他线程仍然可以访问“该对象”的非同步代码块。
        final Count count = new Count();
        Count count4 = new Count();
        // 新建t1, t1会调用“count对象”的synMethod()方法
        Thread t1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        count4.synMethod();
                    }
                }, "t1");

        // 新建t2, t2会调用“count对象”的nonSynMethod()方法
        Thread t2 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        count.nonSynMethod();
                    }
                }, "t2");

        //3.当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，其他线程对“该对象”的其他的“synchronized方法”或者“synchronized代码块”的访问将被阻塞。
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                count.synMethod();
            }
        }, "t3");

        t1.start();  // 启动t1
        t2.start();  // 启动t2
        t3.start();

//        Count count1 = new Count();
//        Count count2 = new Count();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                count1.syncMethod();
//            }
//        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                count2.syncMethod();
//            }
//        }).start();
    }


}

class MyRunable implements Runnable {

    @Override
    public void run() {
        synchronized (this) {//锁这个对象
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " loop " + i);
                }
            } catch (InterruptedException ie) {
            }
        }
    }
}

class Count {

    public synchronized static void syncMethod(){
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100); // 休眠100ms
                System.out.println(Thread.currentThread().getName() + " syncMethod loop " + i);
            }
        } catch (InterruptedException ie) {
        }
    }

    // 含有synchronized同步块的方法
    public void synMethod() {
        synchronized (this) {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " synMethod loop " + i);
                }
            } catch (InterruptedException ie) {
            }
        }
    }

    // 含有synchronized同步块的方法
    public void synMethod2() {
        synchronized (this) {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " synMethod2 loop " + i);
                }
            } catch (InterruptedException ie) {
            }
        }
    }

    // 非同步的方法
    public void nonSynMethod() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " nonSynMethod loop " + i);
            }
        } catch (InterruptedException ie) {
        }
    }
}