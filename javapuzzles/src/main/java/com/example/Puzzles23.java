package com.example;

public class Puzzles23 {
    public static void main(String[] args) {
        String s = null;
        System.out.println(s instanceof String);//instanceof操作符当左操作数为null时返回false
//        System.out.println(new Puzzles23() instanceof String);//如果两个操作数都是类,其中一个必须是另一个的子类型
        Puzzles23 p = (Puzzles23) new Object();//在转型中,若果两个操作数都是类,那么其中一个必须是另一个的子类型
    }
}

