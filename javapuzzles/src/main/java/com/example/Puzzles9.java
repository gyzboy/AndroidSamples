package com.example;

public class Puzzles9 {
    public static void main(String[] args) {
        String s = "abc";
        char[] num = {'1', '2', '3'};
        System.out.println(s + num);
        System.out.println(num);//调用的object的重载版本而不是char[]的重载版本
    }
}
//PS:
//char数组不是字符串,要想将一个char数组转化成一个字符串,就要调用String.valueOf(char[])方法
