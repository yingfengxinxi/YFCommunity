package com.wr.aiot.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: RainCity
 * @Date: 2021-10-26 16:50:30
 * @Desc: 动态表
 */
public interface TableMapper {
    /**
     * 检查是否存在表
     * @param tableName 表名
     * @return
     */
    Integer existTable(@Param("tableName") String tableName);

    /**
     * 删除表
     * @param tableName dbName.tableName
     */
    void dropTable(@Param("tableName") String tableName);

}
