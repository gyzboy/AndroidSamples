package com.gyz.javasamples.concurrent;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */
public class YieldTest {
    //wait()是让线程由“运行状态”进入到“等待(阻塞)状态”，而不yield()是让线程由“运行状态”进入到“就绪状态”。但是，并不能保证在当前线程调用yield()之后，其它具有相同优先级的线程就一定能获得执行权
    //wait()是会线程释放它所持有对象的同步锁，而yield()方法不会释放锁。

    private static Object obj = new Object();

    public static void main(String[] args){
        ThreadA t1 = new ThreadA("t1");
        ThreadA t2 = new ThreadA("t2");
        t1.start();
        t2.start();
    }

    static class ThreadA extends Thread{
        public ThreadA(String name){
            super(name);
        }
        public void run(){
            // 获取obj对象的同步锁
            synchronized (obj) {
                for(int i=0; i <10; i++){
                    System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i);
                    // i整除4时，调用yield
                    if (i%4 == 0)
                        Thread.yield();
                }
            }
        }
    }
}
