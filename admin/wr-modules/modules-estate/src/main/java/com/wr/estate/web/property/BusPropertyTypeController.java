package com.wr.estate.web.property;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.property.BusPropertyTypeService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.property.PropertyType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/17 11:09
 * 资产管理
 */
@RestController
@RequestMapping("propertyType")
@Api(tags = "物管平台-资产类型管理")
public class BusPropertyTypeController extends BaseController {

    @Autowired
    private BusPropertyTypeService propertyTypeService;

    /**
     * @param propertyType
     * @return
     */
    @ApiOperation("资产类型管理列表")
    @GetMapping("list")
    public TableDataInfo list(PropertyType propertyType) {
        LambdaQueryWrapper<PropertyType> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(propertyType.getName())) {
            queryWrapper.like(PropertyType::getName, propertyType.getName());
        }
        if (StringUtils.isNotEmpty(propertyType.getPropertyType())) {
            queryWrapper.eq(PropertyType::getPropertyType, propertyType.getPropertyType());
        }
        queryWrapper.eq(PropertyType::getDelFlag, "0");
        queryWrapper.orderByAsc(PropertyType::getSort);
        startPage();
        List<PropertyType> list = propertyTypeService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * @param propertyType
     * @return
     */
    @ApiOperation("资产类型管理下拉列表")
    @GetMapping("getByPropertyTypelist")
    public AjaxResult getByPropertyTypelist(@RequestParam("propertyType") String propertyType) {
        LambdaQueryWrapper<PropertyType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PropertyType::getPropertyType, propertyType);
        queryWrapper.eq(PropertyType::getDelFlag, "0");
        queryWrapper.orderByAsc(PropertyType::getSort);
        List<PropertyType> list = propertyTypeService.list(queryWrapper);
        return AjaxResult.success(list);
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("资产类型管理详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {

        return AjaxResult.success(propertyTypeService.getById(id));
    }

    /**
     * @param propertyType
     * @return
     */
    @ApiOperation("资产类型管理增加")
    @PostMapping("add")
    public AjaxResult add(@RequestBody PropertyType propertyType) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        propertyType.setEstateId(sysUser.getEstateId());
        LambdaQueryWrapper<PropertyType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PropertyType::getEstateId, propertyType.getEstateId());
        queryWrapper.eq(PropertyType::getPropertyType, propertyType.getPropertyType());
        queryWrapper.eq(PropertyType::getName, propertyType.getName());
        int size = propertyTypeService.list(queryWrapper).size();
        if (size >= 1) {
            return AjaxResult.error("当前名称已存在");
        }

        propertyType.setCreateTime(new Date());
        propertyType.setCreateBy(sysUser.getUserName());
        propertyTypeService.save(propertyType);
        return AjaxResult.success();
    }

    /**
     * @param propertyType
     * @return
     */
    @ApiOperation("资产类型管理修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody PropertyType propertyType) {

        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        propertyType.setEstateId(sysUser.getEstateId());
        LambdaQueryWrapper<PropertyType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PropertyType::getEstateId, propertyType.getEstateId());
        queryWrapper.eq(PropertyType::getPropertyType, propertyType.getPropertyType());
        queryWrapper.eq(PropertyType::getName, propertyType.getName());
        queryWrapper.apply(" id!=" + propertyType.getId());
        int size = propertyTypeService.list(queryWrapper).size();
        if (size >= 1) {
            return AjaxResult.error("当前名称已存在");
        }
        propertyType.setUpdateTime(new Date());
        propertyType.setUpdateBy(sysUser.getUserName());
        propertyTypeService.updateById(propertyType);
        return AjaxResult.success();
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("资产类型管理删除")
    @DeleteMapping("remove")
    public AjaxResult remove(@RequestParam("id") String id) {

        propertyTypeService.removeById(id);
        return AjaxResult.success();
    }


}
