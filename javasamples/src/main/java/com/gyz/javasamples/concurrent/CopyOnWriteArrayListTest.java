package com.gyz.javasamples.concurrent;

/**
 * Created by guoyizhe on 2017/3/16.
 * 邮箱:gyzboy@126.com
 */

public class CopyOnWriteArrayListTest {
// CopyOnWrite容器即写时复制的容器。通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，
// 而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，
// 再将原容器的引用指向新的容器。这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，
// 因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
    //实现原理就是在写入的时候加锁,读取的时候不加锁

    //CopyOnWrite并发容器用于读多写少的并发场景。

    //缺点是 内存性能上因为需要复制所以内存占用空间比较大, 同时 不能保证读取数据的实时性
}
