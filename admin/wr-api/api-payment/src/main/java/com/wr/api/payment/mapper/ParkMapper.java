package com.wr.api.payment.mapper;

import com.wr.remote.aiot.Park;
import com.wr.remote.estate.financial.Duration;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-11-17 19:31:26
 * @Desc: 车库数据层
 */
public interface ParkMapper {
    /**
     * 根据车库id查询数据
     * @param parkId
     * @return
     */
    Park selectById(Long parkId);

    /**
     * 根据车牌号查询车位数据量
     * @param plateNo
     * @return
     */
    Integer selectCountByPlateNo(String plateNo);

    /**
     * 查询小区停车时长
     * @Author: RainCity
     * @Date: 2022/11/8 10:34
     * @param communityId
     * @return java.util.List<com.wr.remote.estate.financial.Duration>
     */
    List<Duration> selectDurationByCommunityId(Long communityId);
}
