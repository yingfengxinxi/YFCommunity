package com.wr.common.core.business.annotation;

import com.wr.common.core.business.enums.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 15:25:41
 * @Desc: 用于标识字段为 父id
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ParentKey {
    
    /** 字段数据类型默认为 long */
    DataType dataType() default DataType.LONG;
}
