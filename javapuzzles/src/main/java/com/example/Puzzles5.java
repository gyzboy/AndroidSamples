package com.example;

public class Puzzles5 {
    public static void main(String[] args) {
        //输出 65535

        //经历了几个步骤,-1是int类型所以:
        // int->byte->char->int  char是无符号类型,byte转char时会发生零扩展,char的16位就都被置位了,就成了65535
        System.out.println((int)(char)(byte)-1);

    }
}

//PS:
//如果最初的数值类型是有符号的,那么就执行符号扩展,如果它是char,那么不管它将要被转换成什么类型,都执行零扩展
