package com.gyz.javasamples.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by guoyizhe on 2017/3/6.
 * 邮箱:gyzboy@126.com
 */
@SupportedAnnotationTypes({"com.gyz.javasamples.annotation.FruitProvider"})
public class AnnotationTest extends AbstractProcessor{
    //Annotation分类:
    //1.标准Annotation,override、deprecated、suppressWarnings是java自带的annotation
    //2.元Annotation,用来定义annotation的annotation
    //3.自定义annotation

    //作用:标记(告诉编译器一些信息)、编译时动态处理、运行时动态处理

    //元annotation:
//    @Documented 是否会保存到 Javadoc 文档中
//    @Retention 保留时间，可选值 SOURCE（源码时可以得到），CLASS（编译时），RUNTIME（运行时可被反射得到），默认为 CLASS，SOURCE 大都为 Mark Annotation，这类 Annotation 大都用来校验，比如 Override, SuppressWarnings
//    @Target 可以用来修饰哪些程序元素，如 TYPE, METHOD, CONSTRUCTOR, FIELD, PARAMETER 等，未标注则表示可修饰所有
//           1.CONSTRUCTOR:用于描述构造器
//    　　　　2.FIELD:用于描述域
//    　　　　3.LOCAL_VARIABLE:用于描述局部变量
//    　　　　4.METHOD:用于描述方法
//    　　　　5.PACKAGE:用于描述包
//    　　　　6.PARAMETER:用于描述参数
//    　　　　7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
//    @Inherited 是否可以被继承，默认为 false

    @FruitName("apple")
    private String appName;

    @FruitColor(fruitColor = FruitColor.Color.GREEN)
    private String appleColor;

    public static void main(String[] args) {
        AnnotationUtil.getFruitInfo(AnnotationTest.class);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        String providerMan = "供应商信息:";

        for (TypeElement typeElement : set) {
            for (Element element : roundEnvironment.getElementsAnnotatedWith(typeElement)) {
                FruitProvider provider = element.getAnnotation(FruitProvider.class);
                providerMan = providerMan + "id = " + provider.id() + " name = " + provider.name() + " address = " + provider.address();
                System.out.println(providerMan);
            }
        }
        return false;
    }
}



