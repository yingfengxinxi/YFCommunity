package com.wr.estate.web.financial;

import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.PropertyFeeEntity;
import com.wr.estate.service.financial.PropertyFeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @author: bajie
 * @create: 2022/11/3
 * @Description:
 * @FileName: PropertyFeeController
 * @History:
 */
@RestController
@RequestMapping("/pfe")
@Api(tags = "物管平台-物业缴费规则设置")

public class PropertyFeeController extends BaseController {

    @Autowired
    private PropertyFeeService propertyFeeService;

    /**
     * 查询物业缴费设置列表
     */

    @PostMapping("/list")
    @ApiOperation("分页列表")
    public TableDataInfo list(@RequestBody PropertyFeeEntity propertyFee) {

        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        propertyFee.setEstateId(estateId);
        PageUtils.startPage(propertyFee.getPageNum(), propertyFee.getPageSize());
        List<PropertyFeeEntity> list = propertyFeeService.selectBusPropertyFeeList(propertyFee);
        return getDataTable(list);
    }


    /**
     * 获取物业缴费设置详细信息
     */
    @ApiOperation("获取物业缴费设置详细信息")
    @GetMapping(value = "/{propertyFeeId}")
    public AjaxResult getInfo(@PathVariable("propertyFeeId") String propertyFeeId) {
        return propertyFeeService.selectBusPropertyFeeByCommunityId(propertyFeeId);
    }

    /**
     * 新增物业缴费设置
     */
    @Log(title = "物业缴费设置", businessType = BusinessType.INSERT)
    @ApiOperation("添加物业缴费设置")
    @PostMapping
    public AjaxResult add(@RequestBody PropertyFeeEntity propertyFee) {

        propertyFee.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        try {
            return propertyFeeService.insertBusPropertyFee(propertyFee);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 修改物业缴费设置
     */
    @ApiOperation("修改物业缴费设置")
    @PutMapping
    public AjaxResult edit(@RequestBody PropertyFeeEntity propertyFee) {

        try {
            return propertyFeeService.updateBusPropertyFee(propertyFee);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 修改物业缴费设置
     */
    @ApiOperation("修改物业缴费状态")
    @PutMapping("updatePropertyFeeStatus")
    public AjaxResult updatePropertyFeeStatus(@RequestBody PropertyFeeEntity propertyFee) {
        propertyFeeService.updatePropertyFeeStatus(propertyFee);
        return AjaxResult.success();
    }

    /**
     * 根据楼栋查询是否配置物业缴费规则
     */
    @ApiOperation("根据楼栋查询是否配置物业缴费规则")
    @GetMapping("getByBuildingIdPropertyFeeCount")
    public AjaxResult getByBuildingIdPropertyFeeCount(@RequestParam("buildingId") Long buildingId, @RequestParam("communityId") Long communityId) {

        return AjaxResult.success(propertyFeeService.getByBuildingIdPropertyFeeCount(buildingId, communityId));
    }

    /**
     * 生成账单定时任务
     */
    @ApiOperation("生成账单定时任务")
    @GetMapping("generatePropertyBillTask")
    public AjaxResult generatePropertyBillTask() {
        propertyFeeService.generatePropertyBillTask();
        return AjaxResult.success();
    }

    /**
     * 未交房生成账单
     */
    @ApiOperation("未交房生成账单")
    @GetMapping("unpaidPropertyGeneratePropertyBill")
    public AjaxResult unpaidPropertyGeneratePropertyBill() {
        propertyFeeService.unpaidPropertyGeneratePropertyBill();
        return AjaxResult.success();
    }
}
