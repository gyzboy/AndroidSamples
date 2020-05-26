package com.gyz.javasamples.object;

/**
 * Created by guoyizhe on 16/8/29.
 * 邮箱:gyzboy@126.com
 */
public class SubClass extends BaseClass {

    public void f() {
        System.out.println(name);
    }
    public String dynamicGet() {
        return "SubClass dynamicGet()";
    }

    public static String staticGet() {
        return "SubClass staticGet()";
    }

    public static void main(String[] args) {
        SubClass s = new SubClass();
        s.f();
    }
}
