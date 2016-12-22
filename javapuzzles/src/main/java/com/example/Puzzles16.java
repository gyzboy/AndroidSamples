package com.example;

public class Puzzles16 {
    public static void main(String[] args) {
        int i = 0;
        while (-1 << i != 0) {
            i++;
        }
        System.out.println(i);//无限循环,不会有任何输出
    }
}
//PS:
//位移长度是对32取余的,如果做左操作数是long类型的,则对64取余
