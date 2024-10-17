package com.wr.api.payment.mapper;

import com.wr.remote.govern.manage.VehicleLog;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: RainCity
 * @Date: 2021-11-18 10:22:29
 * @Desc: 停车记录数据层
 */
public interface VehicleLogMapper {
    /**
     * 根据停车库查询最新出去的记录
     * @param tableName 表名
     * @param parkIndex 车库唯一标识
     * @return
     */
    VehicleLog getNewestOutLogByParkIndex(@Param("tableName") String tableName, @Param("parkIndex") String parkIndex);

    /**
     * 根据停车牌查询最新进入的记录
     * @param tableName
     * @param plateNo
     * @return
     */
    VehicleLog getNewestInLogByPlateNo(@Param("tableName") String tableName, @Param("plateNo") String plateNo);

    /**
     * 修改入场数据
     * @param tableName
     * @param logId
     * @return
     */
    Integer updateInLog(@Param("tableName") String tableName, @Param("logId") Long logId);

    /**
     * 修改出场数据
     * @param tableName
     * @param logId
     * @return
     */
    Integer updateOutLog( @Param("tableName") String tableName, @Param("logId") Long logId);
}
