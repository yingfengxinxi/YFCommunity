package com.wr.api.device.service;

/**
 * @Author: RainCity
 * @Date: 2021-11-25 16:23:54
 * @Desc:
 */
public interface AcsOpenService {

    /**
     * 合法卡比对通过 198914
     * @param req 请求数据
     */
    void acsDeviceOpen(String req);
}
