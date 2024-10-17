package com.wr.aiot.callback.mapper;

import com.wr.remote.aiot.AcsData;
import com.wr.remote.estate.log.UserLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-11-25 20:47:36
 * @Desc: 门禁事件数据层
 */
public interface EventAcsMapper {

    /**
     * 批量添加用户进出记录
     * @param tableName
     * @param userLogs
     * @return
     */
    Integer batchInsertUserLog(@Param("tableName") String tableName, @Param("userLogs") List<UserLog> userLogs);

    /**
     * 新增业主租客通信记录
     * @param userLog
     * @return
     */
    Integer insertUserLog(UserLog userLog);

    /**
     * 根据 indexCode 查询小区id
     * @param indexCode
     * @return
     */
    Long selectCommunityIdByIndexCode(String indexCode);

    /**
     * 添加事件数据
     * @param dataList
     * @return
     */
    Integer batchInsertAcsData(List<AcsData> dataList);

}
