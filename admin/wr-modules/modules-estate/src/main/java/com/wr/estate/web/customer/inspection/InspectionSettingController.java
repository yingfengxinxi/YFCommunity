package com.wr.estate.web.customer.inspection;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.customer.inspection.InspectionSettingService;
import com.wr.remote.domain.InspectionSetting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:46
 */
@RestController
@RequestMapping("inspectionSetting")
@Api(tags = "物管平台-巡查工单设置")
public class InspectionSettingController extends BaseController {

    @Autowired
    private InspectionSettingService inspectionSettingService;

    @ApiOperation("巡查工单设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "inspectName", value = "巡查名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "inspectStatus", value = "巡查状态0=关闭1=开启", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "executorBy", value = "执行人", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    @GetMapping("list")
    public TableDataInfo list(InspectionSetting inspectionSetting) {
        LambdaQueryWrapper<InspectionSetting> queryWrapper = getInspectionSettingLambdaQueryWrapper(inspectionSetting);
        startPage();
        queryWrapper.orderByDesc(InspectionSetting::getCreateTime);
        return getDataTable(inspectionSettingService.list(queryWrapper));
    }

    @NotNull
    private static LambdaQueryWrapper<InspectionSetting> getInspectionSettingLambdaQueryWrapper(InspectionSetting inspectionSetting) {
        LambdaQueryWrapper<InspectionSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InspectionSetting::getCommunityId, inspectionSetting.getCommunityId());
        if (StringUtils.isNotEmpty(inspectionSetting.getInspectName())) {
            queryWrapper.like(InspectionSetting::getInspectName, inspectionSetting.getInspectName());
        }
        if (StringUtils.isNotEmpty(inspectionSetting.getInspectStatus())) {
            queryWrapper.eq(InspectionSetting::getInspectStatus, inspectionSetting.getInspectStatus());
        }
        if (StringUtils.isNotEmpty(inspectionSetting.getExecutorBy())) {
            queryWrapper.apply("REGEXP_LIKE(executor_by,'(^|,)" + inspectionSetting.getExecutorBy() + "($|,)')");
        }
        return queryWrapper;
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("工单设置详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        return AjaxResult.success(inspectionSettingService.getById(id));
    }

    /**
     * @param inspectionSetting
     * @return
     */
    @ApiOperation("工单设置增加")
    @PostMapping("save")
    public AjaxResult save(@RequestBody InspectionSetting inspectionSetting) {
//        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            inspectionSetting.setFirstExecutionTime(sim.parse("2024-03-22 15:20:00"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }

        inspectionSetting.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        inspectionSetting.setCreateBy(SecurityUtils.getUsername());
        inspectionSetting.setCreateTime(new Date());
        inspectionSetting.setInspectStatus("1");
        inspectionSettingService.save(inspectionSetting);
        try {
            if (inspectionSetting.getInspectStatus().equals("1")) {
                inspectionSettingService.saveRedisData(inspectionSetting, "0");
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }

    /**
     * @param inspectionSetting
     * @return
     */
    @ApiOperation("工单设置修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody InspectionSetting inspectionSetting) {
        inspectionSetting.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        inspectionSetting.setUpdateBy(SecurityUtils.getUsername());
        inspectionSetting.setUpdateTime(new Date());
        inspectionSettingService.updateById(inspectionSetting);
        return AjaxResult.success();
    }


    @GetMapping("getTime")
    public AjaxResult getTime(@RequestParam("firstExecutionTime") Date firstExecutionTime, @RequestParam("frequency") String frequency, @RequestParam("executionNumber") Integer executionNumber) {

        try {
            StringBuilder sb = new StringBuilder();
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < 12; i++) {
                Date date = inspectionSettingService.extractedDate(firstExecutionTime, frequency, executionNumber);
                firstExecutionTime = date;
                String format = sim.format(firstExecutionTime);
                sb.append((i + 1) + "、" + format + "&");
            }

            return AjaxResult.success(sb.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @ApiOperation("工单设置修改状态")
    @GetMapping("updateInspectStatus")
    public AjaxResult updateInspectStatus(@RequestParam("id") String id, @RequestParam("inspectStatus") String inspectStatus) {

        InspectionSetting inspectionSetting = inspectionSettingService.getById(id);
        inspectionSetting.setInspectStatus(inspectStatus);
        inspectionSettingService.updateById(inspectionSetting);
        try {
            if (inspectionSetting.getInspectStatus().equals("1")) {
                inspectionSettingService.saveRedisData(inspectionSetting, "0");
            }
            if (inspectionSetting.getInspectStatus().equals("0")) {
                inspectionSettingService.saveRedisData(inspectionSetting, "1");
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("工单设置删除")
    @DeleteMapping("removeById")
    public AjaxResult removeById(@RequestParam("id") String id) {

        InspectionSetting inspectionSetting = inspectionSettingService.getById(id);

        inspectionSettingService.removeById(id);
        //删除redis中工单
        try {
            inspectionSettingService.saveRedisData(inspectionSetting, "1");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }
}
