package com.example;

import java.util.Calendar;

public class Puzzles22 {

    public static final Puzzles22 INSTANCE = new Puzzles22();
    private final int size;
    private static final int CURRENT = Calendar.getInstance().get(Calendar.YEAR);

    public Puzzles22() {
        size = CURRENT - 1900;
    }

    public int getSize() {
        return size;
    }


    public static void main(String[] args) {
        System.out.println("size is" + INSTANCE.getSize());


        System.out.println(Cache.getSum());
    }
}
//ps:
// 代码执行顺序: 静态域(INSTANCE调用构造方法,但构造不执行,所以为NULL,CURRENT为0)-->域(size初始值为0)->构造(size = -1900)


class Cache {
    public static int sum;

    static {
        sum();
    }
    public static boolean isSumed = false;

    static int getSum() {
        sum();
        return sum;
    }

    private synchronized static void sum() {
        if (!isSumed) {

            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            isSumed = true;
        }
    }
}
