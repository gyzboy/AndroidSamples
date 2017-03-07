package com.gyz.javasamples.annotation;

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

@SupportedAnnotationTypes({"com.gyz.javasamples.annotation.FruitProvider"})
public class AnnotationClassUtil  extends AbstractProcessor {//指定为@Retention为Class的Annotation,由apt解析自动解析

//    SupportedAnnotationTypes 表示这个 Processor 要处理的 Annotation 名字。
//    process 函数中参数 annotations 表示待处理的 Annotations，参数 env 表示当前或是之前的运行环境
//    process 函数返回值表示这组 annotations 是否被这个 Processor 接受，如果接受后续子的 rocessor 不会再对这个 Annotations 进行处理

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
        return true;
    }
}
