package com.gyz.androidsamples.sqlite;

/**
 * Created by guoyizhe on 2017/3/1.
 * 邮箱:gyzboy@126.com
 */

public class Person {
    public int _id;
    public String name;
    public int age;
    public String info;

    public Person() {
    }

    public Person(String name, int age, String info) {
        this.name = name;
        this.age = age;
        this.info = info;
    }
}
