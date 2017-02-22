package com.gyz.javasamples.concurrent;

/**
 * Created by guoyizhe on 2017/2/21.
 * 邮箱:gyzboy@126.com
 */

public class VolatileTest {

    public static volatile int race = 0;

    public static void increase(){
        race++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 100; i1++) {
                    increase();
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount() > 1){
            Thread.yield();
        }
        System.out.println(race);
    }

}
