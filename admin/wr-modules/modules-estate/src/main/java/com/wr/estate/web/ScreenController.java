package com.wr.estate.web;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.entity.screen.DictDataVo;
import com.wr.estate.entity.screen.MeterReadingWorkVo;
import com.wr.estate.entity.screen.PatrolOrderScreen;
import com.wr.estate.service.ScreenService;
import com.wr.remote.aiot.WarnEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @Author: GuanYu
 * @Date: 2021-09-26 11:35:13
 * @Desc: 物业端智慧社区大屏
 */
@RestController

@RequestMapping("/screen")
public class ScreenController extends BaseController {
    @Autowired
    private ScreenService screenService;

    /**
     * 访客统计
     * @param communityId
     * @return
     */
    @GetMapping("/getVisitLogCount")
    public AjaxResult selectVisitLogCount(@RequestParam("communityId") Long communityId){
        return screenService.selectVisitLogCount(communityId);
    }

    /**
     * 根据日月年查询访客数
     * @param communityId
     * @return
     */
    @GetMapping("/getDmyVisitLogCount")
    public AjaxResult getDmyVisitLogCount(@RequestParam("communityId") Long communityId){

        return screenService.getDmyVisitLogCount(communityId);
    }

    /**
     * 设备在线离线数量统计
     *
     * @param communityId
     * @return
     */
    @GetMapping("/getEquipmentCount")
    public AjaxResult selectEquipmentCount(@RequestParam("communityId") Long communityId){
        return AjaxResult.success(screenService.selectEquipmentCount(communityId));
    }

    /**
     * 查询业主年龄
     *
     * @param communityId
     * @return
     */
    @GetMapping("/getOwnerAge")
    public AjaxResult selectOwnerAge(@RequestParam("communityId") Long communityId){
        return AjaxResult.success(screenService.selectOwnerAge(communityId));
    }

    /**
     * 小区工单
     *
     * @param communityId
     * @return
     */
    @GetMapping("/getPatrolOrder")
    public AjaxResult selectPatrolOrder(@RequestParam("communityId") Long communityId){
        List<PatrolOrderScreen> patrolOrderScreens = screenService.selectPatrolOrder(communityId);
        return AjaxResult.success(patrolOrderScreens);
    }

    /**
     * 员工数据统计
     * @param deptId
     * @return
     */
    @GetMapping("/getStaffCount")
    public AjaxResult selectStaffCount(@RequestParam(value = "deptId",required = false,defaultValue = "") Long deptId){
        return AjaxResult.success(screenService.selectStaffCount(deptId));
    }

    /**
     * 出入详情数据饼状图
     * @param communityId
     * @return
     */
    @GetMapping("/getPeopleInAndOutChart")
    public AjaxResult selectPeopleInAndOutChart(@RequestParam("communityId") Long communityId){
        return AjaxResult.success(screenService.selectPeopleInAndOutChart(communityId));
    }

    /**
     * 出入详情数据人行折线图
     * @param communityId
     * @return
     */
    @GetMapping("/getVisitCharts")
    public AjaxResult selectVisitCharts(@RequestParam("communityId") Long communityId){
        return screenService.selectVisitCharts(communityId);
    }

    /**
     * 进出场
     * @param communityId
     * @return
     */
    @GetMapping("/getInAndOut")
    public AjaxResult selectInAndOut(@RequestParam("communityId") Long communityId){
        return screenService.selectInAndOut(communityId);
    }

    /**
     * 评价数据
     * @param communityId
     * @return
     */
    @GetMapping("/getEvaluate")
    public AjaxResult selectPraise(@RequestParam("communityId") Long communityId){
        return screenService.selectPraise(communityId);
    }

    /**
     * 查询预警
     * @return
     */
    @GetMapping("/listWaring")
    public AjaxResult selectEarlyWarning(){
        List<WarnEvent> events = screenService.selectWarnEventList();
        return AjaxResult.success(events);
    }

    /**
     * 查询平台公告
     * @return
     */
    @GetMapping("/noticeTitle")
    public AjaxResult selectNoticeTitle(){

        return AjaxResult.success(screenService.selectNoticeTitle());
    }

    /**
     * 获取字典数据
     * @param dictDataVo
     * @return
     */
    @GetMapping("/dictData")
    public AjaxResult selectDictData(DictDataVo dictDataVo) {
        List<DictDataVo> dictDataVos = screenService.selectDictData(dictDataVo);
        return AjaxResult.success(dictDataVos);
    }

    /**
     * 获取字典数据
     * @param meterReadingWorkVo
     * @return
     */
    @GetMapping("/selectWaterNum")
    public AjaxResult selectDictData(MeterReadingWorkVo meterReadingWorkVo) {
        List<String> list = screenService.selectWaterNum(meterReadingWorkVo);
        return AjaxResult.success(list);
    }

}
