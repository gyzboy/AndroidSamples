package com.gyz.javasamples.concurrent;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */
public class WaitNotifyTest {

    private static Object obj = new Object();

    public static void main(String[] args) {

        ThreadA t1 = new ThreadA("t1");

        synchronized(t1) {
            try {
                // 启动“线程t1”
                System.out.println(Thread.currentThread().getName()+" start t1");
                t1.start();

                // 主线程等待t1通过notify()唤醒。
                System.out.println(Thread.currentThread().getName()+" wait()");
                t1.wait();//jdk的解释中，说wait()的作用是让“当前线程”等待，而“当前线程”是指正在cpu上运行的线程！
                //这也意味着，虽然t1.wait()是通过“线程t1”调用的wait()方法，但是调用t1.wait()的地方是在“主线程main”中。
                //而主线程必须是“当前线程”，也就是运行状态，才可以执行t1.wait()。所以，此时的“当前线程”是“主线程main”！
                //因此，t1.wait()是让“主线程”等待，而不是“线程t1”！

                System.out.println(Thread.currentThread().getName()+" continue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




        ThreadB tB1 = new ThreadB("tB1");
        ThreadB tB2 = new ThreadB("tB2");
        ThreadB tB3 = new ThreadB("tB3");
        tB1.start();
        tB2.start();
        tB3.start();

        try {
            System.out.println(Thread.currentThread().getName()+" sleep(3000)");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized(obj) {
            // 主线程等待唤醒。
            System.out.println(Thread.currentThread().getName()+" notifyAll()");
            obj.notifyAll();
        }
    }


    static class ThreadB extends Thread{

        public ThreadB(String name){
            super(name);
        }

        public void run() {
            synchronized (obj) {
                try {
                    // 打印输出结果
                    System.out.println(Thread.currentThread().getName() + " wait");

                    // 唤醒当前的wait线程
                    obj.wait();

                    // 打印输出结果
                    System.out.println(Thread.currentThread().getName() + " continue");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
class ThreadA extends Thread{

    public ThreadA(String name) {
        super(name);
    }

    public void run() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName()+" call notify()");
            // 唤醒当前的wait线程
            notify();
        }
    }
}

