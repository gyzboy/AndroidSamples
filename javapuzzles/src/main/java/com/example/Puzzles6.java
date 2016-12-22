package com.example;

public class Puzzles6 {
    public static void main(String[] args) {
        char x = 'X';
        int i = 3;
        System.out.println(true ? x : 0);
        System.out.println(false ? i : x);
        System.out.println(false ? x : i);

    }
}
//PS:
//1.如果第二个和第三个操作数相同,那就是表达式类型
//2.第一个操作数是T,T表示byte、short、char,另一个操作数是int类型,条件表达式的类型就是T
//3.否则,对操作数类型进行二进制数字提升,表达式类型就是第二个、第三个操作数被提升后的类型
