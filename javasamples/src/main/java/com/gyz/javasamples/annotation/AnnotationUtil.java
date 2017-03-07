package com.gyz.javasamples.annotation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by guoyizhe on 2017/3/7.
 * 邮箱:gyzboy@126.com
 */

public class AnnotationUtil{
    public static void getFruitInfo(Class<?> clazz){

//        getAnnotation(AnnotationName.class) 表示得到该 Target 某个 Annotation 的信息，因为一个 Target 可以被多个 Annotation 修饰
//        getAnnotations() 则表示得到该 Target 所有 Annotation
//        isAnnotationPresent(AnnotationName.class) 表示该 Target 是否被某个 Annotation 修饰

        String strFruitName=" 水果名称：";
        String strFruitColor=" 水果颜色：";

        Field[] fields = clazz.getDeclaredFields();

        for(Field field :fields){
            if(field.isAnnotationPresent(FruitName.class)){
                FruitName fruitName = (FruitName) field.getAnnotation(FruitName.class);
                strFruitName=strFruitName+fruitName.value();
                System.out.println(strFruitName);
            }
            else if(field.isAnnotationPresent(FruitColor.class)){
                FruitColor fruitColor= (FruitColor) field.getAnnotation(FruitColor.class);
                strFruitColor=strFruitColor+fruitColor.fruitColor().toString();
                System.out.println(strFruitColor);
            }
        }
    }
}
