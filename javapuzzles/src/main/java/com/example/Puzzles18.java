package com.example;

import java.io.IOException;
import java.io.InterruptedIOException;

public class Puzzles18 implements T3 {
    public static void main(String[] args) {
        T3 t3 = new Puzzles18();
        t3.f();//这里打印 Hello  因为异常取的是交集而不是合集
    }


    static void f1() {
        //如果一个catch语句抛出一个Exception的子类型异常而try语句中没有抛出,则发生一个编译期错误

//        try {
//            System.out.println("Hello world");
//        } catch (IOException e) {
//            System.out.println("fail");
//        }
    }

    static void f2() {
        //捕获Exception或Throwable的catch语句是合法的
        try {

        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    @Override
    public void f() {
        System.out.println("Hello");
    }
}


interface T1 {
    void f() throws CloneNotSupportedException;
}

interface T2 {
    void f() throws InterruptedIOException;
}

interface T3 extends T1, T2 {
}


