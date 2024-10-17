package com.wr.common.customize.function;

/**
 * null-not null处理接口
 *
 * @Author RainCity
 * @Date 2023-04-24 16:44:39
 */
@FunctionalInterface
public interface NullHandle {

    /**
     * 分支操作
     * @param nullHandle 为null时要进行的操作
     * @param notNullHandle 不为null时要进行的操作
     * @ver v1.0.0
     */
    void nullHandle(Runnable nullHandle, Runnable notNullHandle);

}
