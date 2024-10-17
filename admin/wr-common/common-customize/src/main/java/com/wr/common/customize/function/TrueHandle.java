package com.wr.common.customize.function;

/**
 * true-false判断
 *
 * @Author RainCity
 * @Date 2023-04-24 17:10:06
 */
@FunctionalInterface
public interface TrueHandle {
    /**
     * 分支操作
     * @param trueHandle 为true时要进行的操作
     * @param falseHandle 为false时要进行的操作
     * @ver v1.0.0
     */
    void trueHandle(Runnable trueHandle, Runnable falseHandle);
}
