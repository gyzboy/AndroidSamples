package com.gyz.javasamples.random;

import java.util.Random;



/**
 * Created by guoyizhe on 16/9/6.
 * 邮箱:gyzboy@126.com
 */

public class RandomTest{

    public static void main(String args[]){

        // 通过System的currentTimeMillis()返回随机数
        testSystemTimeMillis();

        // 通过Math的random()返回随机数
        testMathRandom();

        // 新建“种子为1000”的Random对象，并通过该种子去测试Random的API
        testRandomAPIs(new Random(1000), " 1st Random(1000)");
        testRandomAPIs(new Random(1000), " 2nd Random(1000)");
        // 新建“默认种子”的Random对象，并通过该种子去测试Random的API
        testRandomAPIs(new Random(), " 1st Random()");
        testRandomAPIs(new Random(), " 2nd Random()");
    }

    /**
     * 返回随机数-01：测试System的currentTimeMillis()
     */
    private static void testSystemTimeMillis() {
        // 通过
        final long l = System.currentTimeMillis();
        // 通过l获取一个[0, 100)之间的整数
        final int i = (int)( l % 100 );

        System.out.printf("\n---- System.currentTimeMillis() ----\n l=%s i=%s\n", l, i);
    }


    /**
     * 返回随机数-02：测试Math的random()
     */
    private static void testMathRandom() {
        // 通过Math的random()函数返回一个double类型随机数，范围[0.0, 1.0)
        final double d = Math.random();
        // 通过d获取一个[0, 100)之间的整数
        final int i = (int)(d*100);

        System.out.printf("\n---- Math.random() ----\n d=%s i=%s\n", d, i);
    }


    /**
     * 返回随机数-03：测试Random的API
     */
    private static void testRandomAPIs(Random random, String title) {
        final int BUFFER_LEN = 5;

        // 获取随机的boolean值
        boolean b = random.nextBoolean();
        // 获取随机的数组buf[]
        byte[] buf = new byte[BUFFER_LEN];
        random.nextBytes(buf);
        // 获取随机的Double值，范围[0.0, 1.0)
        double d = random.nextDouble();
        // 获取随机的float值，范围[0.0, 1.0)
        float f = random.nextFloat();
        // 获取随机的int值
        int i1 = random.nextInt();
        // 获取随机的[0,100)之间的int值
        int i2 = random.nextInt(100);
        // 获取随机的高斯分布的double值
        double g = random.nextGaussian();
        // 获取随机的long值
        long l = random.nextLong();

        System.out.printf("\n---- %s ----\nb=%s, d=%s, f=%s, i1=%s, i2=%s, g=%s, l=%s, buf=[",
                title, b, d, f, i1, i2, g, l);
        for (byte bt:buf)
            System.out.printf("%s, ", bt);
        System.out.println("]");
    }
}
