package com.gyz.javasamples.concurrent;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */
class InterruptThread extends Thread {

    public InterruptThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        try {
            int i=0;
            while (!isInterrupted()) {//在调用interrupt后会清除标记状态,isInterrupted会返回false
                Thread.sleep(100); // 休眠100ms
                i++;
                System.out.println(Thread.currentThread().getName()+" ("+this.getState()+") loop " + i);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +" ("+this.getState()+") catch InterruptedException.");
        }
    }
}


//public void interrupt():不会中断正在执行的线程，只是将线程的标志位设置成true,如果再调用join等会抛出异常
//public static boolean interrupted():线程的中断状态由该方法清除
//public boolean isInterrupted():测试线程是否已经中断。线程的中断状态不受该方法的影响。在catch块中捕获到这个异常时，
// 线程的中断标志位已经被设置成false了，因此在此catch块中调用t.isInterrupted(),Thread.interrupted()始终都为false

public class InterruptTest {

    public static void main(String[] args) {
        try {
            Thread t1 = new InterruptThread("t1");  // 新建“线程t1”
            System.out.println(t1.getName() +" ("+t1.getState()+") is new.");

            t1.start();                      // 启动“线程t1”
            System.out.println(t1.getName() +" ("+t1.getState()+") is started.");

            // 主线程休眠300ms，然后主线程给t1发“中断”指令。
            Thread.sleep(300);
            t1.interrupt();
            System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted.");

            // 主线程休眠300ms，然后查看t1的状态。
            Thread.sleep(300);
            System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted now.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
