package com.wr.estate.mapper;

import com.wr.estate.entity.screen.*;
import com.wr.remote.aiot.WarnEvent;
import com.wr.remote.estate.info.CommunityNotice;
import com.wr.remote.govern.manage.VehicleLog;
import com.wr.remote.govern.manage.VehicleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: GuanYu
 * @Date: 2021-09-26 11:32:56
 * @Desc: 物业端大屏Mapper
 */
public interface ScreenMapper {

    /**
     * 设备在线离线数量统计
     *
     * @param communityId
     * @return
     */
    EquipmentCount selectEquipmentCount(Long communityId);

    /**
     * 业主性别/年龄
     *
     * @param communityId
     * @return
     */
    OwnerAge selectOwnerAge(Long communityId);

    /**
     * 小区工单
     *
     * @param communityId
     * @return
     */
    List<PatrolOrderScreen> selectPatrolOrder(Long communityId);


    /**
     * 物业员工数据
     *
     * @param deptId
     * @return
     */
    CommunityStaff selectStaffCount(Long deptId);

    /**
     * 查询本周访客人数
     *
     * @param logDto
     * @return
     */
    Integer getVisitorNum(LogDto logDto);

    /**
     * 查询今日访客数
     *
     * @param communityId
     * @return
     */
    Integer getDayVisitorNum(Long communityId);

    /**
     * 查询本月访客数
     *
     * @param communityId
     * @return
     */
    Integer getMonthNum(Long communityId);

    /**
     * 查询今年访客数
     *
     * @param communityId
     * @return
     */
    Integer getYearVisitorNum(Long communityId);

    /**
     * 查询本月业主的出入数
     *
     * @param communityId
     * @return
     */
    Map<String, Object> getUserInOutNum(Long communityId);

    /**
     * 车辆进出场
     *
     * @return
     */
    VehicleVo selectInAndOut(@Param("vehicleOut") String vehicleOut, @Param("communityId") Long communityId);

    /**
     * 查询评价数
     *
     * @param communityId 小区id
     * @param assessScore 0：好评，1：差评，2：未评价
     * @return
     */
    Integer selectAssessCount(@Param("communityId") Long communityId, @Param("assessScore") String assessScore);

    /**
     * 查询预警
     *
     * @param warnEvent
     * @param estateId
     * @return java.util.List<com.wr.remote.aiot.WarnEvent>
     */
    List<WarnEvent> selectWarnEventList(@Param("warnEvent") WarnEvent warnEvent, @Param("estateId") Long estateId);

    /**
     * 查询平台公告
     *
     * @return
     */
    List<CommunityNotice> selectNoticeTitle(@Param("estateId") Long estateId);

    /**
     * 获取字典数据
     * @param dictDataVo
     * @return
     */
    List<DictDataVo> selectDictData(DictDataVo dictDataVo);

    /**
     * 水表，电表抄表统计数据
     * @param meterReadingWorkVo
     * @return
     */
    String selectWaterNum(MeterReadingWorkVo meterReadingWorkVo);

    /**
     * 人行年统计列表
     * @param communityId
     * @return
     */
    List<Integer> selectMonthUserCount(Long communityId);

    /**
     * 车行年统计列表
     * @param communityId
     * @return
     */
    List<Integer> selectMonthVisitCount(Long communityId);

    /**
     * 人行当月统计
     * @param communityId
     * @return
     */
    Integer getUserMonthNum(Long communityId);

    /**
     * 车行当月统计
     * @param communityId
     * @return
     */
    Integer getVehicleMonthNum(Long communityId);
}
