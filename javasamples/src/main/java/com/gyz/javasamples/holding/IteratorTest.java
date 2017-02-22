package com.gyz.javasamples.holding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by guoyizhe on 16/9/1.
 * 邮箱:gyzboy@126.com
 */
public class IteratorTest {
    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");

        Iterator<String> it = list.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
        it.remove();//移除当前最后一个元素,所以必须先调用next
        System.out.println("---------------");
        while (it.hasNext()){//只能单向移动,所以这里输出不了什么东西
            System.out.println(it.next());
        }

        ListIterator<String> lit = list.listIterator();
        while (lit.hasNext()){
            System.out.println("next : " + lit.next());
            lit.set("new");
        }
        lit.remove();
        while (lit.hasPrevious()){
            System.out.println("previous :" + lit.previous());
        }
    }

    public void testRemove(){
        Map<String,String> bb = new HashMap<>();
        bb.put("1","11");
        bb.put("2","22");
        Iterator it = bb.keySet().iterator();
        while (it.hasNext()){
            Object e = it.next();
            bb.remove(e);//这里可能引发ConcurrentModification异常,因为iterator在做遍历的时候,Iterator会检查HashMap的size,size发生变化,就会抛出错误
            //解决方法:
            //1.通过Iterator修改HashTable, it.remove();
            //2.手动给HashMap加锁
            //3.使用ConcurrentHashMap代替HashMap,或者Collections.synchronizedMap
        }
    }
}
