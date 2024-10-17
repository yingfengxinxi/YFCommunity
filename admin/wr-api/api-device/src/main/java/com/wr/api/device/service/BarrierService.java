package com.wr.api.device.service;

/**
 * @Author: RainCity
 * @Date: 2021-11-18 19:38:10
 * @Desc: 车辆事件服务层
 */
public interface BarrierService {

    /**
     * 入场压线
     * @param req 接收数据
     * @return
     */
    void entryPressureLine(String req);

    /**
     * 入场压线
     * @param req 接收数据
     * @return
     */
    void outPressureLine(String req);
}
