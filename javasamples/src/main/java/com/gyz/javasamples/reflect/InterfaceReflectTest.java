package com.gyz.javasamples.reflect;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */
public class InterfaceReflectTest {

    public static void main(String[] args) {
        try {
            // 根据“类名”获取 对应的Class对象
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.IPerson");

            // 获取“Person”的父类
            Type father = cls.getGenericSuperclass();
            // 获取“Person”实现的全部接口
            Type[] intfs = cls.getGenericInterfaces();

            System.out.println("father=" + father);
            for (Type t : intfs)
                System.out.println("t=" + t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * Person 继承于 Object，并且实现了Serializable和Runnable接口
 */
class IPerson extends Object implements Serializable, Runnable {

    @Override
    public void run() {
    }

}