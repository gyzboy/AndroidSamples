package com.gyz.javasamples.concurrent;


import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */

//Callable 和 Future 是比较有趣的一对组合。当我们需要获取线程的执行结果时，就需要用到它们。Callable用于产生结果，Future用于获取结果。
//Callable 是一个接口，它只包含一个call()方法。Callable是一个返回结果并且可能抛出异常的任务。
//为了便于理解，我们可以将Callable比作一个Runnable接口，而Callable的call()方法则类似于Runnable的run()方法。

//Future 是一个接口。它用于表示异步计算的结果。提供了检查计算是否完成的方法，以等待计算的完成，并获取计算的结果。
public class CallableAndFutureTest {

    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        //创建一个线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        //创建有返回值的任务
        Callable c1 = new MyCallable();
        //执行任务并获取Future对象
        Future f1 = pool.submit(c1);
        FutureTask<Integer> task = new FutureTask<Integer>(c1);
        new Thread(task).start();
        // 输出结果
//        System.out.println(f1.get());
        System.out.println(task.get());
        //关闭线程池
        pool.shutdown();
    }
}


class MyCallable implements Callable {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        // 执行任务
        for (int i = 0; i < 100; i++)
            sum += i;
        //return sum;
        return Integer.valueOf(sum);
    }
}