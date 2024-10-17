package com.wr.aiot.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.Barrier;
import com.wr.remote.aiot.Entrance;
import com.wr.remote.aiot.Park;
import com.wr.remote.aiot.Roadway;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 18:51:43
 * @Desc: 停车场业务处理
 */
public interface ParkService {
    /**
     * 查询停车场数据
     * @param park 停车场信息
     * @return java.util.List<com.wr.remote.aiot.Park>
     */
    List<Park> selectParkList(Park park);

    /**
     * 同步停车场设备
     * @param communityId 小区id
     * @return java.lang.Integer
     */
    AjaxResult syncParkData(Long communityId) throws Exception;

    /**
     * 根据id查询数据
     * @param parkId 停车场id
     * @return com.wr.remote.aiot.Park
     */
    Park selectParkById(Long parkId);

    /**
     * 根据停车场唯一标识查询停车场出入口
     * @param parkIndexCode 停车场唯一标识
     * @return java.util.List<com.wr.remote.aiot.Entrance>
     */
    List<Entrance> selectEntranceByPark(String parkIndexCode);

    /**
     * 根据出入口唯一标识查询车道
     * @param entranceIndexCode 出入口唯一标识
     * @return java.util.List<com.wr.remote.aiot.Roadway>
     */
    List<Roadway> selectRoadwayByEntrance(String entranceIndexCode);

    /**
     * 根据车道IndexCode查询设备信息
     * @param roadwayIndexCode 车道IndexCode
     * @return Barrier
     */
    List<Barrier> selectBarrierByRoadway(String roadwayIndexCode);

    /**
     * 更新停车场
     * @param park 停车场
     * @return java.lang.Integer
     */
    Integer updatePark(Park park);

    /**
     * 订阅停车场出入事件
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult subscribeEvent(Long communityId);

    /**
     * 停车场出入事件取消订阅
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult unsubscribeEvent(Long communityId);

    /**
     * 批量设置停车场支付二维码
     * @param park 停车场id
     * @return java.lang.Integer
     */
    Integer batchUpdatePayQr(Park park);

}
