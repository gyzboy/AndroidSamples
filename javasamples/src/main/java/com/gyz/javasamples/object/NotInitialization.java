package com.gyz.javasamples.object;

/**
 * Created by guoyizhe on 2017/2/17.
 * 邮箱:gyzboy@126.com
 */

public class NotInitialization {
    public static void main(String[] args) {
//        System.out.println(SubbClass.value);//没有触发子类的初始化

//        SuperClass[] sca = new SuperClass[10];//不会触发super的初始化

        System.out.println(SuperClass.HELLO);//不会触发super的初始化


    }
}


class SuperClass {
    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;

    public static final String HELLO = "hello world";
}

class SubbClass extends SuperClass {
    static {
        System.out.println("SubClass init!");
    }
}
