package com.gyz.javasamples.operators;

/**
 * Created by guoyizhe on 16/8/29.
 * 邮箱:gyzboy@126.com
 */
public class OperatorTest {

    static void f1(int a){
        a = 30;
    }

    static void f2(Test t){
        t.t = 1000;
    }

    static void f3(String s){
        s = "aaa";
    }


    public static void main(String[] args) {
        String assignment = "赋值操作:\n" +
                "   将'='右边的值复制给左边\n" +
                "   1.对于基本类型,因为基本类型存储了实际的数值,而并非一个指向对象的引用,所以在其赋值的时候直接就是讲一个地方的内容复制到另一个地方\n" +
                "   2.对于对象类型,因为操作的是对象的引用,修改对象的值对所有持有对象引用的对象都有影响";

        System.out.println(assignment);

        int a = 0;
        int b = 100;
        a = b;
        System.out.println(a);//a为100
        a = 40;
        System.out.println(a);//a为40
        System.out.println(b);//b依旧为100
        f1(a);
        System.out.println(a);//a没有改变，因为是直接复制了一个对象



        Test t1 = new Test();
        Test t2 = new Test();
        t1.t = 50;
        t2.t = 100;
        System.out.println("1. t1.t = " + t1.t + " t2.t = " + t2.t);//50,100

        //这个现象叫"别名现象"
//        t1 = t2;
//        System.out.println("2. t1.t = " + t1.t + " t2.t = " + t2.t);//都为100
//        t1.t = 30;
//        System.out.println("3. t1.t = " + t1.t + " t2.t = " + t2.t);//都为30

        //为避免"别名现象"
        t1.t = t2.t;
        System.out.println("2. t1.t = " + t1.t + " t2.t = " + t2.t);//都为100
        t1.t = 30;
        System.out.println("3. t1.t = " + t1.t + " t2.t = " + t2.t);//30,100

        f2(t1);
        System.out.println("t1.rt = " + t1.t);//t1发生了改变,因为引用发生了改变

        String s = "abc";
        f3(s);
        System.out.println(s);//string是个特例,因为string是个不可变对象

        int i = 10;
        System.out.println("i = " + i);
        System.out.println("i++ = " + i++);//后缀运算会先生成值,再计算，运算结束前得到值
        System.out.println("++i = " + ++i);//前缀运算会先执行计算,再生成值,运算结束后得到值

        float f = 3.6f;
        System.out.println("(int)f = " + (int)f);//截尾
        System.out.println("Math.round(f) = " + Math.round(f));//四舍五入


        String hashcode = "HashCode和equals的区别:\n" +
                "     HashCode是jdk根据对象的地址或字符串或者数字利用hash算法计算出的int类型的数值。Java采用了哈希表的原理，将数据依照特定算法直接指定到一个地址上，这样可以简单的理解为hashCode方法返回的就是对象存储位置的映像。\n" +
                "     因此HashCode能够快速的定位对象所在的地址，并且根据Hash常规协定，如果两个对象相等，则他们一定有相同的HashCode。而equals方法对比两个对象实例是否相等时，对比的就是对象示例的ID（内存地址）是否是同一个对象实例；该方法是利用的等号（==）的判断结果。所以HashCode的效率远远大于equals\n" +
                "     但是HashCode并不保证唯一性，因此当对象的HashCode相同时，再利用equals方法来判断两个对象是否相同，就大大加快了对比的速度";

        System.out.println(hashcode);

        System.out.println(100 << 2);

    }
}

class Test {
    int t;
}
