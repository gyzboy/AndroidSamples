package com.gyz.javasamples.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by guoyizhe on 16/9/6.
 * 邮箱:gyzboy@126.com
 */
public class Person {
    private Gender gender;  // 性别

    protected int age;        // 年龄
    protected int getAge() {
        return age;
    }

    protected void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;    // 姓名

    public Person() {
        this.name = "unknown";
        this.age = 0;
        this.gender = Gender.FEMALE;
        System.out.println("call--\"private Person()\"");
    }

    protected Person(String name) {
        this.name = name;
        this.age = 0;
        this.gender = Gender.FEMALE;
        System.out.println("call--\"protected Person(String name)\"");
    }

    public Person(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        System.out.println("call--\"public Person(String name, int age, Gender gender)\"");
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.gender = Gender.FEMALE;
        //内部类在构造方法中
        class InnerA {
        }
        // 获取InnerA的Class对象
        Class cls = InnerA.class;

        // 获取“封闭该内部类(InnerA)”的构造方法
        Constructor cst = cls.getEnclosingConstructor();

        System.out.println("call--\"public Person(String name, int age)\" cst=" + cst);
    }

    // getInner() 中有内部类InnerB，用来测试getEnclosingMethod()
    public void getInner() {
        // 内部类
        class InnerB{
        }
        // 获取InnerB的Class对象
        Class cls = InnerB.class;

        // 获取“封闭该内部类(InnerB)”的构造方法
        Method cst = cls.getEnclosingMethod();

        System.out.println("call--\"getInner()\" cst="+cst);
    }
    @Override
    public String toString() {
        return "(" + name + ", " + age + ", " + gender + ")";
    }
}