package com.wr.estate.web.customer.cleaningWorkOrder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.customer.cleaningWorkOrder.CleaningWorkOrderSettingService;
import com.wr.remote.domain.CleaningWorkOrderSetting;
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
@RequestMapping("cleaningWorkOrderSetting")
@Api(tags = "物管平台-清洁工单设置")
public class cleaningWorkOrderSettingController extends BaseController {

    @Autowired
    private CleaningWorkOrderSettingService cleaningWorkOrderSettingService;


    @ApiOperation("清洁工单设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "cleaningName", value = "巡查名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "cleaningStatus", value = "巡查状态0=关闭1=开启", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "executorBy", value = "执行人", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    @GetMapping("list")
    public TableDataInfo list(CleaningWorkOrderSetting cleaningWorkOrderSetting) {
        LambdaQueryWrapper<CleaningWorkOrderSetting> queryWrapper = getInspectionSettingLambdaQueryWrapper(cleaningWorkOrderSetting);
        startPage();
        queryWrapper.orderByDesc(CleaningWorkOrderSetting::getCreateTime);
        return getDataTable(cleaningWorkOrderSettingService.list(queryWrapper));
    }

    @NotNull
    private static LambdaQueryWrapper<CleaningWorkOrderSetting> getInspectionSettingLambdaQueryWrapper(CleaningWorkOrderSetting cleaningWorkOrderSetting) {
        LambdaQueryWrapper<CleaningWorkOrderSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CleaningWorkOrderSetting::getCommunityId, cleaningWorkOrderSetting.getCommunityId());
        if (StringUtils.isNotEmpty(cleaningWorkOrderSetting.getCleaningName())) {
            queryWrapper.like(CleaningWorkOrderSetting::getCleaningName, cleaningWorkOrderSetting.getCleaningName());
        }
        if (StringUtils.isNotEmpty(cleaningWorkOrderSetting.getCleaningStatus())) {
            queryWrapper.eq(CleaningWorkOrderSetting::getCleaningStatus, cleaningWorkOrderSetting.getCleaningStatus());
        }
        if (StringUtils.isNotEmpty(cleaningWorkOrderSetting.getExecutorBy())) {
            queryWrapper.apply("REGEXP_LIKE(executor_by,'(^|,)" + cleaningWorkOrderSetting.getExecutorBy() + "($|,)')");
        }
        return queryWrapper;
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("清洁工单设置详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        return AjaxResult.success(cleaningWorkOrderSettingService.getById(id));
    }

    /**
     * @param cleaningWorkOrderSetting
     * @return
     */
    @ApiOperation("清洁工单设置增加")
    @PostMapping("save")
    public AjaxResult save(@RequestBody CleaningWorkOrderSetting cleaningWorkOrderSetting) {

//        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            cleaningWorkOrderSetting.setFirstExecutionTime(sim.parse("2024-03-22 15:25:00"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }

        cleaningWorkOrderSetting.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        cleaningWorkOrderSetting.setCreateBy(SecurityUtils.getUsername());
        cleaningWorkOrderSetting.setCreateTime(new Date());
        cleaningWorkOrderSetting.setCleaningStatus("1");
        cleaningWorkOrderSettingService.save(cleaningWorkOrderSetting);
        try {
            if (cleaningWorkOrderSetting.getCleaningStatus().equals("1")) {
                cleaningWorkOrderSettingService.saveRedisData(cleaningWorkOrderSetting, "0");
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }

    /**
     * @param cleaningWorkOrderSetting
     * @return
     */
    @ApiOperation("工单设置修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody CleaningWorkOrderSetting cleaningWorkOrderSetting) {
        cleaningWorkOrderSetting.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        cleaningWorkOrderSetting.setUpdateBy(SecurityUtils.getUsername());
        cleaningWorkOrderSetting.setUpdateTime(new Date());
        cleaningWorkOrderSettingService.updateById(cleaningWorkOrderSetting);
        return AjaxResult.success();
    }


    @GetMapping("getTime")
    public AjaxResult getTime(@RequestParam("firstExecutionTime") Date firstExecutionTime, @RequestParam("frequency") String frequency, @RequestParam("executionNumber") Integer executionNumber) {

        try {
            StringBuilder sb = new StringBuilder();
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < 12; i++) {
                Date date = cleaningWorkOrderSettingService.extractedDate(firstExecutionTime, frequency, executionNumber);
                firstExecutionTime = date;
                String format = sim.format(firstExecutionTime);
                sb.append((i + 1) + "、" + format + "&");
            }

            return AjaxResult.success(sb.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param id
     * @return
     */
    @ApiOperation("工单设置删除")
    @DeleteMapping("removeById")
    public AjaxResult removeById(@RequestParam("id") String id) {

        CleaningWorkOrderSetting cleaningWorkOrderSetting = cleaningWorkOrderSettingService.getById(id);

        cleaningWorkOrderSettingService.removeById(id);
        //删除redis中工单
        try {
            cleaningWorkOrderSettingService.saveRedisData(cleaningWorkOrderSetting, "1");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success();
    }
}
