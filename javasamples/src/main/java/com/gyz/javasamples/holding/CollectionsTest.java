package com.gyz.javasamples.holding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoyizhe on 16/8/31.
 * 邮箱:gyzboy@126.com
 */
public class CollectionsTest {
    public static void main(String[] args) {
        List<String> sort = new ArrayList<>();
        sort.add("3");
        sort.add("5");
        sort.add("1");
        sort.add("9");
        Collections.sort(sort);//内部arrays.sort实现
        System.out.println("sort");
        output(sort);

        System.out.println("min:  " + Collections.min(sort));
        System.out.println("max:  " + Collections.max(sort));

        System.out.println("index:  " + Collections.binarySearch(sort, "10"));//返回索引，没找到返回-(low+1)

        Collections.reverse(sort);//18是临界点,小于18使用for循环，大于等于18使用两个listIteractor
        System.out.println("reverse:");

        output(sort);

        Collections.rotate(sort, 2);//100是临界点
        System.out.println("rotate:");
        output(sort);

//        Collections.shuffle(sort);//乱序,当size小于5时跟大于等于5时使用的策略不同
//        System.out.println("shuffle:");
//        output(sort);

        Collections.swap(sort, 2, 3);
        System.out.println("swap:");
        output(sort);

        Collections.fill(sort, "100");//当size大于等于25时使用的listIterator
        System.out.println("fill:");
        output(sort);

        List<String> copy = new ArrayList<>();
        copy.add("");
        copy.add("");
        copy.add("");
        copy.add("");
        Collections.copy(copy, sort);//size小于10使用for循环
        System.out.println("copy:");
        output(copy);

        Collections.replaceAll(sort, "10", "5");//元素不存在就替换失败,没有异常
        System.out.println("replaceAll:");
        output(sort);

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("123456");
        list1.add("123");
        list1.add("123");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("123");
        System.out.println("indexOfSubList:");
        System.out.println(Collections.indexOfSubList(list1, list2));//不存在就返回-1
        System.out.println("lastIndexOfSubList:");
        System.out.println(Collections.lastIndexOfSubList(list1, list2));//不存在就返回-1


//        Collections.unmodifiableList(sort).add("3333");//只读属性,会抛出异常
//        output(Collections.unmodifiableList(sort));

//        List lis = Collections.emptyList();
//        lis.add("333");


    }

    static void output(List<String> ss) {
        for (String s : ss) {
            System.out.println(s);
        }
    }
}
