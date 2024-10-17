package com.wr.aiot.callback.mapper;

import com.wr.remote.aiot.SmokeFire;
import com.wr.remote.aiot.VideoData;
import com.wr.remote.aiot.VideoResult;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-12-17 13:28:20
 * @Desc:
 */
public interface EventVideoMapper {
    /**
     * 根据 indexCode 查询小区id
     * @param indexCode
     * @return
     */
    Long selectCommunityIdByIndexCode(String indexCode);

    /**
     * 添加事件扩展信息
     * @param videoData
     * @return
     */
    Integer insertVideoData(VideoData videoData);

    /**
     * 批量添加预警分析结果
     * @param resultList
     * @return
     */
    Integer batchInsertVideoResult(List<VideoResult> resultList);

    /**
     * 添加烟火分析数据
     * @param dataList
     * @return
     */
    Integer insertSmokeFire(List<SmokeFire> dataList);

}
