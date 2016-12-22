package com.example;

public class Puzzles10 {
    public static void main(String[] args) {
        System.out.println("a\u0022.length() + \u0022b".length());//  \u0022被转义为 ”,所以这个就等价于a".length() + "b",所以结果为2
        System.out.println("a\".length() + \"b".length());// \"使"被转义,每个unicode字符在源文件中使用6个字符表示但只相当于一个字符,所以结果为16
    }
}
//PS:
//java对在字符串字面常量中的Unicode转义字符没有提供任何特殊处理,编译器在将程序解析成各种符号之前,先将
//unicode转移字符装换为它们所表示的字符