package com.wr.estate.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.entity.screen.*;
import com.wr.remote.aiot.WarnEvent;
import com.wr.remote.estate.info.CommunityNotice;

import java.util.List;

/**
 * @Author: GuanYu
 * @Date: 2021-09-26 11:33:53
 * @Desc: 物业端大屏
 */
public interface ScreenService {

    /**
     * 访客统计
     * @param communityId
     * @return
     */
    AjaxResult selectVisitLogCount(Long communityId);

    /**
     * 根据日月年查询访客数
     * @param communityId
     * @return
     */
    AjaxResult getDmyVisitLogCount(Long communityId);

    /**
     * 设备在线离线数量统计
     * @param communityId
     * @return
     */
    EquipmentCount selectEquipmentCount(Long communityId);

    /**
     * 查询业主年龄
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
     * 出入详情数据饼状图
     *
     * @param communityId
     * @return
     */
    PeopleInAndOutChart selectPeopleInAndOutChart(Long communityId);

    /**
     * 出入详情数据人行折线图
     * @param communityId
     * @return
     */
    AjaxResult selectVisitCharts(Long communityId);


    /**
     * 车辆进出场
     * @return
     */
    AjaxResult selectInAndOut( Long communityId);

    /**
     * 好评差评
     * @param communityId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult selectPraise(Long communityId);

    /**
     * 查询预警列表
     * @return java.util.List<com.wr.remote.aiot.WarnEvent>
     */
    List<WarnEvent> selectWarnEventList();

    /**
     * 查询平台公告
     * @return
     */
    List<CommunityNotice> selectNoticeTitle();

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
    List<String> selectWaterNum(MeterReadingWorkVo meterReadingWorkVo);
}
