package com.wr.common.core.business.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 10:00:12
 * @Desc: 用于标识字段需敏感词过滤
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SensitiveWord {
}
