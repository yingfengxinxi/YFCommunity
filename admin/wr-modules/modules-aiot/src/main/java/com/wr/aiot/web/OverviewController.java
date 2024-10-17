package com.wr.aiot.web;

import com.alibaba.fastjson2.JSONObject;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.customize.model.GarageNodeSearch;
import com.wr.aiot.service.OverviewService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.annotation.RequiresPermissions;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: RainCity
 * @Date: 2022-11-01 16:05:50
 * @Desc: 停车场数据总览
 */
@RestController
@RequestMapping("/overview")
public class OverviewController extends BaseController {
    @Autowired
    private OverviewService overviewService;


    /**
     * 月度停车收费
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    // @RequiresPermissions("aiot:overview:query")
    @GetMapping(value = "/park/month")
    public AjaxResult parkMonth() {

        return AjaxResult.success(overviewService.selectMonthParkData());
    }

    /**
     * 日车辆进出数据
     * @param inoutTime 时间
     * @param monthType 0：上半月； 1：下半月
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    // @RequiresPermissions("aiot:overview:query")
    @GetMapping(value = "/vehicle")
    public AjaxResult vehicleLog(@RequestParam("inoutTime") String inoutTime, @RequestParam("monthType") String monthType) {
        if(StringUtils.isEmpty(inoutTime)){
            return AjaxResult.success();
        }
        return AjaxResult.success(overviewService.selectVehicleLogData(inoutTime, monthType));
    }

    /**
     * 车位占用统计
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    // @RequiresPermissions("aiot:overview:query")
    @GetMapping(value = "/carport")
    public AjaxResult carportUse() {

        return AjaxResult.success(overviewService.selectCarportUseData());
    }


    /**
     * 获取停车库列表
     */
    @ApiOperation("获取停车库列表")
    @GetMapping("/parkList")
    public AjaxResult parkList(@RequestParam(value = "communityId") Long communityId,
                               @RequestParam(value = "parkIndexCodes", required = false) String parkIndexCodes) {
        try {
            return AjaxResult.success(overviewService.parkList(communityId, parkIndexCodes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据停车场编码反控道闸
     */
    @ApiOperation("根据停车场编码反控道闸")
    @GetMapping("/deviceControlBatch")
    public AjaxResult deviceControlBatch(@RequestParam(value = "communityId") Long communityId,
                               @RequestParam(value = "parkSyscode") String parkSyscode,
                               @RequestParam(value = "command") Integer command) {
        try {
            return AjaxResult.state(overviewService.deviceControlBatch(communityId,parkSyscode, command));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据车道编码反控道闸
     */
    @ApiOperation("根据车道编码反控道闸")
    @GetMapping("/deviceControl")
    public AjaxResult deviceControl(@RequestParam(value = "communityId") Long communityId,
                                    @RequestParam(value = "roadwaySyscode") String roadwaySyscode,
                                    @RequestParam(value = "command") Integer command) {
        try {
            return AjaxResult.state(overviewService.deviceControl(communityId, roadwaySyscode, command));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    /**
     * 查询停车库节点信息
     */
    @ApiOperation("查询停车库节点信息")
    @PostMapping("/getGarageNode")
    public AjaxResult getGarageNode(@Valid @RequestBody GarageNodeSearch garageNodeSearch) {
        try {
            JSONObject garageNode = overviewService.getGarageNode(garageNodeSearch);
            return AjaxResult.success(String.valueOf(garageNode.get("msg")),garageNode.get("data"));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询出入口设备关联关系
     */
    @ApiOperation("查询出入口设备关联关系")
    @GetMapping("/deviceRelation")
    public AjaxResult deviceRelation(@RequestParam(value = "communityId") Long communityId,
                                     @RequestParam("syscode") String syscode,
                                     @RequestParam("nodeType") Integer nodeType,
                                     @RequestParam(value = "subNode", required = false) String subNode,
                                     @RequestParam(value = "deviceType", required = false) String deviceType) {
        try {
            JSONObject garageNode = overviewService.deviceRelation(communityId, syscode, nodeType, subNode, deviceType);
            return AjaxResult.success(String.valueOf(garageNode.get("msg")), garageNode.get("data"));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询资源列表
     */
    @ApiOperation("查询资源列表")
    @GetMapping("/resourcesByParams")
    public AjaxResult resourcesByParams(@RequestParam(value = "communityId") Long communityId,
                                        @RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "resourceType") String resourceType,
                                        @RequestParam(value = "pageNo") Integer pageNo,
                                        @RequestParam(value = "pageSize") Integer pageSize) {
        try {
            JSONObject garageNode = overviewService.resourcesByParams(communityId, name,resourceType, pageNo, pageSize);
            return AjaxResult.success(String.valueOf(garageNode.get("msg")), garageNode.get("data"));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

}
