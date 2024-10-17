package com.wr.api.estate.web.application.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.api.estate.service.application.serve.LocationService;
import com.wr.api.estate.service.application.serve.PatrolWorkOrderService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.DeviceConstants;
import com.wr.remote.domain.FacilitieEquipment;
import com.wr.remote.domain.PatrolWorkOrder;
import com.wr.remote.domain.vo.PatrolWorkOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 11:41:39
 * @Desc: 资产定位控制层
 */
@RestController
@RequestMapping("/v2/location")
@Api(tags = "物业移动端-资产定位")
public class LocationController extends BaseController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private PatrolWorkOrderService patrolWorkOrderService;

    @ApiOperation("资产定位详情")
    @GetMapping("/getFacilitieById")
    public AjaxResult getFacilitieById(@RequestParam("id") String id) {
        FacilitieEquipment facilitieEquipment = locationService.getFacilitieById(id);
        LambdaQueryWrapper<PatrolWorkOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PatrolWorkOrder::getFacilitieEquipmentId, id);
        queryWrapper.eq(PatrolWorkOrder::getPatrolStatus, "1");
        List<PatrolWorkOrder> list = patrolWorkOrderService.list(queryWrapper);
        List<PatrolWorkOrderVo> patrolWorkOrderList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(patrolWorkOrder -> {
                PatrolWorkOrderVo patrolWorkOrderVo = new PatrolWorkOrderVo();
                patrolWorkOrderVo.setId(patrolWorkOrder.getId());
                patrolWorkOrderVo.setNo(patrolWorkOrder.getNo());
                patrolWorkOrderVo.setFinalInspectionTime(patrolWorkOrder.getCreateTime());
                patrolWorkOrderList.add(patrolWorkOrderVo);
            });
        }
        List<PatrolWorkOrderVo> patrolWorkOrders = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(patrolWorkOrderList)) {
            Comparator<PatrolWorkOrderVo> comparator3 =
                    Comparator.comparing(PatrolWorkOrderVo::getFinalInspectionTime).reversed();
            patrolWorkOrders = patrolWorkOrderList.stream().sorted(comparator3).collect(Collectors.toList());
        }
        facilitieEquipment.setPatrolWorkOrderList(patrolWorkOrders);
        return AjaxResult.success(facilitieEquipment);
    }

    /**
     * 获取设备名称
     *
     * @param indexCode 设备唯一标识
     * @param type      设备类型
     * @return
     */
    @AuthRequired
    @GetMapping("/list")
    public AjaxResult getLocation(String indexCode, String type) {
        Map<String, Object> resultMap = locationService.getDeviceName(indexCode, type);
        return AjaxResult.success(resultMap);
    }

    /**
     * 更改设备地址
     *
     * @param indexCode 设备唯一标识
     * @param type      设备类型
     * @param address   更改地址
     * @return
     */
    @AuthRequired
    @GetMapping
    public AjaxResult updateAddress(String indexCode, String type, String address) {
        return toAjax(locationService.updateAddress(indexCode, type, address));
    }
}
