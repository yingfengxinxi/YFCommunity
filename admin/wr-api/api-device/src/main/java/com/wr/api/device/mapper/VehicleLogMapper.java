package com.wr.api.device.mapper;

import com.wr.remote.govern.manage.VehicleLog;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: RainCity
 * @Date: 2021-11-19 11:26:15
 * @Desc:
 */
public interface VehicleLogMapper {
    /**
     * 添加车辆进出记录
     * @param vehicleLog
     * @return
     */
    Integer insertVehicleLog(VehicleLog vehicleLog);

    /**
     * 更新最新一条未支付的进车记录
     * @param vehicleLog
     * @return
     */
    Integer updateNewestInNoPay(VehicleLog vehicleLog);

    /**
     * 根据车牌查询今日出门且支付的记录
     * @param tableName
     * @param plateNo
     * @return
     */
    VehicleLog getTodayOutPayLog(@Param("tableName") String tableName, @Param("plateNo") String plateNo);

    /**
     * 根据主键更新开门时间
     * @param tableName
     * @param logId
     * @return
     */
    Integer updateOutOpenTime(@Param("tableName") String tableName, @Param("logId") Long logId);

    /**
     * 查询最新的进车库数据
     * @param tableName
     * @param plantNo
     * @return
     */
    VehicleLog getNewestInLog(@Param("tableName") String tableName, @Param("plateNo") String plantNo);
}
