package com.gyz.javasamples.reflect;

import java.lang.reflect.Constructor;

/**
 * Created by guoyizhe on 16/9/6.
 * 邮箱:gyzboy@126.com
 */
public class ClassReflectTest {
    public static void main(String[] args) {

        // getDeclaredConstructor() 的测试函数
        testGetDeclaredConstructor() ;

        // getConstructor() 的测试函数
        testGetConstructor() ;

        // getEnclosingConstructor() 的测试函数
        testGetEnclosingConstructor() ;
    }

    /**
     * getDeclaredConstructor() 的测试函数
     */
    public static void testGetDeclaredConstructor() {
        try {
            // 获取Person类的Class
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.Person");

            // 根据class，获取构造函数
            Constructor cst1 = cls.getDeclaredConstructor();
            Constructor cst2 = cls.getDeclaredConstructor(new Class[]{String.class});
            Constructor cst3 = cls.getDeclaredConstructor(new Class[]{String.class, int.class, Gender.class});

            // 根据构造函数，创建相应的对象
            cst1.setAccessible(true); // 因为Person中Person()是private的，所以这里要设置为可访问
            Object p1 = cst1.newInstance();
            Object p2 = cst2.newInstance("Juce");
            Object p3 = cst3.newInstance("Jody", 34, Gender.MALE);

            System.out.printf("%-30s: p1=%s, p2=%s, p3=%s\n",
                    "getConstructor()", p1, p2, p3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * getConstructor() 的测试函数
     */
    public static void testGetConstructor() {
        try {
            // 获取Person类的Class
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.Person");

            // 根据class，获取构造函数
            //Constructor cst1 = cls.getConstructor(); // 抛出异常，因为默认构造函数是private权限。
            //Constructor cst2 = cls.getConstructor(new Class[]{String.class});// 抛出异常，因为该构造函数是protected权限。
            Constructor cst3 = cls.getConstructor(new Class[]{String.class, int.class, Gender.class});

            // 根据构造函数，创建相应的对象
            //Object p1 = cst1.newInstance();
            //cst1.setAccessible(true); // 因为Person中Person()是private的，所以这里要设置为可访问
            //Object p1 = cst1.newInstance();
            //Object p2 = cst2.newInstance("Kim");
            Object p3 = cst3.newInstance("Katter", 36, Gender.MALE);

            System.out.printf("%-30s: p3=%s\n",
                    "getConstructor()", p3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getEnclosingConstructor() 的测试函数
     */
    public static void testGetEnclosingConstructor() {
        try {
            // 获取Person类的Class
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.Person");

            // 根据class，调用Person类中有内部类InnerA的构造函数
            Constructor cst = cls.getDeclaredConstructor(new Class[]{String.class, int.class});

            // 根据构造函数，创建相应的对象
            Object obj = cst.newInstance("Ammy", 18);

            System.out.printf("%-30s: obj=%s\n",
                    "getEnclosingConstructor()", obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

