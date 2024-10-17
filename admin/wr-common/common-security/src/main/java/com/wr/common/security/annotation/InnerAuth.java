package com.wr.common.security.annotation;

import com.wr.common.core.constant.UserConstants;

import java.lang.annotation.*;

/**
 * 内部认证注解
 * 
 * @author wr
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerAuth
{
    /**
     * 是否校验用户信息
     */
    boolean isUser() default false;

    /** 用户类型 */
    String userType() default UserConstants.USER_SYS;
}