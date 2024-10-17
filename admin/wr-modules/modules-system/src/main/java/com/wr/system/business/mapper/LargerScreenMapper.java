package com.wr.system.business.mapper;

import com.wr.remote.aiot.VisitingSecord;
import com.wr.remote.domain.Area;
import com.wr.remote.domain.Community;
import com.wr.system.business.domain.screen.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: GuanYu
 * @Date: 2021-09-01 17:34:19
 * @Desc:
 */
public interface LargerScreenMapper {

    /**
     * 物业员工数据
     *
     * @param
     * @return
     */
    EmployeeData selectEmployeeData(Long deptId);


    /**
     * 房屋状态大屏数据
     *
     * @param communityId
     * @param roomStatus
     * @return com.wr.service.entity.operLargerScreen.HousingStatusData
     */
    Integer selectHousingStatusData(@Param("communityId") Long communityId, @Param("roomStatus") String roomStatus);

    /**
     * 业主年龄分布
     *
     * @param communityId
     * @param type
     * @return com.wr.service.entity.operLargerScreen.OwnerAgeData
     */
    Integer selectOwnerAgeData(@Param("communityId") Long communityId, @Param("type") String type);

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

    /**
     * 设备统计
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
    List<OperPatrolOrder> selectPatrolOrder(@Param("communityId") Long communityId, @Param("startTime") Date startTime);

    /**
     * 社区人流量统计
     * user
     *
     * @return
     */
    List<PeopleVisit> selectPeopleUser(String userTable);

    /**
     * 社区人流量统计
     *
     * @return
     */
    List<PeopleVisit> selectPeopleVisit(String visitTable);

    /**
     * 统计车出门记录
     */
    List<PeopleVisit> selectVehicleCountOut(@Param("vehicleTable") String vehicleTable, @Param("inoutType") String inoutType);

    /**
     * 年度报事报修
     */
    ReportRepair selectReportRepair(Long communityId);

    /**
     * 统计车进去的数量
     */
    PeopleVisit selectVehicleCount(UserTableLog userTableLog);

    /**
     * 根据区域id查询小区
     */
    List<Community> selectCommunityByAeraId(Long areaId);

    /**
     * 查询树状列表数据集合
     */
    List<Area> selectAreaList(Area area);

    /**
     * 根据小区id查询
     */
    List<Community> selectCommunityById(Long communityId);

    /**
     * 通过小区id查询设备集合
     */
    List<CameraVo> selectCameraVo(Long communityId);

    /**
     * 通过小区id查询小区
     */
    Community getCommunityById(Long communityId);

    /**
     * 通过id查询设备详情
     */
    CameraVo getCameraById(@Param("id") Long id);

    /**
     * 根据分数排序查询
     */
    List<SuggestVo> selectSuggestScore();

    /**
     * 查询费用
     *
     * @param visitingSecord
     * @return
     */
    AmountFee selectAmount(VisitingSecord visitingSecord);

    /**
     * 查询员工数量
     *
     * @param staffInfo
     * @return
     */
    int selectStaffCount(StaffInfo staffInfo);

    /**
     * 根据小区获取部门信息
     *
     * @param communityId
     * @return
     */
    DeptVo getDeptByCommunityId(Long communityId);

    /**
     * 查询 数据
     *
     * @param tableName
     * @return java.util.List<com.wr.service.entity.operLargerScreen.UserLog>
     * @Author: RainCity
     * @Date: 2022/6/28 18:11
     */
    List<UserLog> selectUserLogList(@Param("tableName") String tableName,
                                    @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 小区情况统计
     *
     * @param communityId
     * @return
     */
    CommunityCount selectCommunityCount(Long communityId);

    /**
     * 居住舒适量统计
     *
     * @param communityId
     * @return
     */
    CommunityDetail selectCommunityDetail(Long communityId);

    /**
     * 通知公告列表数据展示
     *
     * @param communityId
     * @return
     */
    List<NoticeVo> selectNotice(Long communityId);

    /**
     * 招商公示 列表数据展示
     *
     * @param communityId
     * @return
     */
    List<InvestVo> selectInvest(Long communityId);

    /**
     * 获取字典数据
     *
     * @return
     */
    List<DictDataVo> selectDictData(DictDataVo dictDataVo);

    /**
     * 水表，电表抄表统计数据
     *
     * @param meterReadingWorkVo
     * @return
     */
    String selectWaterNum(MeterReadingWorkVo meterReadingWorkVo);

    /**
     * 月每日访客数据
     *
     * @param visitingSecord
     * @return
     */
    List selectMonthVisitCount(VisitingSecord visitingSecord);

    /**
     * 年每月访客数据
     *
     * @param visitingSecord
     * @return
     */
    Integer selectYearVisitCount(VisitingSecord visitingSecord);

    /**
     * 小区人行进入流量走势图
     *
     * @param visitingSecord
     * @return
     */
    List<Integer> getUserCount(VisitingSecord visitingSecord);

    /**
     * 小区车行进入流量走势图
     *
     * @param visitingSecord
     * @return
     */
    List<Integer> getVehicleCount(VisitingSecord visitingSecord);

    /**
     * 小区人行，车行进入流量
     *
     * @param visitingSecord
     * @return
     */
    Map<String, Object> selectFlowNum(VisitingSecord visitingSecord);

    /**
     * 小区出入人员列表
     *
     * @param communityId
     * @return
     */
    List<UserDoorPeopleVo> selectUserDoorLogList(Long communityId);
}
