package com.wr.api.payment.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: RainCity
 * @Date: 2021-10-26 16:50:30
 * @Desc: 动态创建表
 */
public interface TableMapper {
    /**
     * 检查是否存在表
     * @param tableName 表名
     * @return
     */
    Integer existTable(@Param("tableName") String tableName);

    /**
     * 创建缴费记录表
     * @param tableName sc_pay_log_communityId_yyyy_MM
     *                  communityId：小区id
     *                  yyyy：当前年
     *                  MM：当前月
     */
    void createPayLogTable(@Param("tableName") String tableName);

}
