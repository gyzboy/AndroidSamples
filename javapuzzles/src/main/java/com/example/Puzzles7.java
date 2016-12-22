package com.example;

public class Puzzles7 {
    public static void main(String[] args) {

        short x = 0;
        int i = 123456;

        System.out.println(x += i);//自动转型,将int的高两位截掉

        Object o = "ooo";
        String s = "sss";

        o = o + s;
        System.out.println(o);//o+=s 非法,因为String属于object类型

        String s1 = "lenght: 10";
        String s2 = "lenght: " + s1.length();
        //1.==比较的是对象地址 equals比较的是对象的值
        //2. + 的优先级高于其他操作符 所以打印出来的是false而不是result is false
        System.out.println("result is" + s1 == s2);
        System.out.println("result is " + (s1 == s2));
    }
}
