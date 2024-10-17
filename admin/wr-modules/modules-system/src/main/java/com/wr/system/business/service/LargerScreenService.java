package com.wr.system.business.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.Area;
import com.wr.remote.domain.Community;
import com.wr.remote.domain.TreeSelect;
import com.wr.system.business.domain.screen.*;

import java.util.List;

/**
 * @Author: GuanYu
 * @Date: 2021-09-02 08:40:02
 * @Desc:
 */
public interface LargerScreenService {
    /**
     * 物业员工数据
     * @param
     * @return
     */
    EmployeeData selectEmployeeData(Long deptId);


    /**
     *  房屋状态大屏数据
     * @param
     * @return
     */
    AjaxResult selectHousingStatusData(Long communityId);

    /**
     * 业主年龄分布
     *
     * @param
     * @return
     */
    AjaxResult selectOwnerAgeData(Long communityId);

    /**
     * 社区人员结构
     *
     * @param
     * @return
     */
    CommunityPersonnel selectCommunityPersonnel(Long communityId);

    /**
     * 工单统计
     *
     * @param
     * @return
     */
    WorkOrder selectWorkOrderData(Long communityId);

    /**设备统计
     *
     * @param
     * @return
     */
    EquipmentStatistics selectEquipmentStatistics(Long communityId);

    /**
     * 设备状态统计
     *
     * @param
     * @return
     */
    EquipmentStatus selectEquipmentStatus(Long communityId);

    /**
     * 巡查巡更巡检列表
     *
     * @param
     * @return
     */
    List<OperPatrolOrder> selectPatrolOrder(Long communityId);

    /**
     * 社区人流量统计
     *
     * @return
     */
    AjaxResult selectPeopleVisit(Long communityId);

    /**
     * 年度报事报修
     */
    ReportRepair selectReportRepair(Long communityId);

    /**
     * 根据区域id查询小区
     * */
    List<Community> selectCommunityByAeraId(Long areaId);

    /**
     *
     * 查询树状列表数据集合
     * */
    List<Area> selectAreaList(Area area);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param areas 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildAreaTreeSelect(List<Area> areas);

    /**
     * 通过小区id查询设备集合
     * */
    List<CameraVo> selectCameraVo(Long communityId);

    /**
     * 查询行人记录
     * */
    AjaxResult selectUserList(Long communityId);

    /**
     * 通过id查询设备详情
     * */
    CameraVo getCameraById(Long id);

    /**
     * 根据分数排序查询
     * */
    List<SuggestVo> selectSuggestScore();

    /**
     * 查询费用
     *
     * @param
     * @param communityId
     * @return
     */
    AmountFee selectAmount(Long communityId);

    /**
     * 员工服务
     * @param communityId
     * @return
     */
    StaffVo selectStaffNum(Long communityId);

    AjaxResult selectInformation(Long communityId);

    /**
     * 查询本周的数量
     * */
    RepaireNumber  selectCount();

    /**
     * 查询一键处理相关数据
     * */
    OrderStatusVo selectDealCount(Long communityId);

    /**
     * 小区设备告警统计
     * @param communityId
     * @return java.util.List<com.wr.system.business.domain.screen.EquipmentAlarm>
     */
    List<EquipmentAlarm> selectEquipmentAlarmAll(Long communityId);

    /**
     * 小区人行/车行进出流量走势图
     * @param communityId
     * @param month
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult getUserVehicleCount(Long communityId, int month);

    /**
     * 年度维修设备统计
     * @param year
     * @param communityId
     * @return com.wr.system.business.domain.screen.AnnualDay
     */
    AnnualDay getMonthlyMaintenanceStatistics(Integer year, Long communityId);

    /**
     * 设备异常列表
     * @Author: RainCity
     * @Date: 2022/11/3 14:08
     * @param communityId
     * @return java.util.List<com.wr.system.business.domain.screen.DeviceVo>
     */
    List<DeviceVo> getListDerviceAbnormal(Long communityId);

    /**
     * 查询一到十二天访客人数
     * @param communityId
     * @param month
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult selectMonthVisitCount(Long communityId, int month);

    /**
     * 查询一到十二月访客人数
     * @param communityId
     * @param year
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult selectYearVisitCount(Long communityId, int year);

    /**
     * 小区情况统计
     * @param communityId
     * @return
     */
    CommunityCount selectCommunityCount(Long communityId);

    /**
     * 居住舒适量统计
     * @param communityId
     * @return
     */
    CommunityDetail selectCommunityDetail(Long communityId);

    /**
     * 通知公告列表数据展示
     * @param communityId
     * @return
     */
    List<NoticeVo> selectNotice(Long communityId);

    /**
     * 招商公示 列表数据展示
     * @param communityId
     * @return
     */
    List<InvestVo> selectInvest(Long communityId);

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
