package com.wr.common.customize.util;

import com.wr.common.core.business.annotation.SensitiveWord;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.reflect.ReflectUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 09:53:05
 * @Desc: 敏感词过滤
 */
public class SensitiveUtil extends BaseUtils{

    /**
     * 对数据内容进行敏感词过滤
     * @param t 需要进行敏感词过滤的类
     * @param sensitiveList 敏感词集
     * @return T
     */
    public static <T> T sensitiveFilter(T t, List<String> sensitiveList) {
        try {
            Class<?> clazz = t.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                if(field.isAnnotationPresent(SensitiveWord.class)){
                    field.setAccessible(true);
                    String sensitive = (String) field.get(t);
                    if(StringUtils.isNotEmpty( sensitive )){
                        for (String word : sensitiveList) {
                            sensitive = sensitive.replace(word,"**");
                            ReflectUtils.invokeSetter(t, field.getName(), sensitive);
                        }
                    }
                }
            }
            return t;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error("敏感词转换失败：{}",e.getMessage());
            return t;
        }
    }

    public static <T> T sensitiveFilterOwn(T t, List<String> sensitiveList) {
        try {
            Class<?> clazz = t.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.isAnnotationPresent(SensitiveWord.class)) {
                    field.setAccessible(true);
                    String sensitive = (String) field.get(t);
                    if (StringUtils.isNotEmpty(sensitive)) {
                        String filteredSensitive = sensitive;
                        for (String word : sensitiveList) {
                            filteredSensitive = filteredSensitive.replace(word, "**");
                        }
                        ReflectUtils.invokeSetter(t, field.getName(), filteredSensitive);
                    }
                }
            }
            return t;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error("敏感词转换失败：{}", e.getMessage());
            return t;
        }
    }

}
