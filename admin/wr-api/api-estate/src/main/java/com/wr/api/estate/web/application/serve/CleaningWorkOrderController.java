package com.wr.api.estate.web.application.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.manage.StaffService;
import com.wr.api.estate.service.application.serve.CleaningWorkOrderService;
import com.wr.api.estate.service.application.serve.InspectionService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.CleaningWorkOrder;
import com.wr.remote.domain.Inspection;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.manage.Staff;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:51
 */
@RestController
@RequestMapping("/v2/cleaningWorkOrder")
@Api(tags = "物业移动端-清洁工单")
public class CleaningWorkOrderController extends BaseController {

    @Autowired
    private CleaningWorkOrderService cleaningWorkOrderService;

    @Autowired
    private LoginService loginService;


    @GetMapping("list")
    @ApiOperation("清洁工单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(CleaningWorkOrder cleaningWorkOrder) {
        LambdaQueryWrapper<CleaningWorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CleaningWorkOrder::getCommunityId, cleaningWorkOrder.getCommunityId());
        SysUser sysUser = loginService.getByUserIdUser(cleaningWorkOrder.getName());
        String userType = sysUser.getUserType();
        if (!userType.equals("10")) {
            queryWrapper.eq(CleaningWorkOrder::getName, cleaningWorkOrder.getName());
        }

        if (StringUtils.isNotEmpty(cleaningWorkOrder.getCleaningStatus())) {
            queryWrapper.eq(CleaningWorkOrder::getCleaningStatus, cleaningWorkOrder.getCleaningStatus());
        }

        queryWrapper.orderByDesc(CleaningWorkOrder::getCreateTime);
        startPage();
        return getDataTable(cleaningWorkOrderService.list(queryWrapper));
    }

    /**
     * @param cleaningWorkOrder
     * @return
     */
    @ApiOperation("清洁工单提交")
    @PostMapping("save")
    public AjaxResult save(@RequestBody CleaningWorkOrder cleaningWorkOrder) {
        cleaningWorkOrderService.add(cleaningWorkOrder);
        return AjaxResult.success();
    }

    /**
     * @return
     */
    @ApiOperation("清洁工单详情")
    @GetMapping("get")
    public AjaxResult get(@RequestParam("id") String id) {

        return AjaxResult.success(cleaningWorkOrderService.get(id));
    }
}
