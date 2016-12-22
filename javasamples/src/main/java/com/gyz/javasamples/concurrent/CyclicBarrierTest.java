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

        cb = new CyclicBarrier(SIZE, new Runnable() {
            public void run() {
                System.out.println("CyclicBarrier's parties is: " + cb.getParties());
            }
        });

        // 新建5个任务
        for (int i = 0; i < SIZE ; i++)//小于的话也是一直等待,无法向下执行
            new InnerThread().start();
    }

    static class InnerThread extends Thread {
        public synchronized void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " wait for CyclicBarrier.");

                // 将cb的参与者数量加1
                cb.await();

            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
