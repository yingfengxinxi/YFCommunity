package com.wr.common.customize.util;

import com.wr.common.customize.function.ExceptionHandle;
import com.wr.common.customize.function.NullHandle;
import com.wr.common.customize.function.TrueHandle;

/**
 * function工具
 *
 * @Author RainCity
 * @Date 2023-04-24 16:46:19
 */
public class FunctionUtil {


    /**
     * 参数为null时，分别进行不同的操作
     * @param obj 参数
     * @return {@link NullHandle}
     * @ver v1.0.0
     */
    public static NullHandle isNull(Object obj){

        return (nullHandle, notNullHandle) -> {
            if (obj == null){
                nullHandle.run();
            } else {
                notNullHandle.run();
            }
        };
    }

    /**
     * 参数为true或false时，分别进行不同的操作
     * @param condition 判断条件
     * @return {@link NullHandle}
     * @ver v1.0.0
     */
    public static TrueHandle isTure(boolean condition){

        return (trueHandle, falseHandle) -> {
            if (condition){
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    /**
     * 如果参数为true抛出异常
     * @param condition 判断条件
     * @return {@link ExceptionHandle}
     * @ver v1.0.0
     */
    public static ExceptionHandle tureThrow(boolean condition){

        return (errorMessage) -> {
            if (condition){
                throw new RuntimeException(errorMessage);
            }
        };
    }
}
