package com.wr.common.core.business.annotation;

import java.lang.annotation.*;

/**
 * @Author: RainCity
 * @Date: 2022-08-29 08:49:23
 * @Desc: 用于方法，参数加密注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptResponse {

}
