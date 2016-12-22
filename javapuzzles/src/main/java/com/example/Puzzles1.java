package com.example;

public class Puzzles1 {
    public static void main(String[] args) {
        int i = -5;
        System.out.println(isOdd(i));
    }

    static boolean isOdd(int i){
//        return i % 2 == 1;//wrong 对所有负数都失效
//        return i % 2 != 0;
        return (i & 1) != 0;//最高效率
    }
}
