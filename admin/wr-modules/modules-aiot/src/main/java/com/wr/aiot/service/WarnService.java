package com.wr.aiot.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.AcsData;
import com.wr.remote.aiot.SmokeFire;
import com.wr.remote.aiot.VideoData;
import com.wr.remote.aiot.VideoResult;
import com.wr.remote.aiot.VisData;
import com.wr.remote.aiot.WarnEvent;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-27 13:49:50
 * @Desc: 预警事件数据处理
 */
public interface WarnService {
    /**
     * 查询预警事件
     *
     * @param warnEvent 预警信息
     * @return java.util.List<com.wr.remote.aiot.WarnEvent>
     */
    List<WarnEvent> selectWarnEventList(WarnEvent warnEvent);

    /**
     * 根据id查询数据
     *
     * @param warnId 预警id
     * @return com.wr.remote.aiot.WarnEvent
     */
    WarnEvent selectWarnEventById(Long warnId);

    /**
     * 查询门禁预警扩展事件
     *
     * @param warnId 预警id
     * @return java.util.List<com.wr.remote.aiot.AcsData>
     */
    List<AcsData> selectAcsDataList(Long warnId, boolean getImg);

    /**
     * 查询可视对讲预警事件详情
     *
     * @param warnId 预警id
     * @return java.util.List<com.wr.remote.aiot.VisData>
     */
    List<VisData> selectVisDataList(Long warnId, boolean getImg);

    /**
     * 查询视频预警事件扩展信息
     *
     * @param warnId 预警id
     * @return java.util.List<com.wr.remote.aiot.VideoData>
     */
    List<VideoData> selectVideoDataList(Long warnId);

    /**
     * 查询视频预警分析结果
     *
     * @param dataId
     * @param communityId
     * @return
     */
    List<VideoResult> selectVideoResultList(Long dataId, Long communityId);



    /**
     * 删除数据
     *
     * @param ids 选中数据id
     * @return java.lang.Integer
     */
    Integer removeWarnEventByIds(Long[] ids);

    /**
     * 预警事件订阅
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult subscribeWarnEvent(Long communityId);

    /**
     * 预警事件取消订阅
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult unsubscribeWarnEvent(Long communityId);

    /**
     * 查询梯控历史事件信息
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult elevatorSearch(Long communityId, String startTime, String endTime, Integer pageNo, Integer pageSize, String eventTypes);


}
