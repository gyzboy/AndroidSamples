package com.example;

public class Puzzles8 {
    public static void main(String[] args) {
        System.out.println("H" + "A");//HA
        System.out.println('H' + 'A');//137

        System.out.println("" + 'H' + 'A');
        System.out.println("2 + 2 = " + 2 + 2);
        System.out.printf("%c%c",'H','A');

    }
}
//PS:
//当且仅当+操作符的操作数中至少有一个是String类型时,才会执行字符串连接操作,否则,执行加法
//如果要连接的数值没有一个是字符串类型,可以有几种选择:
//1.预置一个空字符串
//2.将第一个数值使用String.valueOf显式的转化成一个字符串
//3.使用一个字符串缓冲区StringBuilder
//4.使用printf打印