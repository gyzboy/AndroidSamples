package com.gyz.javasamples.holding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guoyizhe on 16/9/6.
 * 邮箱:gyzboy@126.com
 */
public class FailFastTest {
    //Fail-fast 机制是java集合(Collection)中的一种错误机制。当多个线程对同一个集合的内容进行操作时，就可能会产生fail-fast事件。
    //例如：当某一个线程A通过iterator去遍历某集合的过程中，若该集合的内容被其他线程所改变了；
    //那么线程A访问集合时，就会抛出ConcurrentModificationException异常，产生fail-fast事件。


    private static List<String> list = new ArrayList<String>();

    //private static List<String> list = new CopyOnWriteArrayList<String>();
    public static void main(String[] args) {

        // 同时启动两个线程对list进行操作！
        new ThreadOne().start();
        new ThreadTwo().start();
    }

    private static void printAll() {
        System.out.println("");

        String value = null;
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            value = (String) iter.next();
            System.out.print(value + ", ");
        }
    }

    /**
     * 向list中依次添加0,1,2,3,4,5，每添加一个数之后，就通过printAll()遍历整个list
     */
    private static class ThreadOne extends Thread {
        public void run() {
            int i = 0;
            while (i < 6) {
                list.add(String.valueOf(i));
                printAll();
                i++;
            }
        }
    }

    /**
     * 向list中依次添加10,11,12,13,14,15，每添加一个数之后，就通过printAll()遍历整个list
     */
    private static class ThreadTwo extends Thread {
        public void run() {
            int i = 10;
            while (i < 16) {
                list.add(String.valueOf(i));
                printAll();
                i++;
            }
        }
    }
}
