package com.wr.govern.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 18:14:07
 * @Desc: 动态创建表
 */
@Mapper
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
