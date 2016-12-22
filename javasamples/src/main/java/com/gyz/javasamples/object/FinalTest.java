package com.gyz.javasamples.object;

import java.util.Random;

/**
 * Created by guoyizhe on 16/8/30.
 * 邮箱:gyzboy@126.com
 */
public class FinalTest {
    private static Random rand = new Random(47);
    private String id;
    //final的基本类型参数在编译时执行,减少运行时负担,初始化时或者构造器中必须赋值:
    private final int valueOne = 9;
    private final int valueBlank;

    private static final int VALUE_TWO = 99;
    public static final int VALUE_THREE = 39;
    //无法在编译时执行:
    private final int i4 = rand.nextInt(20);
    static final int INT_5 = rand.nextInt(20);
    private Value v1 = new Value(11);
    private final Value v2 = new Value(22);
    private static final Value VAL_3 = new Value(33);
    // Arrays:
    private final int[] a = { 1, 2, 3, 4, 5, 6 };

    public FinalTest(String id,int valueBlank) {
        this.id = id;
        this.valueBlank = valueBlank;
    }

    public String toString() {
        return id + ": " + "v1 =" + v1.i + ", v2 =" + v2.i + ", i4 = " + i4 + ", INT_5 = " + INT_5;
    }
    public static void main(String[] args) {
        FinalTest fd1 = new FinalTest("fd1",0);
        //! fd1.valueOne++; // Error
        fd1.v2.i++; //fd1发生变化
        fd1.v1 = new Value(9); // OK -- not final
        for(int i = 0; i < fd1.a.length; i++) {
            fd1.a[i]++;
            System.out.println("a[" + i + "]" + fd1.a[i]);
        }
        //! fd1.v2 = new Value(0); // Error
        //! fd1.VAL_3 = new Value(1); // Error:引用发生变化
        //! fd1.a = new int[3];
        System.out.println(fd1);
        System.out.println("Creating new FinalData");
        FinalTest fd2 = new FinalTest("fd2",0);
        System.out.println(fd1);
        System.out.println(fd2);

        FinalTest fdBlank = new FinalTest("fd3",5);
        System.out.println(fdBlank.valueBlank);
    }
}
class Value {
    int i;
    public Value(int i) { this.i = i; }
}