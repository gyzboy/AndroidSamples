package com.gyz.javasamples.concurrent;

/**
 * Created by guoyizhe on 2017/2/13.
 * 邮箱:gyzboy@126.com
 */

public class DaemonThreadTest {

//每个线程都有一个优先级。“高优先级线程”会优先于“低优先级线程”执行。每个线程都可以被标记为一个守护进程或非守护进程。
//在一些运行的主线程中创建新的子线程时，子线程的优先级被设置为等于“创建它的主线程的优先级”，当且仅当“创建它的主线程是守护线程”时“子线程才会是守护线程”。
//当Java虚拟机启动时，通常有一个单一的非守护线程（该线程通过是通过main()方法启动）。JVM会一直运行直到下面的任意一个条件发生，JVM就会终止运行：
//  (01) 调用了exit()方法，并且exit()有权限被正常执行。
//  (02) 所有的“非守护线程”都死了(即JVM中仅仅只有“守护线程”)。
//每一个线程都被标记为“守护线程”或“用户线程”。当只有守护线程运行时，JVM会自动退出。

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "is Daemon = " + Thread.currentThread().isDaemon());
        Thread t1 = new MThread("t1");
        Thread t2 = new MyDaemon("t2");
        t2.setDaemon(true);
        t1.start();
        t2.start();
    }


}
class MThread extends Thread {
    public MThread(String name) {
        super(name);
    }

    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(3);
                System.out.println(this.getName() + "(isDaemon) = " + this.isDaemon() + ", loop = " + i);
            }
        } catch (InterruptedException e) {
        }
    }
}

class MyDaemon extends Thread {
    public MyDaemon(String name) {
        super(name);
    }

    public void run() {
        try {
            for (int i = 0; i < 10000; i++) {
                Thread.sleep(1);
                System.out.println(this.getName() + "(isDaemon=" + this.isDaemon() + ")" + ", loop " + i);
            }
        } catch (InterruptedException e) {
        }
    }
}
