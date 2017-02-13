package com.gyz.javasamples;

public class MyClass {

    public static void main(String[] args){
        System.out.println('a' + 7 + "hello");//'a'先转换为int,'a'对应的ASIC码为97,97+7=104
        double a = Double.NEGATIVE_INFINITY;
        float b = Float.NEGATIVE_INFINITY;
        System.out.println(a == b);//负无穷大是相等的
        System.out.println(0.0/0.0);//出现非数
        System.out.println(0.0/0.0 == Float.NaN);//两个非数之间不相等
        System.out.println(6.0/0==555.0/0);//所有正无穷大都是相等的
        System.out.println(-8/0.0);//得到负无穷大
    }
}
