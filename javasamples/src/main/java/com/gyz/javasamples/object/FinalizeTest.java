package com.gyz.javasamples.object;

/**
 * Created by guoyizhe on 2017/2/15.
 * 邮箱:gyzboy@126.com
 */

public class FinalizeTest {
    public static FinalizeTest SAVE = null;

    public void isAlive(){
        System.out.println("yes , i am still alive ");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeTest.SAVE = this;
    }
    public static void main(String[] args) throws InterruptedException {
        SAVE = new FinalizeTest();

        //第一次拯救成功,因为任何一个对象的finalize方法只会执行一次,如果对象面临下一次回收,它的finalize方法不会被再次执行
        SAVE = null;
        System.gc();

        Thread.sleep(500);
        if (SAVE == null) {
            System.out.println("no i am dead");
        }else{
            SAVE.isAlive();
        }

        //第二次拯救失败
        SAVE = null;
        System.gc();

        Thread.sleep(500);
        if (SAVE == null) {
            System.out.println("no i am dead");
        }else{
            SAVE.isAlive();
        }
    }
}
