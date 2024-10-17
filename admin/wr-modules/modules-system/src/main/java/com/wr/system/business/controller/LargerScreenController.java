package com.wr.system.business.controller;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.ProtocolConstants;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.Area;
import com.wr.remote.domain.Community;
import com.wr.common.customize.util.CommunityHik;
import com.wr.system.business.domain.screen.*;
import com.wr.system.business.service.CommunityService;
import com.wr.system.business.service.LargerScreenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: GuanYu
 * @Date: 2021-09-02 08:45:06
 * @Desc: 大屏数据
 */
@RestController
@RequestMapping("/screenData")
public class LargerScreenController extends BaseController {

    @Autowired
    private CommunityService communityService;
    @Autowired
    private LargerScreenService screenDataService;
    @Autowired
    private RedisService redisService;

    /**
     * 大屏员工工作 类型数据
     *
     * @param
     * @return
     */
    @Deprecated
    @GetMapping("/employeeData")
    public AjaxResult employeeData(Long deptId){
        EmployeeData employeeData = screenDataService.selectEmployeeData(deptId);
        return AjaxResult.success(employeeData);
    }

    /**
     * 大屏员工服务
     *
     * @param
     * @return
     */
    @GetMapping("/staffData")
    public AjaxResult staffData(Long communityId){
        StaffVo staffVo = screenDataService.selectStaffNum(communityId);
        return AjaxResult.success(staffVo);
    }

    /**
     * 大屏房间状态数据
     * @param
     * @return
     */
    @GetMapping("/housingStatusData")
    public AjaxResult housingStatus(Long communityId){
        return screenDataService.selectHousingStatusData(communityId);
    }

    /**
     * 大屏业主年龄分布数据
     *
     * @param
     * @return
     */
    @GetMapping("/ownerAgeData")
    public AjaxResult ownerAgeData(Long communityId){
        return screenDataService.selectOwnerAgeData(communityId);
    }

    /**
     * 社区人员结构
     *
     * @param
     * @return
     */
    @GetMapping("/communityPersonnelData")
    public AjaxResult communityPersonnelData(Long communityId){
        CommunityPersonnel communityPersonnel = screenDataService.selectCommunityPersonnel(communityId);
        return AjaxResult.success(communityPersonnel);
    }

    /**
     * 工单统计
     *
     * @param
     * @return
     */
    @GetMapping("/workOrderData")
    public AjaxResult workOrderData(Long communityId){

        WorkOrder workOrder = screenDataService.selectWorkOrderData(communityId);
        return AjaxResult.success(workOrder);
    }

    /**
     * 设备统计
     *
     * @param
     * @return
     */
    @GetMapping("/equipmentStatisticsData")
    public AjaxResult equipmentStatisticsData(Long communityId){
        EquipmentStatistics equipmentStatistics = screenDataService.selectEquipmentStatistics(communityId);
        return AjaxResult.success(equipmentStatistics);
    }

    /**
     * 设备在线状态统计
     *
     * @param
     * @return
     */
    @GetMapping("/equipmentStatusData")
    public AjaxResult equipmentStatusData(Long communityId){
        EquipmentStatus equipmentStatus = screenDataService.selectEquipmentStatus(communityId);
        return AjaxResult.success(equipmentStatus);
    }

    /**
     * 巡查巡更巡检列表
     * @param
     * @return
     */
    @GetMapping("/getPatrolOrder")
    public AjaxResult selectPatrolOrder(Long communityId){
        List<OperPatrolOrder> operPatrolOrders = screenDataService.selectPatrolOrder(communityId);
        return AjaxResult.success(operPatrolOrders);
    }

    /**
     * 社区人流量统计
     * @return
     */
    @GetMapping("/getPeopleVisit")
    public AjaxResult selectPeopleVisit(Long communityId){
        return screenDataService.selectPeopleVisit(communityId);
    }

    /**
     * 年度报事报修
     * @return
     */
    @GetMapping("/getReportRepair")
    public AjaxResult selectReportRepair(Long communityId){
        ReportRepair reportRepair = screenDataService.selectReportRepair(communityId);
        return AjaxResult.success(reportRepair);
    }

    /**
     * 根据小区查询区域id
     * */
    @GetMapping("/selectCommunityByAreaId")
    public AjaxResult selectCommunityByAreaId(Long areaId){
        List<Community> list = screenDataService.selectCommunityByAeraId(areaId);
        if(list==null){
            return AjaxResult.error("无数据");
        }
        return AjaxResult.success(list);
    }

    /**
     * 查询区域数据
     * */
    @GetMapping("/selectAreaList")
    public AjaxResult selectAreaList(Area area){
        List<Area> list = screenDataService.selectAreaList(area);
        return AjaxResult.success(list);
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeSelect")
    public AjaxResult treeSelect(Area area)
    {
        List<Area> areas = screenDataService.selectAreaList(area);
        return AjaxResult.success(screenDataService.buildAreaTreeSelect(areas));
    }

    /**
     * 通过小区id查询设备信息
     * */
    @GetMapping("/selectCameraVo")
    public AjaxResult selectCameraVo(Long communityId){
        List<CameraVo> list = screenDataService.selectCameraVo(communityId);
        return AjaxResult.success(list);
    }

    /**
     * 获取视频流
     * */
    @GetMapping("/getCameraById")
    public AjaxResult getCameraById(CameraVo cameraVo){
        CommunityHik hik = communityService.selectHikByCommunityId(cameraVo.getCommunityId());
        if(null == hik){
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("url", null);
        String urlKey = "CAMERA_URL:";
        String url = redisService.getCacheObject(urlKey + cameraVo.getIndexCode());
        if(StringUtils.isNotEmpty(url)){
            ajaxResult.put("url", url);
        }else {
            HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
            url = HikUtils.camerasPreviewURLs(cameraVo.getIndexCode(), ProtocolConstants.PROTOCOL_WS);
            ajaxResult.put("url", url);
            redisService.setCacheObject(urlKey + cameraVo.getIndexCode(), url, 5L, TimeUnit.MINUTES);
        }
        return ajaxResult;
    };


    /**
     * 查询人员记录
     * */
   @GetMapping("/selectUserList")
   public AjaxResult selectUserList(Long communityId){
       return screenDataService.selectUserList(communityId);
   }

    /**
     * 查询物业排名
     * */
    @GetMapping("/selectSuggestScore")
    public AjaxResult selectSuggestScore(){
        List<SuggestVo> list = screenDataService.selectSuggestScore();
        if(list==null){
            return AjaxResult.error("无数据");
        }
        List<SuggestVo> result = list.stream()
                .sorted(Comparator.comparing(SuggestVo::getScore).reversed())
                .collect(Collectors.toList());
        return AjaxResult.success(result);
    }


    @GetMapping("/getAmountFee")
    public AjaxResult getAmountFee(Long communityId){
        AmountFee amountFee = screenDataService.selectAmount(communityId);
        return AjaxResult.success(amountFee);
    }


    /**
     * 巡检/巡查/巡更
     * @param communityId
     * @return
     */
    @GetMapping("/selectInformation")
    public AjaxResult selectInformation( Long communityId){

        return screenDataService.selectInformation(communityId);
    }

    /**
     * 一键处理本周数据
     * */
    @GetMapping("/selectCount")
    public AjaxResult selectCount(){
        RepaireNumber number = screenDataService.selectCount();
       return AjaxResult.success(number);
    }

    /**
     * 一键处理处理以及总数据 by 小区ID
     * */
    @GetMapping("/selectDealCount")
    public AjaxResult selectDealCount(Long communityId){

        OrderStatusVo statusVo = screenDataService.selectDealCount(communityId);
        return AjaxResult.success(statusVo);
    }

    /**
     * 根据小区ID search
     * @param communityId 小区ID
     * @return
     */
    @RequestMapping("/getAllNum")
    public AjaxResult selectAllData(Long communityId){
        List<EquipmentAlarm> equipmentAlarms = screenDataService.selectEquipmentAlarmAll(communityId);
        return AjaxResult.success(equipmentAlarms);
    }

    @GetMapping("/user_vehicle")
    public AjaxResult userCount(Long communityId,int month) {
        return screenDataService.getUserVehicleCount(communityId, month);
    }

    /**
     * 年度维修设备统计
     * @return
     */
    @GetMapping("/getMaintenance")
    public AjaxResult getMonthlyMaintenanceStatistics(Integer year,Long communityId) {
        AnnualDay annualDay = screenDataService.getMonthlyMaintenanceStatistics(year,communityId);
        return AjaxResult.success(annualDay);
    }

    /**
     * 设备异常列表
     * @param communityId
     * @return
     */
    @GetMapping("/device_abnormal")
    public AjaxResult deviceAbnormal(Long communityId) {
        List<DeviceVo> deviceVos = screenDataService.getListDerviceAbnormal(communityId);
        return AjaxResult.success(deviceVos);
    }

    /**
     * 查询一到十二天访客人数
     * @author: DIANWEI
     * @date: 2022/1/4
     * @param communityId
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/getMonthVisit")
    public AjaxResult selectMonthVisit(Long communityId,int month){
        return screenDataService.selectMonthVisitCount(communityId, month);
    }

    /**
     * 查询一到十二月访客人数
     * @author: DIANWEI
     * @date: 2022/1/4
     * @param communityId
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/getYearVisit")
    public AjaxResult selectYearVisit(Long communityId,int year){
        return screenDataService.selectYearVisitCount(communityId, year);
    }

    /**
     * 小区情况统计
     * @param communityId
     * @return
     */
    @GetMapping("/communityCount")
    public AjaxResult selectCommunityCount(Long communityId) {
        CommunityCount communityCount = screenDataService.selectCommunityCount(communityId);
        return AjaxResult.success(communityCount);
    }

    /**
     * 居住舒适量统计
     * @param communityId
     * @return
     */
    @GetMapping("/communityDetail")
    public AjaxResult selectCommunityDetail(Long communityId) {
        CommunityDetail communityDetail = screenDataService.selectCommunityDetail(communityId);
        return AjaxResult.success(communityDetail);
    }

    /**
     * 通知公告列表数据展示
     * @param communityId
     * @return
     */
    @GetMapping("/selectNotice")
    public AjaxResult selectNotice(Long communityId) {
        List<NoticeVo> list = screenDataService.selectNotice(communityId);
        return AjaxResult.success(list);
    }

    /**
     * 招商公示 列表数据展示
     * @param communityId
     * @return
     */
    @GetMapping("/selectInvest")
    public AjaxResult selectInvest(Long communityId) {
        List<InvestVo> list = screenDataService.selectInvest(communityId);
        return AjaxResult.success(list);
    }

    /**
     * 获取字典数据
     * @param dictDataVo
     * @return
     */
    @GetMapping("/dictData")
    public AjaxResult selectDictData(DictDataVo dictDataVo) {
        List<DictDataVo> dictDataVos = screenDataService.selectDictData(dictDataVo);
        return AjaxResult.success(dictDataVos);
    }

    /**
     * 获取字典数据
     * @param meterReadingWorkVo
     * @return
     */
    @GetMapping("/selectWaterNum")
    public AjaxResult selectDictData(MeterReadingWorkVo meterReadingWorkVo) {
        List<String> list = screenDataService.selectWaterNum(meterReadingWorkVo);
        return AjaxResult.success(list);
    }
}

