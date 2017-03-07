package com.gyz.javasamples.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by guoyizhe on 2017/3/7.
 * 邮箱:gyzboy@126.com
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitName{
    String value() default ""; //只有一个属性的时候直接用value
}
