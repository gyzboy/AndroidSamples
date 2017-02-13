package com.gyz.javasamples.holding;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by guoyizhe on 2017/2/13.
 * 邮箱:gyzboy@126.com
 */

public class QueueTest {
    public static void main(String[] args) {
        testPriorityQueue();
    }

    public static void testPriorityQueue() {

// 优先队列的作用是能保证每次取出的元素都是队列中权值最小的（Java的优先队列每次取最小元素，C++的优先队列每次取最大元素）。
// 这里牵涉到了大小关系，元素大小的评判可以通过元素本身的自然顺序（natural ordering），也可以通过构造时传入的比较器（Comparator，类似于C++的仿函数）。
// Java中PriorityQueue实现了Queue接口，不允许放入null元素；其通过堆实现，具体说是通过完全二叉树（complete binary tree）实现的小顶堆（任意一个非叶子节点的权值，都不大于其左右子节点的权值），
// 也就意味着可以通过数组来作为PriorityQueue的底层实现。

        System.out.println("--------------PriorityQueue---------------");

        Comparator<Test> OrderIsdn = new Comparator<Test>() {
            public int compare(Test o1, Test o2) {
                int numbera = o1.getPopulation();
                int numberb = o2.getPopulation();
                if (numberb > numbera) {
                    return 1;
                } else if (numberb < numbera) {
                    return -1;
                } else {
                    return 0;
                }

            }
        };
        Queue<Test> priorityQueue = new PriorityQueue<Test>(11, OrderIsdn);//默认数组大小就是11

        Test t1 = new Test("t1", 1);
        Test t3 = new Test("t3", 3);
        Test t2 = new Test("t2", 2);
        Test t4 = new Test("t4", 0);
        priorityQueue.add(t1);
        priorityQueue.add(t3);
        priorityQueue.add(t2);
        priorityQueue.add(t4);
        System.out.println(priorityQueue.poll().toString());
    }
}

class Test {
    private String name;
    private int population;

    public Test(String name, int population) {
        this.name = name;
        this.population = population;
    }

    public String getName() {
        return this.name;
    }

    public int getPopulation() {
        return this.population;
    }

    public String toString() {
        return getName() + " - " + getPopulation();
    }
}
