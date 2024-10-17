package com.wr.common.security.business.annotation;

import com.wr.common.security.business.validator.ApiFieldRepeatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: RainCity
 * @Date: 2022-08-22 16:08:02
 * @Desc:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ApiFieldRepeatValidator.class)
public @interface ApiFieldRepeat {
    String message() default "已存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String tableName() default "";

    String idField() default "";

    String delField() default "";

    String[] fields() default {};

    String[] msg() default {};

}
