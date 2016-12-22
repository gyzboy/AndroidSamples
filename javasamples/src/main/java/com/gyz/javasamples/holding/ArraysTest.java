package com.gyz.javasamples.holding;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by guoyizhe on 16/8/31.
 * 邮箱:gyzboy@126.com
 */
public class ArraysTest {
    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1,2,3,4,5));//内部通过数组实现
        double[] arr = new double[10000001];
        for (int index = 0; index < 10000000; index++) {
            arr[index]= Math.random();
        }
        System.out.println("The start time  " + System.currentTimeMillis());
        Arrays.sort(arr);//内部为快排
        System.out.println("The end   time  " + System.currentTimeMillis());

        int[] binary = {1,2,3,4,5};
        System.out.println(Arrays.binarySearch(binary,3));//二分查找,返回索引

        int[] fill = {4,3,5,6};
        int fill2 = 3;
        Arrays.fill(fill,fill2);//fill2填充至fill
        for (int i = 0; i < fill.length; i++) {
            System.out.println("fill  " + fill[i]);
        }

        int[] copy = Arrays.copyOf(fill,5);//超出原数组补0
        for (int i = 0; i < copy.length; i++) {
            System.out.println("copy  " + copy[i]);
        }
        int[] copy2 = Arrays.copyOfRange(fill,2,5);//超出原数组补0
        for (int i = 0; i < copy2.length; i++) {
            System.out.println("copyOfRange  " + copy2[i]);
        }

        String[] str1 = new String[]{"3","3","5"};
        String[] str2 = new String[]{"3","3","5"};
        System.out.println("deepEquals  " + Arrays.deepEquals(str1,str2));

    }
}
