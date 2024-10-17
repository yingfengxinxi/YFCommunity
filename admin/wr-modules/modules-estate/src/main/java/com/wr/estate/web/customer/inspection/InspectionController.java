package com.wr.estate.web.customer.inspection;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.entity.vo.BusVehicleImportVo;
import com.wr.estate.service.CommunityService;
import com.wr.estate.service.customer.inspection.InspectionService;
import com.wr.remote.domain.Inspection;
import com.wr.remote.estate.manage.Staff;
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
@RequestMapping("inspection")
@Api(tags = "物管平台-巡查工单")
public class InspectionController extends BaseController {

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private CommunityService communityService;


    @GetMapping("list")
    @ApiOperation("巡查工单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(Inspection inspection) {

        LambdaQueryWrapper<Inspection> queryWrapper = getInspectionLambdaQueryWrapper(inspection);
        startPage();
        return getDataTable(inspectionService.getList(queryWrapper));
    }

    @NotNull
    private static LambdaQueryWrapper<Inspection> getInspectionLambdaQueryWrapper(Inspection inspection) {
        LambdaQueryWrapper<Inspection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Inspection::getCommunityId, inspection.getCommunityId());
        if (StringUtils.isNotEmpty(inspection.getInspectionName())) {
            queryWrapper.like(Inspection::getInspectionName, inspection.getInspectionName());
        }
        if (StringUtils.isNotEmpty(inspection.getName())) {
            queryWrapper.eq(Inspection::getName, inspection.getName());
        }
        if (StringUtils.isNotEmpty(inspection.getInspectionStatus())) {
            queryWrapper.eq(Inspection::getInspectionStatus, inspection.getInspectionStatus());
        }
        if (StringUtils.isNotEmpty(inspection.getStartTime()) && StringUtils.isNotEmpty(inspection.getEndTime())) {
            queryWrapper.apply(" date_format(create_time,'%Y-%m-%d') between date_format('" + inspection.getStartTime() + "','%Y-%m-%d') and date_format('" + inspection.getEndTime() + "','%Y-%m-%d')");
        }
        queryWrapper.orderByDesc(Inspection::getCreateTime);
        return queryWrapper;
    }

    /**
     * @return
     */
    @ApiOperation("巡查工单详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {

        return AjaxResult.success(inspectionService.get(id));
    }

    @ApiOperation("巡查工单导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Inspection inspection) {
        LambdaQueryWrapper<Inspection> queryWrapper = getInspectionLambdaQueryWrapper(inspection);
        List<Inspection> list = inspectionService.getList(queryWrapper);

        ExcelUtil<Inspection> util = new ExcelUtil<>(Inspection.class);
        String communityName = communityService.getCommunityName(inspection.getCommunityId());
        util.exportExcel(response, list, communityName + "小区巡查工单");
    }

}
