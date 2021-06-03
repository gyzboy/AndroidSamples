package com.gyz.javasamples.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by guoyizhe on 2017/9/11.
 * 邮箱:gyzboy@126.com
 *
 * @author guoyizhe
 * @date 2017/09/11
 */

public class ExecutorsTest {
    public static void main(String[] args) {
        //避免通过Executor去创建线程池
//        FixedThreadPool 和 SingleThreadPool:
//        允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
//        CachedThreadPool 和 ScheduledThreadPool:
//        允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM。
        ExecutorService pool = Executors.newFixedThreadPool(5);


        /**
         * 线程池初始化方法
         *
         * corePoolSize 核心线程池大小----10
         * maximumPoolSize 最大线程池大小----30
         * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
         * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
         * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列
         * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
         * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
         *                          即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
         *                                任务会交给RejectedExecutionHandler来处理
         */
        ThreadPoolExecutor ePool = new ThreadPoolExecutor(3,
                5,
                30,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());
        for (int i = 0; i < 10; i++) {
            int val = i;
            ePool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(val);
                }
            });
        }
    }
}

class CustomThreadFactory implements ThreadFactory {

    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        String threadName = ExecutorsTest.class.getSimpleName() + count.addAndGet(1);
        System.out.println(threadName);
        t.setName(threadName);
        return t;
    }
}


class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // 记录异常
        // 报警处理等
        System.out.println("error.............");
    }
}

