package com.wr.common.core.business.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: RainCity
 * @Date: 2021-05-24 10:25:39
 * @Desc: 在需要验证的方法上使用此注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {
    boolean required() default true;
}
