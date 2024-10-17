package com.wr.api.device.mapper;

import com.wr.api.device.entity.vo.AccessVO;

/**
 * @Author: RainCity
 * @Date: 2021-09-14 19:44:41
 * @Desc:
 */
public interface AccessMapper {
    /**
     * 根据 indexCode 查询门禁点数据
     * @param indexCode
     * @return
     */
    AccessVO selectByIndexCode(String indexCode);

    /**
     * 根据indexCode查询门禁
     * @param indexCode
     * @return
     */
    AccessVO selectDoorByIndexCode(String indexCode);

    /**
     * 根据设备IP查询数据
     * @param deviceIp
     * @return
     */
    AccessVO selectByDevIp(String deviceIp);


}
