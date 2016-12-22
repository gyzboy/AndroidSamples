package com.gyz.javasamples.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */

public class CountDownLatchTest {


    //CountDownLatch是通过“共享锁”实现的。在创建CountDownLatch中时，会传递一个int类型参数count，该参数是“锁计数器”的初始状态，
    // 表示该“共享锁”最多能被count给线程同时获取。当某线程调用该CountDownLatch对象的await()方法时，该线程会等待“共享锁”可用时，
    // 才能获取“共享锁”进而继续运行。而“共享锁”可用的条件，就是“锁计数器”的值为0！而“锁计数器”的初始值为count，
    // 每当一个线程调用该CountDownLatch对象的countDown()方法时，才将“锁计数器”-1；通过这种方式，必须有count个线程调用countDown()之后，
    // “锁计数器”才为0，而前面提到的等待线程才能继续运行！
    private static int LATCH_SIZE = 5;
    private static CountDownLatch doneSignal;
    public static void main(String[] args) {

        try {
            doneSignal = new CountDownLatch(LATCH_SIZE);

            // 新建8个任务
            for(int i=0; i<LATCH_SIZE ; i++)//小于size的话就会一直等待,不会向下执行
                new InnerThread().start();

            System.out.println("main await begin.");
            // "主线程"等待线程池中5个任务的完成
            doneSignal.await();

            System.out.println("main await finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class InnerThread extends Thread{
        public synchronized void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " sleep 1000ms.");
                // 将CountDownLatch的数值减1
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
