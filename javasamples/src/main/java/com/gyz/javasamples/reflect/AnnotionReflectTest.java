package com.gyz.javasamples.reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by guoyizhe on 16/9/7.
 * 邮箱:gyzboy@126.com
 */
public class AnnotionReflectTest {
    public static void main(String[] args) {
        try {
            // 根据“类名”获取 对应的Class对象
            Class<?> cls = Class.forName("com.gyz.javasamples.reflect.PPerson");

            // 获取“Person类”的注解
            MyAnnotation myann = cls.getAnnotation(MyAnnotation.class);

            System.out.println("myann="+myann);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * MyAnnotation是自定义个一个Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
}

/**
 * MyAnnotation 是Person的注解。
 */
@MyAnnotation
class PPerson {
}