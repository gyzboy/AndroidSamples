package com.gyz.javasamples.reflect;

/**
 * Created by guoyizhe on 16/9/6.
 * 邮箱:gyzboy@126.com
 */
public class ReflectTest {
    //Java 反射机制。通俗来讲呢，就是在运行状态中，我们可以根据“类的部分已经的信息”来还原“类的全部的信息”

    public static void main(String[] args){
        try {
            // 方法1：Class.forName("类名字符串")  （注意：类名字符串必须是全称，包名+类名）
            //Class cls1 = Class.forName("com.gyz.javasamples.reflect.Person");
            Class<?> cls1 = Class.forName("com.gyz.javasamples.reflect.Person");
            //Class<Person> cls1 = Class.forName("com.gyz.javasamples.reflect.Person");

            // 方法2：类名.class
            Class cls2 = Person.class;

            // 方法3：实例对象.getClass()
            Person person = new Person();
            Class cls3 = person.getClass();

            // 方法4："类名字符串".getClass()
            String str = "com.gyz.javasamples.reflect.Person";
            Class cls4 = str.getClass();

            System.out.printf("cls1=%s, cls2=%s, cls3=%s, cls4=%s\n", cls1, cls2, cls3, cls4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
