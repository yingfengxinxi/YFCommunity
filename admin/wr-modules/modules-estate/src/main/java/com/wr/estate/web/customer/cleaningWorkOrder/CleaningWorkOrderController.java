package com.wr.estate.web.customer.cleaningWorkOrder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.CommunityService;
import com.wr.estate.service.customer.cleaningWorkOrder.CleaningWorkOrderService;
import com.wr.estate.service.customer.inspection.InspectionService;
import com.wr.remote.domain.CleaningWorkOrder;
import com.wr.remote.domain.Inspection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:46
 */
@RestController
@RequestMapping("cleaningWorkOrder")
@Api(tags = "物管平台-清洁工单")
public class CleaningWorkOrderController extends BaseController {

    @Autowired
    private CleaningWorkOrderService cleaningWorkOrderService;

    @Autowired
    private CommunityService communityService;


    @GetMapping("list")
    @ApiOperation("清洁工单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(CleaningWorkOrder cleaningWorkOrder) {

        LambdaQueryWrapper<CleaningWorkOrder> queryWrapper = getInspectionLambdaQueryWrapper(cleaningWorkOrder);
        startPage();
        return getDataTable(cleaningWorkOrderService.getList(queryWrapper));
    }

    @NotNull
    private static LambdaQueryWrapper<CleaningWorkOrder> getInspectionLambdaQueryWrapper(CleaningWorkOrder cleaningWorkOrder) {
        LambdaQueryWrapper<CleaningWorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CleaningWorkOrder::getCommunityId, cleaningWorkOrder.getCommunityId());
        if (StringUtils.isNotEmpty(cleaningWorkOrder.getCleaningName())) {
            queryWrapper.like(CleaningWorkOrder::getCleaningName, cleaningWorkOrder.getCleaningName());
        }
        if (StringUtils.isNotEmpty(cleaningWorkOrder.getName())) {
            queryWrapper.eq(CleaningWorkOrder::getName, cleaningWorkOrder.getName());
        }
        if (StringUtils.isNotEmpty(cleaningWorkOrder.getCleaningStatus())) {
            queryWrapper.eq(CleaningWorkOrder::getCleaningStatus, cleaningWorkOrder.getCleaningStatus());
        }
        if (StringUtils.isNotEmpty(cleaningWorkOrder.getStartTime()) && StringUtils.isNotEmpty(cleaningWorkOrder.getEndTime())) {
            queryWrapper.apply(" date_format(create_time,'%Y-%m-%d') between date_format('" + cleaningWorkOrder.getStartTime() + "','%Y-%m-%d') and date_format('" + cleaningWorkOrder.getEndTime() + "','%Y-%m-%d')");
        }
        queryWrapper.orderByDesc(CleaningWorkOrder::getCreateTime);
        return queryWrapper;
    }

    /**
     * @return
     */
    @ApiOperation("清洁工单详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {

        return AjaxResult.success(cleaningWorkOrderService.get(id));
    }

    @ApiOperation("巡查工单导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CleaningWorkOrder cleaningWorkOrder) {
        LambdaQueryWrapper<CleaningWorkOrder> queryWrapper = getInspectionLambdaQueryWrapper(cleaningWorkOrder);
        List<CleaningWorkOrder> list = cleaningWorkOrderService.getList(queryWrapper);

        ExcelUtil<CleaningWorkOrder> util = new ExcelUtil<>(CleaningWorkOrder.class);
        String communityName = communityService.getCommunityName(cleaningWorkOrder.getCommunityId());
        util.exportExcel(response, list, communityName + "小区清洁工单");
    }

}
