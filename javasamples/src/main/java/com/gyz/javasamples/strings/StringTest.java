package com.gyz.javasamples.strings;

/**
 * Created by guoyizhe on 16/8/25.
 * 邮箱:gyzboy@126.com
 */
public class StringTest {
    public static void main(String[] args){
        String s = "333";//不可变
        f(s);
        System.out.println(s);

       byte[] bytes = s.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            System.out.println(bytes[i]);
        }

        char[] c = s.toCharArray();
        System.out.println("toCharArray:");
        for (int i = 0; i < c.length; i++) {
            System.out.println(c[i]);
        }

        String news = "456";
        System.out.println("Concat:");
        System.out.println(news.concat(s));

        System.out.println(news.replaceAll(".","/"));//replaceAll第一个参数为正则表达式,"."表示任意字符


    }

    static void f(String s){
        s = "123";
    }
}


