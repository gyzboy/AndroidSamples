package com.gyz.javasamples.reflect;

import java.lang.reflect.Method;

/**
 * Created by guoyizhe on 16/9/6.
 * 邮箱:gyzboy@126.com
 */
public class MethodReflectTest {
    public static void main(String[] args) {

//        // getDeclaredMethod() 的测试函数  获取所有方法,包括protected\private,需要访问权限设置,子类继承中如果没有重写则get失败
//        testGetDeclaredMethod() ;
//
//        // getMethod() 的测试函数,获取所有public方法
//        testGetMethod() ;
//
//        // getEnclosingMethod() 的测试函数
//        testGetEnclosingMethod() ;

        testSubInDMethod();
    }

    public static void testSubInDMethod() {
        try {
            // 获取Person类的Class
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.SubPerson");
            // 根据class，调用类的默认构造函数(不带参数)
            Object person = cls.newInstance();

            // 获取Person中的方法
            Method mSetName = cls.getDeclaredMethod("setName", new Class[]{String.class});
            Method mGetName = cls.getDeclaredMethod("getName", new Class[]{});
            //无法获得方法,因为子类继承了但未重写
//            Method mSetAge  = cls.getDeclaredMethod("setAge", new Class[]{int.class});
//            Method mGetAge  = cls.getDeclaredMethod("getAge", new Class[]{});
//            Method mSetGender = cls.getDeclaredMethod("setGender", new Class[]{Gender.class});
//            Method mGetGender = cls.getDeclaredMethod("getGender", new Class[]{});


            // 调用获取的方法
            mSetName.invoke(person, new Object[]{"Jimmy"});
//            mSetAge.invoke(person, new Object[]{30});
//            mSetGender.setAccessible(true);    // 因为Person中setGender()是private的，所以这里要设置为可访问
//            mSetGender.invoke(person, new Object[]{Gender.MALE});
            String name = (String)mGetName.invoke(person, new Object[]{});
//            Integer age = (Integer)mGetAge.invoke(person, new Object[]{});
//            mGetGender.setAccessible(true);    // 因为Person中getGender()是private的，所以这里要设置为可访问
//            Gender gender = (Gender)mGetGender.invoke(person, new Object[]{});

            // 打印输出
            System.out.printf("%-30s: person=%s, name=%s, age=%s, gender=%s\n",
                    "getDeclaredMethod()", person, name, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getDeclaredMethod() 的测试函数
     */
    public static void testGetDeclaredMethod() {
        try {
            // 获取Person类的Class
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.Person");
            // 根据class，调用类的默认构造函数(不带参数)
            Object person = cls.newInstance();

            // 获取Person中的方法
            Method mSetName = cls.getDeclaredMethod("setName", new Class[]{String.class});
            Method mGetName = cls.getDeclaredMethod("getName", new Class[]{});
            Method mSetAge  = cls.getDeclaredMethod("setAge", new Class[]{int.class});
            Method mGetAge  = cls.getDeclaredMethod("getAge", new Class[]{});
            Method mSetGender = cls.getDeclaredMethod("setGender", new Class[]{Gender.class});
            Method mGetGender = cls.getDeclaredMethod("getGender", new Class[]{});

            // 调用获取的方法
            mSetName.invoke(person, new Object[]{"Jimmy"});
            mSetAge.invoke(person, new Object[]{30});
            mSetGender.setAccessible(true);    // 因为Person中setGender()是private的，所以这里要设置为可访问
            mSetGender.invoke(person, new Object[]{Gender.MALE});
            String name = (String)mGetName.invoke(person, new Object[]{});
            Integer age = (Integer)mGetAge.invoke(person, new Object[]{});
            mGetGender.setAccessible(true);    // 因为Person中getGender()是private的，所以这里要设置为可访问
            Gender gender = (Gender)mGetGender.invoke(person, new Object[]{});

            // 打印输出
            System.out.printf("%-30s: person=%s, name=%s, age=%s, gender=%s\n",
                    "getDeclaredMethod()", person, name, age, gender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * getMethod() 的测试函数
     */
    public static void testGetMethod() {
        try {
            // 获取Person类的Class
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.Person");
            // 根据class，调用类的默认构造函数(不带参数)
            Object person = cls.newInstance();

            // 获取Person中的方法
            Method mSetName = cls.getMethod("setName", new Class[]{String.class});
            Method mGetName = cls.getMethod("getName", new Class[]{});
            //Method mSetAge  = cls.getMethod("setAge", new Class[]{int.class});         // 抛出异常，因为setAge()是protected权限。
            //Method mGetAge  = cls.getMethod("getAge", new Class[]{});                  // 抛出异常，因为getAge()是protected权限。
            //Method mSetGender = cls.getMethod("setGender", new Class[]{Gender.class}); // 抛出异常，因为setGender()是private权限。
            //Method mGetGender = cls.getMethod("getGender", new Class[]{});             // 抛出异常，因为getGender()是private权限。

            // 调用获取的方法
            mSetName.invoke(person, new Object[]{"Phobe"});
            //mSetAge.invoke(person, new Object[]{38});
            //mSetGender.invoke(person, new Object[]{Gender.FEMALE});
            String name = (String)mGetName.invoke(person, new Object[]{});
            //Integer age = (Integer)mGetAge.invoke(person, new Object[]{});
            //Gender gender = (Gender)mGetGender.invoke(person, new Object[]{});

            // 打印输出
            System.out.printf("%-30s: person=%s\n",
                    "getMethod()", person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * getEnclosingMethod() 的测试函数
     */
    public static void testGetEnclosingMethod() {
        try {
            // 获取Person类的Class
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.Person");
            // 根据class，调用类的默认构造函数(不带参数)
            Object person = cls.newInstance();

            // 根据class，调用Person类中有内部类InnerB的函数
            Method mGetInner = cls.getDeclaredMethod("getInner", new Class[]{});

            // 根据构造函数，创建相应的对象
            mGetInner.invoke(person, new Object[]{});

            System.out.printf("%-30s: person=%s\n",
                    "getEnclosingMethod", person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
