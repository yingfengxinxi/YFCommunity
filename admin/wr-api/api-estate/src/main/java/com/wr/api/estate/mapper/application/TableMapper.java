package com.wr.api.estate.mapper.application;

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
     * 删除表
     * @param tableName dbName.tableName
     */
    void dropTable(@Param("tableName") String tableName);

    /**
     * 创建人员出行记录表
     * @param tableName xccj_log.bus_user_log_communityId_yyyy_MM
     *                  communityId：小区id
     *                  yyyy：当前年
     *                  MM：当前月
     */
    void createUserLogTable(@Param("tableName") String tableName);

    /**
     * 创建访客通行记录表
     * @param tableName xccj_log.bus_visitor_log_communityId_yyyy_MM
     *                  communityId：小区id
     *                  yyyy：当前年
     *                  MM：当前月
     */
    void createVisitLogTable(@Param("tableName") String tableName);

    /**
     * 创建车辆进出记录表
     * @param tableName xccj_log.bus_vehicle_log_communityId_yyyy_MM
     *                  communityId：小区id
     *                  yyyy：当前年
     *                  MM：当前月
     */
    void createVehicleLogTable(@Param("tableName") String tableName);


}
