package com.wr.api.device.mapper;

import com.wr.remote.aiot.Entrance;
import com.wr.remote.aiot.Park;
import com.wr.remote.aiot.Roadway;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.center.BusVehicle;
import com.wr.remote.estate.financial.Duration;
import com.wr.remote.estate.log.PayLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-11-07 16:24:49
 * @Desc: 车场事件数据层
 */
public interface BarrierMapper {

    /**
     * 根据车库唯一标识查询车库
     * @param parkIndex
     * @return com.wr.remote.aiot.Park
     */
    Park getByParkIndex(String parkIndex);

    /**
     * 根据车牌号查询车辆信息
     * @param plateNo
     * @return com.wr.remote.estate.center.BusVehicle
     */
    BusVehicle getByPlateNo(String plateNo);

    /**
     * 根据账号id查询车位
     * @param accountId
     * @return com.wr.remote.estate.center.BusCarport
     */
    BusCarport getCarportByAccountId(Long accountId);

    /**
     * 根据出入口唯一标识查询出入口信息
     * @param gateIndex
     * @return com.wr.remote.aiot.Entrance
     */
    Entrance getByGateIndex(String gateIndex);

    /**
     * 根据车道唯一标识查询数据
     * @param roadwayIndex
     * @return com.wr.remote.aiot.Roadway
     */
    Roadway getByRoadwayIndex(String roadwayIndex);

    /**
     * 根据车牌号查询车辆信息
     * @param plateNo
     * @return com.wr.remote.estate.center.BusVehicle
     */
    BusVehicle getVehicleByPlateNo(String plateNo);

    /**
     * 查询小区停车缴费开关
     * @param communityId
     * @return java.lang.String
     */
    String getSwitchTypeByCommunityId(Long communityId);

    /**
     * 查询最新一条的支付记录
     * @param tableName
     * @param plateNo
     * @return com.wr.remote.estate.log.PayLog
     */
    PayLog selectNewestPayByPlantNo(@Param("tableName") String tableName, @Param("plateNo") String plateNo);

    /**
     * 查询小区停车时长
     * @Author: RainCity
     * @Date: 2022/11/8 10:34
     * @param communityId
     * @return java.util.List<com.wr.remote.estate.financial.Duration>
     */
    List<Duration> selectDurationByCommunityId(Long communityId);
}
