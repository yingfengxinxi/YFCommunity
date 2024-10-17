package com.wr.aiot.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.aiot.service.VisitingRecordService;
import com.wr.aiot.service.VisitingSecordService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.aiot.VisitingRecord;
import com.wr.remote.aiot.VisitingSecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/10 17:05
 */
@RestController
@RequestMapping("visitingRecord")
@Api(tags = "来访记录")
public class VisitingRecordController  extends BaseController {


    @Autowired
    private VisitingRecordService visitingRecordService;



    @ApiOperation("列表")
    @GetMapping("/list")
    public TableDataInfo list(VisitingRecord visitingRecord) {
        LambdaQueryWrapper<VisitingRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VisitingRecord::getCommunityId, visitingRecord.getCommunityId());

        if (StringUtils.isNotEmpty(visitingRecord.getVisitorName())) {
            queryWrapper.like(VisitingRecord::getVisitorName, visitingRecord.getVisitorName());
        }
        if (StringUtils.isNotEmpty(visitingRecord.getPhoneNo())) {
            queryWrapper.like(VisitingRecord::getPhoneNo, visitingRecord.getPhoneNo());
        }
        if (StringUtils.isNotEmpty(visitingRecord.getPlateNo())) {
            queryWrapper.like(VisitingRecord::getPlateNo, visitingRecord.getPlateNo());
        }
        if (StringUtils.isNotEmpty(visitingRecord.getVisitorStatus())) {
            queryWrapper.eq(VisitingRecord::getVisitorStatus, visitingRecord.getVisitorStatus());
        }
        if (StringUtils.isNotEmpty(visitingRecord.getReceptionistName())) {
            queryWrapper.like(VisitingRecord::getReceptionistName, visitingRecord.getReceptionistName());
        }
        if (StringUtils.isNotEmpty(visitingRecord.getReceptionistPhoneNo())) {
            queryWrapper.like(VisitingRecord::getReceptionistPhoneNo, visitingRecord.getReceptionistPhoneNo());
        }

        if (StringUtils.isNotEmpty(visitingRecord.getVisitStartStartDate())) {
            queryWrapper.apply(" DATE_FORMAT(visit_start_time,'%Y-%m-%d') BETWEEN '" + visitingRecord.getVisitStartStartDate() + "' AND '" + visitingRecord.getVisitStartEndDate() + "' ");
        }

        if (StringUtils.isNotEmpty(visitingRecord.getVisitEndStartDate())) {
            queryWrapper.apply(" DATE_FORMAT(visit_end_time,'%Y-%m-%d') BETWEEN '" + visitingRecord.getVisitEndStartDate() + "' AND '" + visitingRecord.getVisitEndEndDate() + "' ");
        }

        startPage();
        queryWrapper.orderByDesc(VisitingRecord::getVisitStartTime);
        List<VisitingRecord> list = visitingRecordService.list(queryWrapper);
        return getDataTable(list);
    }



    /**
     * @param id
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        //
        return AjaxResult.success(visitingRecordService.getById(id));
    }


    @ApiOperation("初始化数据")
    @GetMapping("visitingRecord")
    public AjaxResult doorEvents() {
        try {
            visitingRecordService.visitingRecord("0");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }
}
