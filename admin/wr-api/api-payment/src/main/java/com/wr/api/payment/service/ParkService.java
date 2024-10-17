package com.wr.api.payment.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.aiot.Park;

/**
 * @Author: RainCity
 * @Date: 2021-11-17 19:30:38
 * @Desc: 车库服务
 */
public interface ParkService {

    /**
     * 获取最新的一条出去的记录
     * @param parkId 停车场id
     * @return 缴费信息
     */
    AjaxResult getNewestVehicleLog(Long parkId);

    /**
     * 根据车库id查询数据
     * @param parkId
     * @return
     */
    Park selectById(Long parkId);

}
