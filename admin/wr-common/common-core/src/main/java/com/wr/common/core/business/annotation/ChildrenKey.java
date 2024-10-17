package com.wr.common.core.business.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 15:13:39
 * @Desc: 用于标识字段为 子集
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ChildrenKey {

    /** 字段数据类型默认为 List */
    //DataType dataType() default DataType.LIST;
}
