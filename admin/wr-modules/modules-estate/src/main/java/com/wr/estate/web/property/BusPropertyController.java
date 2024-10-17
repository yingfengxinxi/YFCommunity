package com.wr.estate.web.property;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusQuestionnaire;
import com.wr.estate.service.property.BusPropertyRecordService;
import com.wr.estate.service.property.BusPropertyService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.property.Property;
import com.wr.remote.estate.property.PropertyRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author lvzy
 * @Date 2024/1/17 11:09
 * 资产管理
 */
@RestController
@RequestMapping("property")
@Api(tags = "物管平台-资产管理")
public class BusPropertyController extends BaseController {

    @Autowired
    private BusPropertyService busPropertyService;

    @Autowired
    private BusPropertyRecordService busPropertyRecordService;

    /**
     * @param property
     * @return
     */
    @ApiOperation("资产管理列表")
    @GetMapping("list")
    public TableDataInfo list(Property property) {

        LambdaQueryWrapper<Property> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Property::getType, property.getType());
        queryWrapper.eq(Property::getEstateId,SecurityUtils.getLoginUser().getSysUser().getEstateId());

        if (StringUtils.isNotEmpty(property.getName())) {
            queryWrapper.like(Property::getName, property.getName());
        }
        if (StringUtils.isNotEmpty(property.getAssetType())) {
            queryWrapper.eq(Property::getAssetType, property.getAssetType());
        }
        if (property.getCommunityId() != null) {
            queryWrapper.eq(Property::getCommunityId, property.getCommunityId());
        }
        queryWrapper.orderByDesc(Property::getCreateTime);
        startPage();
        List<Property> list = busPropertyService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("资产管理详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {

        return AjaxResult.success(busPropertyService.getById(id));
    }

    /**
     * @param property
     * @return
     */
    @ApiOperation("资产管理新增")
    @PostMapping("add")
    public AjaxResult add(@RequestBody Property property) {

        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        property.setEstateId(sysUser.getEstateId());
        property.setCreateTime(new Date());
        property.setCreateBy(sysUser.getUserName());
        property.setDelFlag("0");
        busPropertyService.add(property);
        return AjaxResult.success();
    }

    /**
     * @param property
     * @return
     */
    @ApiOperation("资产管理修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody Property property) {

        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        property.setEstateId(sysUser.getEstateId());
        property.setUpdateTime(new Date());
        property.setUpdateBy(sysUser.getUserName());
        busPropertyService.update(property);
        return AjaxResult.success();
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("资产管理删除")
    @DeleteMapping("remove")
    public AjaxResult remove(@RequestParam("id") String id) {

        busPropertyService.removeById(id);
        return AjaxResult.success();
    }

    /**
     * @param propertyRecord
     * @return
     */
    @ApiOperation("资产管理借用物品人员列表")
    @GetMapping("recordlist")
    public TableDataInfo recordlist(PropertyRecord propertyRecord) {
        LambdaQueryWrapper<PropertyRecord> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(propertyRecord.getName())) {
            queryWrapper.like(PropertyRecord::getName, propertyRecord.getName());
        }
        if (StringUtils.isNotEmpty(propertyRecord.getPhone())) {
            queryWrapper.like(PropertyRecord::getPhone, propertyRecord.getPhone());
        }
        queryWrapper.eq(PropertyRecord::getPropertyId, propertyRecord.getPropertyId());
        queryWrapper.orderByDesc(PropertyRecord::getCreateTime);

        return getDataTable(busPropertyRecordService.list(queryWrapper));
    }


    /**
     * @param propertyRecord
     * @return
     */
    @ApiOperation("资产管理出库")
    @PostMapping("recordAdd")
    public AjaxResult add(@RequestBody PropertyRecord propertyRecord) {
        Property property = busPropertyService.getById(propertyRecord.getPropertyId());
        Integer number = property.getNumber();

        LambdaQueryWrapper<PropertyRecord> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(PropertyRecord::getPropertyId, propertyRecord.getPropertyId());
        queryWrapper1.eq(PropertyRecord::getStatus, "0");
        List<PropertyRecord> propertyRecordList = busPropertyRecordService.list(queryWrapper1);

        if (CollectionUtils.isNotEmpty(propertyRecordList)) {
            IntSummaryStatistics quantityUsed = propertyRecordList.stream().collect(Collectors.summarizingInt(PropertyRecord::getQuantityUsed));
            number = number - Integer.valueOf((int) quantityUsed.getSum());
        }
        Integer quantityUsed = propertyRecord.getQuantityUsed();

        if (number - quantityUsed < 0) {
            return AjaxResult.error("库存不足,当前剩余库存" + number + "(" + property.getSpecifications() + ")");
        }


        propertyRecord.setCreateTime(new Date());
        propertyRecord.setCreateBy(SecurityUtils.getUsername());
        busPropertyRecordService.save(propertyRecord);
        return AjaxResult.success();
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("资产管理修改出库入库状态")
    @GetMapping("updateStatus")
    public AjaxResult updateStatus(@RequestParam("id") String id) {
        PropertyRecord byId = busPropertyRecordService.getById(id);
        byId.setStatus("1");
        byId.setUpdateTime(new Date());
        byId.setReturnTime(new Date());
        byId.setUpdateBy(SecurityUtils.getUsername());
        busPropertyRecordService.updateById(byId);
        return AjaxResult.success();
    }
}
