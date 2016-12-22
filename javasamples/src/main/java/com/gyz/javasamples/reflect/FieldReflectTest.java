package com.gyz.javasamples.reflect;

import java.lang.reflect.Field;

/**
 * Created by guoyizhe on 16/9/6.
 * 邮箱:gyzboy@126.com
 */
public class FieldReflectTest {
    public static void main(String[] args) {
        // getDeclaredField() 的测试函数
        testGetDeclaredField();

        // getField() 的测试函数
        testGetField();
    }

    /**
     * getDeclaredField() 的测试函数
     * getDeclaredField() 用于获取的是类自身声明的所有成员遍历，包含public、protected和private方法。
     */
    public static void testGetDeclaredField() {
        try {
            // 获取Person类的Class
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.Person");
            // 根据class，调用类的默认构造函数(不带参数)
            Object person = cls.newInstance();

            // 根据class，获取Filed
            Field fName = cls.getDeclaredField("name");
            Field fAge = cls.getDeclaredField("age");
            Field fGender = cls.getDeclaredField("gender");

            // 根据构造函数，创建相应的对象
            fName.set(person, "Hamier");
            fAge.set(person, 31);
            fGender.setAccessible(true);  // 因为"flag"是private权限，所以要设置访问权限为true；否则，会抛出异常。
            fGender.set(person, Gender.FEMALE);

            System.out.printf("%-30s: person=%s\n",
                    "getDeclaredField()", person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getField() 的测试函数
     * getField() 用于获取的是public的“成员”
     */
    public static void testGetField() {
        try {
            // 获取Person类的Class
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.Person");
            // 根据class，调用类的默认构造函数(不带参数)
            Object person = cls.newInstance();

            // 根据class，获取Filed
            Field fName = cls.getField("name");
            Field fAge = cls.getDeclaredField("age");       // 抛出异常，因为Person中age是protected权限。
            Field fGender = cls.getDeclaredField("gender"); // 抛出异常，因为Person中gender是private权限。

            // 根据构造函数，创建相应的对象
            fName.set(person, "Grace");
            //fAge.set(person, 26);
            //fGender.set(person, Gender.FEMALE);

            System.out.printf("%-30s: person=%s\n",
                    "getField()", person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
