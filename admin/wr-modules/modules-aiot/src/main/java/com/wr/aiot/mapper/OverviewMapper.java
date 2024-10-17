package com.wr.aiot.mapper;

import com.wr.aiot.entity.VehicleData;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-11-01 16:17:53
 * @Desc: 停车场数据总览数据层
 */
public interface OverviewMapper {
    /**
     * 查询某月停车费总和
     * @param tableName 表名
     * @return java.math.BigDecimal
     */
    BigDecimal selectTotalAmount(String tableName);

    /**
     * 查询车辆进出次数
     * @param tableName 表名
     * @param inoutType 进出类型
     * @return java.util.List<com.wr.aiot.entity.VehicleData>
     */
    List<VehicleData> selectFirstHarfVehicleData(@Param("tableName") String tableName, @Param("day") int day, @Param("inoutType") String inoutType);

    /**
     * 查询车辆进出次数
     * @param tableName 表名
     * @param inoutType 进出类型
     * @return java.util.List<com.wr.aiot.entity.VehicleData>
     */
    List<VehicleData> selectSecHarfVehicleData(@Param("tableName") String tableName, @Param("day") int day, @Param("inoutType") String inoutType);

    /**
     * 查询物业下所有车位
     * @param estateId 物业id
     * @return java.lang.Integer
     */
    Integer countCarportTotal(Long estateId);

    /**
     * 查询物业下所有车辆
     * @param estateId 物业id
     * @param auditTime 审核时间
     * @return java.lang.Integer
     */
    Integer countVehicleTotal(@Param("estateId") Long estateId, @Param("auditTime") Date auditTime);

    /**
     * 查询今日如愿的车辆数
     * @param tableName 表名
     * @param userType 用户类型,仅用作查询条件判断
     * @return java.lang.Integer
     */
    Integer countVehicleLogToday(@Param("tableName") String tableName, @Param("userType") String userType);
}
