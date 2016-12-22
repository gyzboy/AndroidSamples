package com.example;

public class Puzzles12 {
    public static void main(String[] args) {
        long x = Long.MAX_VALUE;
        double y = (double) Long.MAX_VALUE;
        long z = Long.MAX_VALUE - 1;

        System.out.println((x == y) + "");//true
        System.out.println((y == z) + "");//true
        System.out.println(x == z);//false
    }
}
//PS:
//int或long转换为float值,或者long转换为double时,都会造成精度丢失