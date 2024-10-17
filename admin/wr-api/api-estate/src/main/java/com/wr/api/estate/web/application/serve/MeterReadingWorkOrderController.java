package com.wr.api.estate.web.application.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.serve.MeterReadingWorkOrderService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.MeterReadingWorkOrder;
import com.wr.remote.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/3/5 13:47
 */
@RestController
@RequestMapping("v2/meterReading")
@Api(tags = "物业移动端-抄表工单")
public class MeterReadingWorkOrderController extends BaseController {

    @Autowired
    private MeterReadingWorkOrderService meterReadingWorkOrderService;


    @GetMapping("list")
    @ApiOperation("抄表工单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "meterReadingStatus", value = "0=待上报1=已完成2=已超期", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "meterReadingType", value = "抄表类型", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(MeterReadingWorkOrder meterReadingWorkOrder) {

        startPage();
        return getDataTable(meterReadingWorkOrderService.getList(meterReadingWorkOrder));
    }

    @ApiOperation("校验工单是否可以处理")
    @GetMapping("getByIdIsRead")
    public AjaxResult getByIdIsRead(@RequestParam("id") String id) {
        MeterReadingWorkOrder meterReadingWorkOrder = meterReadingWorkOrderService.getById(id);
        LambdaQueryWrapper<MeterReadingWorkOrder> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(MeterReadingWorkOrder::getEnergyConsumptionType, meterReadingWorkOrder.getEnergyConsumptionType());
        queryWrapper1.eq(MeterReadingWorkOrder::getMeterReadingType, meterReadingWorkOrder.getMeterReadingType());
        queryWrapper1.eq(MeterReadingWorkOrder::getCommunityId, meterReadingWorkOrder.getCommunityId());
        //queryWrapper1.apply(" id!='" + meterReadingWorkOrder.getId() + "'");
        List<String> statusList = Arrays.asList("0,2".split(","));
        queryWrapper1.in(MeterReadingWorkOrder::getMeterReadingStatus, statusList);
        queryWrapper1.orderByAsc(MeterReadingWorkOrder::getCreateTime);
        List<MeterReadingWorkOrder> workOrders = meterReadingWorkOrderService.list(queryWrapper1);
        List<String> idList = workOrders.stream().map(MeterReadingWorkOrder::getId).collect(Collectors.toList());
        int i = idList.indexOf(id);
        if (i >= 1) {
            return AjaxResult.success(1);
        }
        return AjaxResult.success(0);
    }





    @GetMapping("getReportedCount")
    @ApiOperation("抄表待上报数量统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "meterReadingStatus", value = "0=待上报1=已完成2=已超期", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "meterReadingType", value = "抄表类型", dataType = "String", required = false, paramType = "query"),
    })
    public AjaxResult getReportedCount(MeterReadingWorkOrder meterReadingWorkOrder) {

        return AjaxResult.success(meterReadingWorkOrderService.getList(meterReadingWorkOrder).size());
    }

    /**
     * @param meterReadingWorkOrder
     * @return
     */
    @ApiOperation("抄表工单提交")
    @PostMapping("save")
    public AjaxResult save(@RequestBody MeterReadingWorkOrder meterReadingWorkOrder) {
        if(CollectionUtils.isEmpty(meterReadingWorkOrder.getRecordWorkOrderList())){

            return AjaxResult.error("请填写设备能耗总量");
        }
        meterReadingWorkOrderService.saveMeterReadingWorkOrder(meterReadingWorkOrder);
        return AjaxResult.success();
    }

    /**
     * @return
     */
    @ApiOperation("抄表工单详情")
    @GetMapping("get")
    public AjaxResult get(@RequestParam("id") String id) {

        return AjaxResult.success(meterReadingWorkOrderService.get(id));
    }

    /**
     * @param communityId
     * @param energyConsumptionType
     * @param meterReadingType
     * @return
     */
    @ApiOperation("抄表工单")
    @GetMapping("getMeterReadingEquipmentList")
    public AjaxResult getMeterReadingEquipmentList(@RequestParam("communityId") Long communityId, @RequestParam("energyConsumptionType") String energyConsumptionType, @RequestParam("meterReadingType") String meterReadingType) {

        return AjaxResult.success(meterReadingWorkOrderService.meterReadingEquipmentList(communityId, energyConsumptionType, meterReadingType));
    }

}
