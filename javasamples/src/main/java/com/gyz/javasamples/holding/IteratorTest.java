package com.gyz.javasamples.holding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
}
