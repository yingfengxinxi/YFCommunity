package com.wr.api.device.mapper;

import com.wr.remote.aiot.CloudAccess;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 19:48:11
 * @Desc: 云+ 控制器 硬件接口 数据层
 */
public interface CloudMapper {
    /**
     * 根据设备IP查询设备
     * @param accessIp
     * @return
     */
    CloudAccess selectByDevIp(String accessIp);

    /**
     * 根据id查询门禁点indexCode
     * @param doorId
     * @return
     */
    String selectDoorIndex(Long doorId);
}
