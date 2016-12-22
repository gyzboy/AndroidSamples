package com.gyz.javasamples.holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

/**
 * Created by guoyizhe on 16/8/31.
 * 邮箱:gyzboy@126.com
 */
public class ListTest {

//    ArrayList 是一个数组队列，相当于 动态数组。与Java中的数组相比，它的容量能动态增长。它继承于AbstractList，实现了List, RandomAccess, Cloneable, java.io.Serializable这些接口。
//
//    ArrayList 继承了AbstractList，实现了List。它是一个数组队列，提供了相关的添加、删除、修改、遍历等功能。
//    ArrayList 实现了RandmoAccess接口，即提供了随机访问功能。RandmoAccess是java中用来被List实现，为List提供快速访问功能的。
// 在ArrayList中，我们即可以通过元素的序号快速获取元素对象；这就是快速随机访问。稍后，我们会比较List的“快速随机访问”和“通过Iterator迭代器访问”的效率。
//
//    ArrayList 实现了Cloneable接口，即覆盖了函数clone()，能被克隆。
//
//    ArrayList 实现java.io.Serializable接口，这意味着ArrayList支持序列化，能通过序列化去传输。
//
//    和Vector不同，ArrayList中的操作不是线程安全的！所以，建议在单线程中才使用ArrayList，而在多线程中可以选择Vector或者CopyOnWriteArrayList。

    public static void main(String[] args) {


        Random rand = new Random(47);
        String[] str = {"234", "333", "435"};
        ArrayList<String> pets = new ArrayList<>(Arrays.asList(str));
        print("1: " + pets);
        String h = "444";
        pets.add(h);
        print("2: " + pets);
        print("3: " + pets.contains(h));//list中的方法大都通过循环去操作的
        pets.remove(h);
        String p = pets.get(2);
        print("4: " + p + " " + pets.indexOf(p));
        String cymric = "333";
        print("5: " + pets.indexOf(cymric));
        print("6: " + pets.remove(cymric));
        print("7: " + pets.remove(p));
        print("8: " + pets);
        pets.add(1, "555");
        pets.add(2, "566");
        print("9: " + pets);
        List<String> sub = pets.subList(1, 2);
        print("subList: " + sub);
        print("10: " + pets.containsAll(sub));
        Collections.sort(sub);
        print("sorted subList: " + sub);
        print("11: " + pets.containsAll(sub));//顺序不重要
        Collections.shuffle(sub, rand);
        print("shuffled subList: " + sub);
        print("12: " + pets.containsAll(sub));
        List<String> copy = new ArrayList<String>(pets);
        sub = Arrays.asList(pets.get(1), pets.get(2));
        print("copy:" + copy);
        print("sub: " + sub);
        copy.retainAll(sub);//交集，依赖于equal方法
        print("13: " + copy);
        copy = new ArrayList<String>(pets);
        copy.remove(2);
        print("14: " + copy);
        copy.removeAll(sub);
        print("15: " + copy);
        copy.set(0, "555");
        print("16: " + copy);
        copy.addAll(1, sub);
        print("17: " + copy);
        print("18: " + pets.isEmpty());
        pets.clear();
        print("19: " + pets);
        print("20: " + pets.isEmpty());
        pets.add("111");
        pets.add("222");
        Object[] o = pets.toArray();
        print("21: " + o[1]);
        String[] pa = pets.toArray(new String[0]);
        print("22: " + pa[1]);
        pets.add("\0");
        print("23: " + pets);
        pets.trimToSize();//用于减小arrayList多申请的空间
        print("24: " + pets);


        List list = new ArrayList();
        for (int i=0; i<100000; i++)
            list.add(i);
        //isRandomAccessSupported(list);
        iteratorThroughRandomAccess(list) ;
        iteratorThroughIterator(list) ;
        iteratorThroughFor2(list) ;

    }

    public static void print(Object obj) {
        System.out.println(obj);
    }


    private static void isRandomAccessSupported(List list) {
        if (list instanceof RandomAccess) {
            System.out.println("RandomAccess implemented!");
        } else {
            System.out.println("RandomAccess not implemented!");
        }

    }

    public static void iteratorThroughRandomAccess(List list) {

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for (int i=0; i<list.size(); i++) {
            list.get(i);
        }
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughRandomAccess：" + interval+" ms");
    }

    public static void iteratorThroughIterator(List list) {

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for(Iterator iter = list.iterator(); iter.hasNext(); ) {
            iter.next();
        }
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughIterator：" + interval+" ms");
    }


    public static void iteratorThroughFor2(List list) {

        long startTime;
        long endTime;
        startTime = System.currentTimeMillis();
        for(Object obj:list)
            ;
        endTime = System.currentTimeMillis();
        long interval = endTime - startTime;
        System.out.println("iteratorThroughFor2：" + interval+" ms");
    }

    // toArray(T[] contents)调用方式一
    public static Integer[] vectorToArray1(ArrayList<Integer> v) {
        Integer[] newText = new Integer[v.size()];
        v.toArray(newText);
        return newText;
    }

    // toArray(T[] contents)调用方式二。最常用！
    public static Integer[] vectorToArray2(ArrayList<Integer> v) {
        Integer[] newText = (Integer[])v.toArray(new Integer[0]);
        return newText;
    }

    // toArray(T[] contents)调用方式三
    public static Integer[] vectorToArray3(ArrayList<Integer> v) {
        Integer[] newText = new Integer[v.size()];
        Integer[] newStrings = (Integer[])v.toArray(newText);
        return newStrings;
    }
}
