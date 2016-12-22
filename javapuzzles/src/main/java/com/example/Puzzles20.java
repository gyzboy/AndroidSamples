package com.example;

public class Puzzles20 {
    public static void main(String[] args) {

        new Struct(null);
    }
}

class Struct {
    public Struct(Object o) {
        System.out.println("object");
    }

    public Struct(double[] arr) {
        System.out.println("double");
    }
}

//PS:
//这里输出的double, 构造器遵循最精确匹配原则,对于两个构造器,double[]类型的所有参数都适用于
//object,所以,传入NULL的话匹配到的是double参数类型的构造器
