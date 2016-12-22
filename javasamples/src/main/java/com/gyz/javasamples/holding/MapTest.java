package com.gyz.javasamples.holding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Created by guoyizhe on 16/9/1.
 * 邮箱:gyzboy@126.com
 */
public class MapTest {
    public static void main(String[] args) {

        String hashmap = "HashMap实现原理:\n" +
                "   HashMap基于哈希表的 Map 接口的实现。此实现提供所有可选的Map操作，并允许使用 null 值和 null 键。\n" +
                "   其原理是因为其中有一个 entryForNullKey 的属性，专门来存放 null 相应的 value 值，再进行 put 和 remove 方法的时候，都会对 key 进行判断，若为 null，则会只进行 entryForNullKey 相应的更新操作\n" +
                "   内部实现为数组链表，比如Map大小为16,存储的key为12,28两个Entry\n" +
                "   则两个都存储在数组下标为12%16=12 28%16=12的位置上。\n" +
                "   在HashMap（jdk1.7）的put方法实现中首先利用了hash()生成key的hashCode，然后比较key的hashCode是否已经存在集合，如果不存在，就插入到集合，如果已存在，则返回null\n" +
                "   jdk1.8版本对HashMap改动很大，jdk1.7之前的版本，HashMap采用的是链表+位桶的方式，也就是我们经常说的散列表的方式，但是在jdk1.8版本中，HashMap采用的是位桶+链表/红黑树的方式，也是非线程安全的。\n" +
                "   当某个位桶的链表的长度到达某个阈值的时候，这个链表就转化为红黑树\n" +
                "性能影响因素:\n" +
                "   初始桶大小和变化因子,默认为16，0.75,当到达map大小到达16*0.75=12时,map大小就会翻倍\n" +
                "   加载因子过高虽然减小了空间开销，但是同时也增加了查询成本。\n" +
                "对HashCode碰撞的处理:\n" +
                "   在调用HashMap的put方法或get方法时，都会首先调用hashcode方法，去查找相关的key，当有冲突时，再调用equals方法。hashMap基于hasing原理，我们通过put和get方法存取对象。\n" +
                "   当我们将键值对传递给put方法时，他调用键对象的hashCode()方法来计算hashCode，然后找到bucket（哈希桶）位置来存储对象。当获取对象时，通过键对象的equals()方法找到正确的键值对，然后返回值对象。\n" +
                "   HashMap使用链表来解决碰撞问题，当碰撞发生了，对象将会存储在链表的下一个节点中。hashMap在每个链表节点存储键值对对象。当两个不同的键却有相同的hashCode时，他们会存储在同一个bucket位置的链表中。";

        System.out.println(hashmap);

        Map<String, String> map = new HashMap<>();
        map.put("aaa", "aaa");
        map.put("aaa", "bbb");

        System.out.println(map.get("aaa"));//有的话就返回value值
        System.out.println(map.get("bbb"));//没有的话就返回null
        //遍历方式:

        //1.forEach遍历entries,同时需要key和value时使用
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }


        //2.forEach遍历key或者value
        for (String key : map.keySet()) {
            System.out.println("Key = " + key);
        }
        for (String value : map.values()) {
            System.out.println("Value = " + value);
        }


        //3.用迭代器遍历
        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }


        //4.遍历key查找value 效率差
        for (String key : map.keySet()) {
            String value = map.get(key);
            System.out.println("Key = " + key + ", Value = " + value);
        }
        for (String value : map.values()) {
            System.out.println("Value = " + value);
        }


//        WeakHashMap是哈希表，但是它的键是"弱键"。WeakHashMap中保护几个重要的成员变量：table, size, threshold, loadFactor, modCount, queue。
//        　　table是一个Entry[]数组类型，而Entry实际上就是一个单向链表。哈希表的"key-value键值对"都是存储在Entry数组中的。
//        　　size是Hashtable的大小，它是Hashtable保存的键值对的数量。
//        　　threshold是Hashtable的阈值，用于判断是否需要调整Hashtable的容量。threshold的值="容量*加载因子"。
//        　　loadFactor就是加载因子。
//        　　modCount是用来实现fail-fast机制的
//        　　queue保存的是“已被GC清除”的“弱引用的键”

        testWeakHashMap();

    }


    private static void testWeakHashMap() {
        // 初始化3个“弱键”
        String w1 = new String("one");
        String w2 = new String("two");
        String w3 = new String("three");
        // 新建WeakHashMap
        Map wmap = new WeakHashMap();

        // 添加键值对
        wmap.put(w1, "w1");
        wmap.put(w2, "w2");
        wmap.put(w3, "w3");

        // 打印出wmap
        System.out.printf("\nwmap:%s\n",wmap );

        // containsKey(Object key) :是否包含键key
        System.out.printf("contains key two : %s\n",wmap.containsKey("two"));
        System.out.printf("contains key five : %s\n",wmap.containsKey("five"));

        // containsValue(Object value) :是否包含值value
        System.out.printf("contains value 0 : %s\n",wmap.containsValue(new Integer(0)));

        // remove(Object key) ： 删除键key对应的键值对
        wmap.remove("three");

        System.out.printf("wmap: %s\n",wmap );



        // ---- 测试 WeakHashMap 的自动回收特性 ----

        // 将w1设置null。
        // 这意味着“弱键”w1再没有被其它对象引用，调用gc时会回收WeakHashMap中与“w1”对应的键值对
        w1 = null;
        // 内存回收。这里，会回收WeakHashMap中与“w1”对应的键值对
        System.gc();

        // 遍历WeakHashMap
        Iterator iter = wmap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry en = (Map.Entry)iter.next();
            System.out.printf("next : %s - %s\n",en.getKey(),en.getValue());
        }
        // 打印WeakHashMap的实际大小
        System.out.printf(" after gc WeakHashMap size:%s\n", wmap.size());
    }
}
