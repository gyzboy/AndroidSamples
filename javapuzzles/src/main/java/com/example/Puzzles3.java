package com.example;

/**
 * Created by guoyizhe on 16/9/21.
 * 邮箱:gyzboy@126.com
 */
public class Puzzles3 {
    public static void main(String[] args){
        //已经发生溢出,因为是int类型的数值进行相乘会先成为int类型然后在转化为long型,在生成int类型的数值时已经发生了溢出
//        final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;
        final long MICROS_PER_DAY = 24L * 60 * 60 * 1000 * 1000;
        final long MILLIS_PER_DAY = 24L * 60 * 60 * 1000;
        System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
    }
}
//PS:
//在操作大的数值时一定要防止数值过大造成的溢出,因为它很难觉察,但是会造成错误的结果
