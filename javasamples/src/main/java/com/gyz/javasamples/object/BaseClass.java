package com.gyz.javasamples.object;

/**
 * Created by guoyizhe on 16/8/29.
 * 邮箱:gyzboy@126.com
 */
public class BaseClass {

    public String name = "base";

    public String dynamicGet() {
        return "Base dynamicGet()";
    }
    public static String staticGet() {
        return "Base staticGet()";
    }

    //注意这里访问修饰符是private,所以子类再定义f方法的时候不是覆盖方法而是new了一个新的方法
    private void f() {
        System.out.println(name);
    }

    public static void main(String[] args) {
        BaseClass m = new SubClass();
        // 构造中为callName输出null，因为多态无法覆盖域,即使它为public，调用顺序:父类构造->调用子类callName->子类无法重载name,name初始值为NULL
        m.f();//这里打印的是base,因为多态无法覆盖私有方法
        System.out.println(m.staticGet());//静态域无法多态
        System.out.println(m.dynamicGet());
    }
}
