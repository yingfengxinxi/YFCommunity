package com.wr.api.estate.web.application.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.manage.StaffService;
import com.wr.api.estate.service.application.serve.PatrolWorkOrderService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.*;
import com.wr.remote.estate.manage.Staff;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/2 14:51
 */
@RestController
@RequestMapping("v2/patrolWorkOrder")
@Api(tags = "物业移动端-巡检工单")
public class PatrolWorkOrderController extends BaseController {

    @Autowired
    private PatrolWorkOrderService patrolWorkOrderService;

    @Autowired
    private LoginService loginService;

    @GetMapping("list")
    @ApiOperation("巡检工单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "patrolStatus", value = "0=待上报1=已完成2=已超期", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "facilitieEquipmentType", value = "设备类型", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(PatrolWorkOrder patrolWorkOrder) {

        extracted(patrolWorkOrder);
        startPage();
        return getDataTable(patrolWorkOrderService.getList(patrolWorkOrder));
    }

    private void extracted(PatrolWorkOrder patrolWorkOrder) {
        SysUser sysUser = loginService.getByUserIdUser(patrolWorkOrder.getName());
        String userType = sysUser.getUserType();
        if (!userType.equals("10")) {
            patrolWorkOrder.setName(patrolWorkOrder.getName());
        } else {
            patrolWorkOrder.setName("");
        }
    }


    @GetMapping("getReportedCount")
    @ApiOperation("巡检待上报数量统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "patrolStatus", value = "0=待上报1=已完成2=已超期", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "facilitieEquipmentType", value = "设备类型", dataType = "Long", required = false, paramType = "query"),
    })
    public AjaxResult getReportedCount(PatrolWorkOrder patrolWorkOrder) {
        extracted(patrolWorkOrder);
        patrolWorkOrder.setPatrolStatus("0");
        return AjaxResult.success(patrolWorkOrderService.getList(patrolWorkOrder).size());
    }

    /**
     * @param patrolWorkOrder
     * @return
     */
    @ApiOperation("巡检工单提交")
    @PostMapping("patrolWorkOrderAdd")
    public AjaxResult save(@RequestBody PatrolWorkOrder patrolWorkOrder) {
        patrolWorkOrderService.savePatrolWorkOrder(patrolWorkOrder);
        return AjaxResult.success();
    }


    @ApiOperation("巡检工单修改巡检内容")
    @PostMapping("updatePatrolContent")
    public AjaxResult updatePatrolContent(@RequestBody PatrolWorkOrder patrolWorkOrder) {
        patrolWorkOrderService.updatePatrolContent(patrolWorkOrder);
        return AjaxResult.success();
    }

    /**
     * @return
     */
    @ApiOperation("巡检工单详情")
    @GetMapping("get")
    public AjaxResult get(@RequestParam("id") String id) {

        return AjaxResult.success(patrolWorkOrderService.get(id));
    }
}
