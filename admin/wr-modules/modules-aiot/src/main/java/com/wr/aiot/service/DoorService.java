package com.wr.aiot.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.Door;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 16:04:43
 * @Desc: 门禁点业务处理
 */
public interface DoorService {

    /**
     * 查询门禁点数据
     * @param door 门禁点信息
     * @return java.util.List<com.wr.remote.aiot.Door>
     */
    List<Door> selectDoorList(Door door);

    /**
     * 同步门禁点设备
     * @param communityId 小区id
     * @return java.lang.Integer
     */
    AjaxResult syncDoorData(Long communityId) throws Exception;

    /**
     * 检测设备状态
     * @param door 门禁点信息
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult checkOnline(Door door) throws Exception;

    /**
     * 根据id查询数据
     * @param doorId 门禁点id
     * @return com.wr.remote.aiot.Door
     */
    Door selectDoorById(Long doorId);

    /**
     * 更新门禁点
     * @param door 门禁点信息
     * @return java.lang.Integer
     */
    Integer updateDoor(Door door);

    /**
     * 查询小区下所有门禁点
     * @param communityId 小区id
     * @return java.util.List<com.wr.remote.aiot.Door>
     */
    List<Door> selectAllDoorList(Long communityId);
}
