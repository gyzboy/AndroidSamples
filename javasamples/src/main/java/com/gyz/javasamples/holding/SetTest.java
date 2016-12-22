package com.gyz.javasamples.holding;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by guoyizhe on 16/9/1.
 * 邮箱:gyzboy@126.com
 */
public class SetTest {
    public static void main(String[] args) {
        Random rand = new Random();
        Set<Integer> intset = new HashSet<Integer>();//散列,所以不保证有序
//这里插入HashSet的是Integer，其hashCode()实现就返回int值本身。所以在对象hashCode这一步引入了巧合的“按大小排序”。
//然后HashMap.hash(Object)获取了对象的hashCode()之后会尝试进一步混淆。
//JDK8版java.util.HashMap内的hash算法比JDK7版的混淆程度低；在[0, 2^32-1]范围内经过HashMap.hash()之后还是得到自己。
//例子正好落入这个范围内。外加load factor正好在此例中让这个HashMap没有hash冲突，这就导致例中元素正好按大小顺序插入在HashMap的开放式哈希表里。


        String set = "Set 的内部通常是基于 Map 来实现的，Map 中的 Key 构成了 Set，而 Value 全部使用一个无意义的 Object 。 \n" +
                "Set 的特征与其内部的 Set 的特征是一致的。基于 HashMap 的 HashSet 是无序时的最佳通用实现，基于 LinkedHashMap 的 LinkedHashSet 保留插入或访问的顺序(需定义hashCode)，\n" +
                "基于 TreeMap 的 TreeSet 可以按照元素升序排列，要求元素实现 Comaprable 接口或自定义比较器。\n" +
                "HashSet , LinkedHashSet, TreeSet 都不是线程安全的，在多线程环境下使用时要注意同步问题。\n" +
                "CopyOnWriteArraySet 是一个线程安全的实现，但是并不是基于 Map 实现的，而是通过 CopyOnWriteArrayList 实现的。使用 addIfAbsent() 方法进行去重，性能比较一般";
        System.out.println(set);



        for (int i = 0; i < 10000; i++)
            intset.add(rand.nextInt(20) + 1 << 2);
        intset.add(null);
        Iterator<Integer> it = intset.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

        TreeSet<String> ts = new TreeSet<>();//有序的,内部实现为二叉树
        ts.add("234");
        ts.add("456");
        ts.add("345");
        System.out.println(ts);

        LinkedHashSet<String> lhs = new LinkedHashSet<>();
        lhs.add("111");
        lhs.add("333");
        lhs.add("222");
        System.out.println(lhs);

    }
}
