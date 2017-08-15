package com.gyz.javasamples.strings;

import java.util.ArrayList;
import java.util.List;

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

        //testSubStringOOM();

    }

    static void f(String s){
        s = "123";
    }

    public static void testSubStringOOM(){
        List<String> strList = new ArrayList<>();
        String str = new String(new char[100000]);
        for (int i = 0; i < 10000; i++) {
            strList.add(str.substring(1,5));
            //jdk1.6会出现OOM,具体流程为:调用str的substring方法得到b,其实就是调整了一下b的offset和count,用的内容还是str的字符数组,并没有重新创建专属b的内容字符数组
            //如果有一个1G的字符串a，我们使用substring(0,2)得到了一个只有两个字符的字符串b,如果b的生命周期长于a或者手动设置a为null,当垃圾回收进行后,a被回收掉,b没有被
            //回收掉,那么这1G的内存占用依旧存在,因为b持有这1G大小的字符数组的引用.

            //但是共享内容数组是一个很棒的设计,这样避免了每次进行字符数组复制

            //解决方法是,String little = new String(largeString.subString(0,2));,这个string的构造方法中在源字符串内容数组长度大于字符串长度时，进行数组复制，新的字符串会创建一个只包含源字符串内容的字符数组
        }
    }


}


