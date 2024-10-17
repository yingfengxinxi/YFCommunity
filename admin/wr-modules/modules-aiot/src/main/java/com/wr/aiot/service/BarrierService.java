package com.wr.aiot.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.Barrier;
import com.wr.remote.aiot.Roadway;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 18:51:43
 * @Desc: 道闸业务处理
 */
public interface BarrierService {
    /**
     * 查询道闸数据
     * @param barrier 道闸信息
     * @return java.util.List<com.wr.remote.aiot.Barrier>
     */
    List<Barrier> selectBarrierList(Barrier barrier);

    /**
     * 同步道闸设备
     * @param communityId 小区id
     * @return java.lang.Integer
     */
    AjaxResult syncBarrierData(Long communityId);

    /**
     * 根据id查询数据
     * @param barrierId 道闸id
     * @return com.wr.remote.aiot.barrier
     */
    Barrier selectBarrierById(Long barrierId);

    /**
     * 更新道闸
     * @param barrier 道闸
     * @return java.lang.Integer
     */
    Integer updateBarrier(Barrier barrier);

    /**
     * 批量设置道闸设备二维码
     * @param barrier 道闸id
     * @return java.lang.Integer
     */
    Integer batchUpdateDeviceQr(Barrier barrier);

    /**
     * 查询所有未绑定的车道数据
     * @param barrier 道闸数据
     * @return java.util.List<com.wr.remote.aiot.Entrance>
     */
    List<Roadway> selectUnbidRoadway(Barrier barrier);
}
