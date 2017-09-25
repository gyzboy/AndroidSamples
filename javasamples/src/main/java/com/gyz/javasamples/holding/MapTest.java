package com.gyz.javasamples.holding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;

/**
 * Created by guoyizhe on 16/9/1.
 * 邮箱:gyzboy@126.com
 */
public class MapTest {
    public static void main(String[] args) {

        String hashmap = "HashMap实现原理:O(1)\n" +
            "   HashMap基于哈希表的 Map 接口的实现。此实现提供所有可选的Map操作，并允许使用 null 值和 null 键。\n" +
            "   其原理是因为其中有一个 entryForNullKey 的属性，专门来存放 null 相应的 value 值，再进行 put 和 remove 方法的时候，都会对 key 进行判断，若为 "
            + "null，则会只进行 entryForNullKey 相应的更新操作\n"
            +
            "   内部实现为数组链表,hash用的数组为table，比如Map大小为16,存储的key为12,28两个Entry\n" +
            "   则两个都存储在数组下标为12%16=12 28%16=12的位置上。\n" +
            "   在HashMap（jdk1.7）的put方法实现中首先利用了hash()"
            + "生成key的hashCode，然后比较key的hashCode是否已经存在集合，如果不存在，就插入到集合，如果已存在，则返回null\n"
            +
            "   jdk1.8版本对HashMap改动很大，jdk1.7之前的版本，HashMap采用的是链表+位桶的方式，也就是我们经常说的散列表的方式，但是在jdk1.8版本中，HashMap"
            + "采用的是位桶+链表/红黑树的方式，也是非线程安全的。\n"
            +
            "   当某个位桶的链表的长度到达某个阈值的时候，这个链表就转化为红黑树\n" +
            "性能影响因素:\n" +
            "   初始桶大小和变化因子,默认为16，0.75,当到达map大小到达16*0.75=12时,map大小就会翻倍\n" +
            "   加载因子过高虽然减小了空间开销，但是同时也增加了查询成本。\n" +
            "对HashCode碰撞的处理:\n" +
            "   在调用HashMap的put方法或get方法时，都会首先调用hashcode方法，去查找相关的key，当有冲突时，再调用equals方法。hashMap基于hasing原理，我们通过put和get"
            + "方法存取对象。\n"
            +
            "   当我们将键值对传递给put方法时，他调用键对象的hashCode()方法来计算hashCode，然后找到bucket（哈希桶）位置来存储对象。当获取对象时，通过键对象的equals()"
            + "方法找到正确的键值对，然后返回值对象。\n"
            +
            "   HashMap使用链表来解决碰撞问题，当碰撞发生了，对象将会存储在链表的下一个节点中。hashMap在每个链表节点存储键值对对象。当两个不同的键却有相同的hashCode"
            + "时，他们会存储在同一个bucket位置的链表中。\n"
            +
            "分类:\n" +
            "   有序性:TreeMap、LinkedHashMap  无序性:HashMap";
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

        //testWeakHashMap();
        //
        //testTreeMap();
        //
        //testLinkedHashMap();

        testMap();

    }

    private static void testMap() {
        Country india = new Country("India", 1000);
        Country japan = new Country("Japan", 10000);

        Country france = new Country("France", 2000);
        Country russia = new Country("Russia", 20000);

        HashMap<Country, String> countryCapitalMap = new HashMap<Country, String>();
        countryCapitalMap.put(india, "Delhi");
        countryCapitalMap.put(japan, "Tokyo");
        countryCapitalMap.put(france, "Paris");
        countryCapitalMap.put(russia, "Moscow");

        Iterator<Country> countryCapitalIter = countryCapitalMap.keySet().iterator();//put debug point at this line
        while (countryCapitalIter.hasNext()) {
            Country countryObj = countryCapitalIter.next();
            String capital = countryCapitalMap.get(countryObj);
            System.out.println(countryObj.getName() + "----" + capital);
        }
    }

    private static void testWeakHashMap() {
        // WeakHashMap是哈希表，但是它的键是"弱键"。WeakHashMap中保护几个重要的成员变量：table, size, threshold, loadFactor, modCount, queue。
        // table是一个Entry[]数组类型，而Entry实际上就是一个单向链表。哈希表的"key-value键值对"都是存储在Entry数组中的。
        // size是Hashtable的大小，它是Hashtable保存的键值对的数量。
        // threshold是Hashtable的阈值，用于判断是否需要调整Hashtable的容量。threshold的值="容量*加载因子"。
        // loadFactor就是加载因子。
        // modCount是用来实现fail-fast机制的
        // queue保存的是“已被GC清除”的“弱引用的键”

        System.out.println("-----------------WeakHashMap-------------------");
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
        System.out.printf("\nwmap:%s\n", wmap);

        // containsKey(Object key) :是否包含键key
        System.out.printf("contains key two : %s\n", wmap.containsKey("two"));
        System.out.printf("contains key five : %s\n", wmap.containsKey("five"));

        // containsValue(Object value) :是否包含值value
        System.out.printf("contains value 0 : %s\n", wmap.containsValue(new Integer(0)));

        // remove(Object key) ： 删除键key对应的键值对
        wmap.remove("three");

        System.out.printf("wmap: %s\n", wmap);

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
            System.out.printf("next : %s - %s\n", en.getKey(), en.getValue());
        }
        // 打印WeakHashMap的实际大小
        System.out.printf(" after gc WeakHashMap size:%s\n", wmap.size());
    }

    private static void testTreeMap() {
        //        TreeMap实现了SortedMap接口，也就是说会按照key的大小顺序对Map中的元素进行排序，key大小的评判可以通过其本身的自然顺序（natural
        // ordering），也可以通过构造时传入的比较器（Comparator）。
        //        TreeMap底层通过红黑树（Red-Black tree）实现,O(log n)

        //        出于性能原因，TreeMap是非同步的（not synchronized），如果需要在多线程环境使用，需要程序员手动同步；或者通过如下方式将TreeMap包装成（wrapped）同步的：
        //        SortedMap m = Collections.synchronizedSortedMap(new TreeMap(...));

        //        红黑树是一种近似平衡的二叉查找树，它能够确保任何一个节点的左右子树的高度差不会超过二者中较低那个的一陪。具体来说，红黑树是满足如下条件的二叉查找树（binary search tree）：
        //        1.每个节点要么是红色，要么是黑色。
        //        2.根节点必须是黑色
        //        3.红色节点不能连续（也即是，红色节点的孩子和父亲都不能是红色）。
        //        4.对于每个节点，从该点至null（树尾端）的任何路径，都含有相同个数的黑色节点

        System.out.println("-----------------TreeMap-------------------");
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("d", "112");
        treeMap.put("b", "113");
        treeMap.put("e", "114");
        treeMap.put("a", "115");
        System.out.println(treeMap);
    }

    private static void testLinkedHashMap() {
        //        LinkedHashMap是HashMap的直接子类，二者唯一的区别是LinkedHashMap在HashMap的基础上，
        //        采用双向链表（doubly-linked list）的形式将所有entry连接起来，这样是为保证元素的迭代顺序跟插入顺序相同。
        //        LinkedHashMap的结构图，主体部分跟HashMap完全一样，多了header指向双向链表的头部（是一个哑元），该双向链表的迭代顺序就是entry的插入顺序。

        //除了可以保迭代历顺序，这种结构还有一个好处：迭代LinkedHashMap时不需要像HashMap那样遍历整个table，而只需要直接遍历header指向的双向链表即可，也就是说LinkedHashMap
        // 的迭代时间就只跟entry的个数相关，而跟table的大小无关
        //
        // 出于性能原因，LinkedHashMap是非同步的（not synchronized），如果需要在多线程环境使用，需要程序员手动同步；或者通过如下方式将LinkedHashMap包装成（wrapped）同步的：
        //        Map m = Collections.synchronizedMap(new LinkedHashMap(...));

        System.out.println("-----------------LinkedHashMap-------------------");
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("aa", "aaa");
        linkedHashMap.put("ab", "aab");
        linkedHashMap.put("ba", "bba");
        linkedHashMap.put("bc", "bbc");
        System.out.println(linkedHashMap);
    }

    private static void testConcurrentSkipListMap() {
        //ConcurrentSkipListMap是基于跳表实现的，时间复杂度平均能达到O(log n)。
        //1、ConcurrentSkipListMap 的key是有序的。
        //2、ConcurrentSkipListMap 支持更高的并发。ConcurrentSkipListMap
        // 的存取时间是log（N），和线程数几乎无关。也就是说在数据量一定的情况下，并发的线程越多，ConcurrentSkipListMap越能体现出他的优势。

        //什么是跳表:
        //   1.多条链构成，是关键字升序排列的数据结构；
        //        　　2.包含多个级别，一个head引用指向最高的级别，最低（底部）的级别，包含所有的key;
        //        　　3.每一个级别都是其更低级别的子集，并且是有序的；
        //        　　4.如果关键字 key在 级别level=i中出现，则，level<=i的链表中都会包含该关键字key;
    }

/*
    集合类                 Key             Value           Super           说明
    Hashtable           不允许为 null    不允许为 null      Dictionary      线程安全
    ConcurrentHashMap   不允许为 null    不允许为 null      AbstractMap     分段锁技术
    TreeMap             不允许为 null    允许为 null        AbstractMap     线程不安全
    HashMap             允许为 null      允许为 null       AbstractMap      线程不安全
*/
}

class Country {

    String name;
    long population;

    public Country(String name, long population) {
        super();
        this.name = name;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    // If length of name in country object is even then return 31(any random number) and if odd then return 95(any
    // random number).
    // This is not a good practice to generate hashcode as below method but I am doing so to give better and easy
    // understanding of hashmap.
    @Override
    public int hashCode() {
        if (this.name.length() % 2 == 0) { return 31; } else { return 95; }
    }

    @Override
    public boolean equals(Object obj) {

        Country other = (Country)obj;
        if (name.equalsIgnoreCase((other.name))) { return true; }
        return false;
    }
}