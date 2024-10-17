package com.wr.common.customize.function;

/**
 * 抛异常接口
 *
 * @Author RainCity
 * @Date 2023-04-24 16:44:39
 */
@FunctionalInterface
public interface ExceptionHandle {

    /**
     * 抛出异常信息
     * @param message 异常信息
     * @ver v1.0.0
     */
    void throwMessage(String message);

}
