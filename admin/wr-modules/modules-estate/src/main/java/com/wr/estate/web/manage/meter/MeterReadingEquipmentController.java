package com.wr.estate.web.manage.meter;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.MeterReadingEquipment;
import com.wr.estate.service.manage.meter.MeterReadingEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/4 11:26
 */
@RequestMapping("meterReadingEquipment")
@RestController
@Api(tags = "物管平台-抄表工单")
public class MeterReadingEquipmentController extends BaseController {

    @Autowired
    private MeterReadingEquipmentService meterReadingEquipmentService;

    /**
     * @param meterReadingEquipment
     * @return
     */
    @ApiOperation("抄表设备设施设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "meterReadingType", value = "抄表类型", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "energyConsumptionType", value = "能耗类型", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    @PostMapping("list")
    public TableDataInfo list(@RequestBody MeterReadingEquipment meterReadingEquipment) {
        LambdaQueryWrapper<MeterReadingEquipment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MeterReadingEquipment::getCommunityId, meterReadingEquipment.getCommunityId());
        if (StringUtils.isNotEmpty(meterReadingEquipment.getMeterReadingType())) {
            queryWrapper.eq(MeterReadingEquipment::getMeterReadingType, meterReadingEquipment.getMeterReadingType());
        }
        if (StringUtils.isNotEmpty(meterReadingEquipment.getEnergyConsumptionType())) {
            queryWrapper.eq(MeterReadingEquipment::getEnergyConsumptionType, meterReadingEquipment.getEnergyConsumptionType());
        }
        PageUtils.startPage(meterReadingEquipment.getPageNum(), meterReadingEquipment.getPageSize());
        queryWrapper.orderByDesc(MeterReadingEquipment::getCreateTime);
        return getDataTable(meterReadingEquipmentService.list(queryWrapper));
    }


    /**
     * @param id
     * @return
     */
    @ApiOperation("抄表设备设施设置详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        return AjaxResult.success(meterReadingEquipmentService.getById(id));
    }

    /**
     * @param meterReadingEquipment
     * @return
     */
    @ApiOperation("抄表设备设施设置增加")
    @PostMapping("save")
    public AjaxResult save(@RequestBody MeterReadingEquipment meterReadingEquipment) {
        meterReadingEquipment.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        meterReadingEquipment.setCreateBy(SecurityUtils.getUsername());
        meterReadingEquipment.setCreateTime(new Date());
        LambdaQueryWrapper<MeterReadingEquipment> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(MeterReadingEquipment::getEquipmentNo, meterReadingEquipment.getEquipmentNo());
        queryWrapper1.eq(MeterReadingEquipment::getCommunityId, meterReadingEquipment.getCommunityId());
        MeterReadingEquipment readingEquipment1 = meterReadingEquipmentService.getOne(queryWrapper1);
        if (readingEquipment1 != null) {
            return AjaxResult.error("当前设备编号已存在请勿重复添加");
        }
        meterReadingEquipment.setStatus("0");
        meterReadingEquipmentService.save(meterReadingEquipment);
        return AjaxResult.success();
    }

    /**
     * @param meterReadingEquipment
     * @return
     */
    @ApiOperation("抄表设备设施设置修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody MeterReadingEquipment meterReadingEquipment) {
        meterReadingEquipment.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        meterReadingEquipment.setUpdateBy(SecurityUtils.getUsername());
        meterReadingEquipment.setUpdateTime(new Date());
        meterReadingEquipmentService.updateById(meterReadingEquipment);
        return AjaxResult.success();
    }


    /**
     * @param id
     * @param status
     * @return
     */
    @ApiOperation("抄表设备设施状态修改")
    @GetMapping("updateByIdStatus")
    public AjaxResult updateByIdStatus(@RequestParam("id") String id, @RequestParam("status") String status) {
        try {


            MeterReadingEquipment readingEquipment = meterReadingEquipmentService.getById(id);

            LambdaQueryWrapper<MeterReadingEquipment> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(MeterReadingEquipment::getCommunityId, readingEquipment.getCommunityId());
            queryWrapper1.eq(MeterReadingEquipment::getEnergyConsumptionType, readingEquipment.getEnergyConsumptionType());
            queryWrapper1.eq(MeterReadingEquipment::getMeterReadingType, readingEquipment.getMeterReadingType());
            List<MeterReadingEquipment> meterReadingEquipmentList = meterReadingEquipmentService.list(queryWrapper1);
            if (CollectionUtils.isNotEmpty(meterReadingEquipmentList)) {
                meterReadingEquipmentList.stream().forEach(meterReadingEquipment -> meterReadingEquipment.setStatus(status));
            }
            meterReadingEquipmentService.updateBatchById(meterReadingEquipmentList);

            String flag = "0";
            if (StringUtils.equals(status, "0")) {
                flag = "1";
            }
            meterReadingEquipmentService.saveRedisData(readingEquipment, flag);

            return AjaxResult.success();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param id
     * @return
     */
    @ApiOperation("抄表设备设施设置删除")
    @DeleteMapping("removeById")
    public AjaxResult removeById(@RequestParam("id") String id) {

        meterReadingEquipmentService.removeById(id);

        return AjaxResult.success();
    }

}
