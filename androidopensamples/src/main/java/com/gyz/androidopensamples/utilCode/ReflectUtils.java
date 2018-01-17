package com.gyz.androidopensamples.utilCode;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by guoyizhe on 2017/12/28.
 */

public class ReflectUtils {

    /**
     * 反射获得类中静态变量string
     * @param mClass
     * @param value
     * @return
     * @throws IllegalAccessException
     */
    public static String getVariableNameByValue(Class mClass, Object value) throws IllegalAccessException {
        final int PATTERN_FINAL_STATIC = Modifier.FINAL | Modifier.STATIC;
        for (Field field : mClass.getDeclaredFields()) {
            if ((~field.getModifiers() & PATTERN_FINAL_STATIC) == 0) {
                if (field.get(mClass).equals(value)) {
                    return field.getName();
                }
            }
        }
        return null;
    }
}
