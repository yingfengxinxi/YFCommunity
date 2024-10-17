package com.wr.api.estate.web.application.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.manage.StaffService;
import com.wr.api.estate.service.application.serve.InspectionService;
import com.wr.api.estate.service.application.serve.ReportingWarrantyService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.Inspection;
import com.wr.remote.domain.ReportingWarranty;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.manage.Staff;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:51
 */
@RestController
@RequestMapping("/v2/inspection")
@Api(tags = "物业移动端-巡查工单")
public class InspectionController extends BaseController {

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private LoginService loginService;


    @GetMapping("list")
    @ApiOperation("巡查工单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(Inspection inspection) {
        LambdaQueryWrapper<Inspection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inspection::getCommunityId, inspection.getCommunityId());
        SysUser sysUser = loginService.getByUserIdUser(inspection.getName());
        String userType = sysUser.getUserType();
        if (!userType.equals("10")) {
            queryWrapper.eq(Inspection::getName, inspection.getName());
        }

        if (StringUtils.isNotEmpty(inspection.getInspectionStatus())) {
            queryWrapper.eq(Inspection::getInspectionStatus, inspection.getInspectionStatus());
        }

        queryWrapper.orderByDesc(Inspection::getCreateTime);
        startPage();
        return getDataTable(inspectionService.list(queryWrapper));
    }


    @GetMapping("getReportedCount")
    @ApiOperation("巡查待上报数量统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
    })
    public AjaxResult getReportedCount(Inspection inspection) {
        LambdaQueryWrapper<Inspection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inspection::getCommunityId, inspection.getCommunityId());
        SysUser sysUser = loginService.getByUserIdUser(inspection.getName());
        String userType = sysUser.getUserType();
        if (!userType.equals("10")) {
            queryWrapper.eq(Inspection::getName,inspection.getName());
        }
        queryWrapper.eq(Inspection::getInspectionStatus, "0");
        queryWrapper.orderByDesc(Inspection::getCreateTime);
        return AjaxResult.success(inspectionService.list(queryWrapper).size());
    }

    /**
     * @param inspection
     * @return
     */
    @ApiOperation("巡查工单提交")
    @PostMapping("save")
    public AjaxResult save(@RequestBody Inspection inspection) {
        inspectionService.add(inspection);
        return AjaxResult.success();
    }

    /**
     * @return
     */
    @ApiOperation("巡查工单详情")
    @GetMapping("get")
    public AjaxResult get(@RequestParam("id") String id) {

        return AjaxResult.success(inspectionService.get(id));
    }
}
