package com.wr.common.customize.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 09:58:18
 * @Desc: 注解工具类
 */
public class AnnotationUtil extends BaseUtils {

    /**
     * 获取注解 annotation 标识的字段值
     * @param t entity
     * @param annotation 注解
     * @return java.lang.Object
     */
    public static <T> Object getFieldValue(T t, Class<? extends Annotation> annotation) throws IllegalAccessException {
        Object fieldValue = null;
        Class<?> clazz = t.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if(field.isAnnotationPresent(annotation)){
                field.setAccessible(true);
                fieldValue = field.get(t);
                break;
            }
        }
        return fieldValue;
    }
}
