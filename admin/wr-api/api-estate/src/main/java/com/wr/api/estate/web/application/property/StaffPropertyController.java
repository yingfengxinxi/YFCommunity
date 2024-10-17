package com.wr.api.estate.web.application.property;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.estate.service.application.property.StaffPropertyService;
import com.wr.api.estate.service.application.serve.CarportNoService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.CarportNo;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.property.StaffProperty;
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
 * @Date 2024/1/19 20:02
 */
@RestController
@RequestMapping("v2/staffProperty")
@Api(tags = "物业移动端-物业人员个人资产认证")
public class StaffPropertyController extends BaseController {

    @Autowired
    private StaffPropertyService staffPropertyService;

    @Autowired
    private CarportNoService carportNoService;


    /**
     * @param staffProperty
     * @return
     */
    @GetMapping("list")
    @ApiOperation("物业人员个人资产认证列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当登录用id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(StaffProperty staffProperty) {
        LambdaQueryWrapper<StaffProperty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StaffProperty::getDelFlag, "0");
        queryWrapper.eq(StaffProperty::getUserId, staffProperty.getUserId());
        queryWrapper.orderByDesc(StaffProperty::getCreateTime);
        startPage();
        return getDataTable(staffPropertyService.list(queryWrapper));
    }

    @ApiOperation("车牌下拉")
    @GetMapping("getList")
    public AjaxResult getList(StaffProperty staffProperty) {
        LambdaQueryWrapper<StaffProperty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StaffProperty::getDelFlag, "0");
        queryWrapper.eq(StaffProperty::getUserId, staffProperty.getUserId());
        queryWrapper.eq(StaffProperty::getAuditStatus, "1");
        queryWrapper.orderByDesc(StaffProperty::getCreateTime);
        List<StaffProperty> list = staffPropertyService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(sp -> {
                String vehicleNo = sp.getVehicleNo();
                CarportNo carportNo = carportNoService.get(Long.valueOf(vehicleNo));
                if (carportNo != null) {
                    sp.setVehicleNo(carportNo.getCarportNo());
                }
                String carportNature = carportNoService.getCarportNoCarportNature(vehicleNo);
                if (StringUtils.isNotEmpty(carportNature)) {
                    sp.setCarportNature(carportNature);
                }
            });
        }
        return AjaxResult.success(list);
    }

    /**
     * @param staffProperty
     * @return
     */
    @ApiOperation("物业人员个人资产认证提交")
    @PostMapping("add")
    public AjaxResult add(@RequestBody StaffProperty staffProperty) {
        LambdaQueryWrapper<StaffProperty> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StaffProperty::getPlateNo, staffProperty.getPlateNo());
        queryWrapper.eq(StaffProperty::getUserId, staffProperty.getUserId());
        List<StaffProperty> list = staffPropertyService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            long count = list.stream().filter(sp -> sp.getAuditStatus().equals("1")).count();
            if (count >= 1) {
                return AjaxResult.error("当前车牌号资产已认证");
            }
            if (list.size() > count) {
                staffProperty.setId(list.get(0).getId());
            }
        }
        staffProperty.setCreateTime(new Date());
        staffProperty.setAuditStatus("0");
        staffProperty.setDelFlag("0");
        staffProperty.setAuditTime(null);
        staffProperty.setVehicleStatus("0");
        staffProperty.setAuditBy(null);
        staffProperty.setUpdateBy(null);
        staffProperty.setRejectReason(null);
        staffProperty.setUpdateTime(null);
        if (StringUtils.isNotEmpty(staffProperty.getId())) {
            staffPropertyService.updateById(staffProperty);
            return AjaxResult.success();
        }
        staffPropertyService.save(staffProperty);
        return AjaxResult.success();
    }
}
