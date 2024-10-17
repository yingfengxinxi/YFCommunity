package com.wr.system.business.mapper;

import com.wr.system.business.domain.screen.TableName;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 模糊查询表名Visit
     * */
    List<TableName> selectVisitLike(String visitTable);

    /**
     * 查询年
     * */
    List<TableName> selectVisitLikeYears(String year);

    /**
     * 查询访客
     * */
    List<TableName> selectVisitLikeYear(TableName tableName);

    /**
     *模糊查询User
     * */
    List<TableName> selectUserLike(String userTable);

    /**
     * 模糊查询车辆Vehicle
     * */
    List<TableName> selectVehicleLike(String vehicleTable);

}
