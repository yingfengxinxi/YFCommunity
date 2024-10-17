package com.wr.aiot.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.Visual;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 18:51:43
 * @Desc: 可视对讲业务处理
 */
public interface VisualService {
    /**
     * 查询可视对讲数据
     * @param visual 可视对讲信息
     * @return java.util.List<com.wr.remote.aiot.Visual>
     */
    List<Visual> selectVisualList(Visual visual);

    /**
     * 同步可视对讲设备
     * @param communityId 小区id
     * @return java.lang.Integer
     */
    AjaxResult syncVisualData(Long communityId) throws Exception;

    /**
     * 更新设备状态
     * @param visual 可视对讲设备
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult checkOnline(Visual visual) throws Exception;

    /**
     * 根据id查询数据
     * @param visualId 可视对讲id
     * @return com.wr.remote.aiot.Visual
     */
    Visual selectVisualById(Long visualId);

    /**
     * 更新可视对讲
     * @param visual 可视对讲
     * @return java.lang.Integer
     */
    Integer updateVisual(Visual visual);

    /**
     * 批量设置可视对讲设备二维码
     * @param visual 可视对讲id
     * @return java.lang.Integer
     */
    Integer batchUpdateDeviceQr(Visual visual);

    /**
     * 可视对讲开锁事件订阅
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult subscribeLock(Long communityId);

    /**
     * 可视对讲开锁事件取消订阅
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult unsubscribeLock(Long communityId);

}
