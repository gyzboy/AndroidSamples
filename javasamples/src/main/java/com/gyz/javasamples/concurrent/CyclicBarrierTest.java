package com.gyz.javasamples.concurrent;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */

public class CyclicBarrierTest {

    private static int SIZE = 5;
    private static CyclicBarrier cb;

    public static void main(String[] args) {

        //CyclicBarrier允许N个线程相互等待,计数器可以被重置后使用
        //CyclicBarrier是通过ReentrantLock(独占锁)和Condition来实现的。

//        在CyclicBarrier中，同一批的线程属于同一代，即同一个Generation；CyclicBarrier中通过generation对象，记录属于哪一代。
//        当有parties个线程到达barrier，generation就会被更新换代。
        cb = new CyclicBarrier(SIZE, new Runnable() {
            public void run() {
                System.out.println("CyclicBarrier's parties is: " + cb.getParties());//要求启动此barrier的参与者数目
            }
        });

        // 新建5个任务
        for (int i = 0; i < SIZE ; i++)//小于的话也是一直等待,无法向下执行
            new InnerThread().start();

        System.out.println(cb.getNumberWaiting());//当前屏障处等待的参与者数目
//        cb.reset();//重置其初始状态

    }

    static class InnerThread extends Thread {
        public synchronized void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " wait for CyclicBarrier.");

                // 将cb的参与者数量加1,都会等待
                cb.await();

            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
