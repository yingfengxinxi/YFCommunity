package com.wr.common.log.annotation;

import com.wr.common.log.enums.BusinessType;
import com.wr.common.log.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 11:06:06
 * @Desc: api 自定义操作日志记录注解
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {
    /**
     * 模块 
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    public OperatorType operatorType() default OperatorType.MOBILE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;
}
