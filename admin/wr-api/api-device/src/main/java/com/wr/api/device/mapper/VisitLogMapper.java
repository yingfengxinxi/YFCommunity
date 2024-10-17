package com.wr.api.device.mapper;

import com.wr.remote.estate.log.VisitLog;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: RainCity
 * @Date: 2021-08-30 18:33:10
 * @Desc: 访客通行记录数据层
 */
public interface VisitLogMapper {

    /**
     * 增加访客通信记录
     * @param visitLog
     * @return
     */
    Integer insertVisitLog(VisitLog visitLog);

    /**
     * 根据身份证查询最新的一条数据
     * @param tableName
     * @param idCardNo
     * @return
     */
    VisitLog getLastTimeDate(@Param("tableName") String tableName, @Param("idCardNo") String idCardNo);

    /**
     * 修改拜访出门时间
     * @param tableName 表名
     * @param logId 主键id
     * @return java.lang.Integer
     */
    Integer updateVisitLogOutTime(@Param("tableName") String tableName,@Param("logId") Long logId);
}
