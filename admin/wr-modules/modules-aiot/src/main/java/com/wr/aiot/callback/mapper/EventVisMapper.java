package com.wr.aiot.callback.mapper;

import com.wr.remote.aiot.VisData;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-12-17 14:03:28
 * @Desc:
 */
public interface EventVisMapper {
    /**
     * 根据 indexCode 查询小区id
     * @param indexCode
     * @return
     */
    Long selectCommunityIdByIndexCode(String indexCode);

    /**
     * 添加数据
     * @param dataList
     * @return
     */
    Integer batchInsertVisData(List<VisData> dataList);
}
