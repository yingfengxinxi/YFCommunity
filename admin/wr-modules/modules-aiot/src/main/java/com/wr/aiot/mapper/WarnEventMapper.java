package com.wr.aiot.mapper;

import com.wr.remote.aiot.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-27 13:58:16
 * @Desc: 预警事件数据层
 */
public interface WarnEventMapper {
    /**
     * 查询预警事件
     * @param warnEvent 预警信息
     * @return java.util.List<com.wr.remote.aiot.WarnEvent>
     */
    List<WarnEvent> selectWarnEventList(WarnEvent warnEvent);

    /**
     * 根据id查询数据
     * @param warnId 预警id
     * @return com.wr.remote.aiot.WarnEvent
     */
    WarnEvent selectWarnEventById(Long warnId);

    /**
     * 查询门禁预警扩展事件
     * @param warnId 预警id
     * @return java.util.List<com.wr.remote.aiot.AcsData>
     */
    List<AcsData> selectAcsDataList(Long warnId);

    /**
     * 查询可视对讲预警事件详情
     * @param warnId 预警id
     * @return java.util.List<com.wr.remote.aiot.VisData>
     */
    List<VisData> selectVisDataList(Long warnId);

    /**
     * 查询视频预警事件扩展信息
     * @param warnId 预警id
     * @return java.util.List<com.wr.remote.aiot.VideoData>
     */
    List<VideoData> selectVideoDataList(Long warnId);

    /**
     * 查询视频预警分析结果
     * @param dataId 扩展信息id
     * @return java.util.List<com.wr.remote.aiot.VideoResult>
     */
    List<VideoResult> selectVideoResultList(Long dataId);

    /**
     * 查询烟雾/火点检测分析结果
     * @param dataId 扩展信息id
     * @return java.util.List<com.wr.remote.aiot.SmokeFire>
     */
    List<SmokeFire> selectSmokeFireList(Long dataId);

    /**
     * 删除数据
     * @param ids 选中数据id
     * @return java.lang.Integer
     */
    Integer removeWarnEventByIds(Long[] ids);
    /**
     * 添加联动事件
     * @param warnEvent
     * @return
     */
    Integer insertWarnEvent(WarnEvent warnEvent);

    /**
     *
     * @param eventId
     * @return
     */
    Long getEventIdInfo(@Param("eventId") String eventId);
}
