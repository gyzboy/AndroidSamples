package com.gyz.javasamples.holding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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


        //Collections.synchronizedMap()对put、get等方法进行了同步,但只是简单的同步,缺点有二:
        //1.一次只能有一个线程访问hash表
        //2.许多公用的混合操作仍然需要额外的同步,是有条件的同步,所有 单个的操作都是线程安全的，但是多个操作组成的操作序列却可能导致数据争用，因为在操作序列中控制流取决于前面操作的结果。例子见testQ1


        //ConcurrentHashMap针对桶进行加锁,性能更好,一个ConcurrentHashMap由多个segment组成，每一个segment都包含了一个HashEntry数组的hashtable，
        // 每一个segment包含了对自己的hashtable的操作，比如get，put，replace等操作，这些操作发生的时候，对自己的hashtable进行锁定。
        //它把区间按照并发级别(concurrentLevel)，分成了若干个segment。默认情况下内部按并发级别为16来创建。对于每个segment的容量，默认情况也是16。
        // 当然并发级别(concurrentLevel)和每个段(segment)的初始容量都是可以通过构造函数设定的。


    }

    static void output(List<String> ss) {
        for (String s : ss) {
            System.out.println(s);
        }
    }


    Object obj = new Object();
    public void testQ1(){
        Map m = Collections.synchronizedMap(new HashMap<>());
        synchronized (obj){
            if (!m.containsKey("1")) {//这样可能在线程1中containskey判断的时候,线程2也对它进行了访问,这样就造成了put了两次,可以使用synchronize将这块代码包裹起来
                m.put("1","2");
            }
        }

        List l = Collections.synchronizedList(new ArrayList<>());
        l.remove("1");//在多进程情况下,线程1调用get方法,同时线程2在调用了remove方法导致key为1的值被删除,这样线程1调用get方法时就会产生np异常,解决方法是将整个list加上同步锁,但效率会下降

    }
}
