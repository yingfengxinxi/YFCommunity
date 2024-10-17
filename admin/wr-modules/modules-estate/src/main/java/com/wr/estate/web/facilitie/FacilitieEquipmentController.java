package com.wr.estate.web.facilitie;


import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.customer.patrol.PatrolWorkOrderService;
import com.wr.estate.service.facilitie.FacilitieEquipmentService;
import com.wr.remote.domain.FacilitieEquipment;
import com.wr.remote.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author lvzy
 * @Date 2024/2/26 21:18
 */
@RestController
@RequestMapping("facilitieEquipment")
@Api(tags = "物管平台-设施设备管理")
public class FacilitieEquipmentController extends BaseController {

    @Autowired
    private FacilitieEquipmentService facilitieEquipmentService;


    /**
     * @param facilitieEquipment
     * @return
     */
    @ApiOperation("分页列表")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody FacilitieEquipment facilitieEquipment) {
        LambdaQueryWrapper<FacilitieEquipment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FacilitieEquipment::getCommunityId, facilitieEquipment.getCommunityId());
        if (StringUtils.isNotEmpty(facilitieEquipment.getEquipmentName())) {
            queryWrapper.like(FacilitieEquipment::getEquipmentName, facilitieEquipment.getEquipmentName());
        }
        if (StringUtils.isNotEmpty(facilitieEquipment.getFacilitieEquipmentType())) {
            queryWrapper.eq(FacilitieEquipment::getFacilitieEquipmentType, facilitieEquipment.getFacilitieEquipmentType());
        }
        if (StringUtils.isNotEmpty(facilitieEquipment.getStartTime()) && StringUtils.isNotEmpty(facilitieEquipment.getEndTime())) {
            queryWrapper.apply(" date_format(final_inspection_time,'%Y-%m-%d') between date_format('" + facilitieEquipment.getStartTime() + "','%Y-%m-%d') and date_format('" + facilitieEquipment.getEndTime() + "','%Y-%m-%d')");
        }
        queryWrapper.select(FacilitieEquipment.class, r -> !r.getColumn().equals("qr_code"));
        queryWrapper.orderByDesc(FacilitieEquipment::getCreateTime);
        PageUtils.startPage(facilitieEquipment.getPageNum(), facilitieEquipment.getPageSize());
        List<FacilitieEquipment> rescueVOList = facilitieEquipmentService.list(queryWrapper);
        return getDataTable(rescueVOList);
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable String id) {

        return AjaxResult.success(facilitieEquipmentService.getById(id));
    }

    /**
     * @param facilitieEquipment
     * @return
     */
    @ApiOperation("增加设施设备")
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody FacilitieEquipment facilitieEquipment) {

        LambdaQueryWrapper<FacilitieEquipment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FacilitieEquipment::getCommunityId, facilitieEquipment.getCommunityId());
        queryWrapper.eq(FacilitieEquipment::getProductNumber, facilitieEquipment.getProductNumber());
        int size = facilitieEquipmentService.list(queryWrapper).size();
        if (size >= 1) {
            return AjaxResult.error("产品编号已存在请勿重复添加");
        }
        facilitieEquipment.setCreateBy(SecurityUtils.getUsername());
        facilitieEquipment.setCreateTime(new Date());
        facilitieEquipment.setDelFlag("0");
        facilitieEquipment.setId(UUID.randomUUID().toString().replace("-", ""));
        facilitieEquipment.setQrCode(facilitieEquipmentService.getQrCode(facilitieEquipment));
        facilitieEquipmentService.save(facilitieEquipment);
        return AjaxResult.success();
    }


    /**
     * @param facilitieEquipment
     * @return
     */
    @ApiOperation("修改设施设备")
    @PutMapping("update")
    public AjaxResult update(@RequestBody FacilitieEquipment facilitieEquipment) {
        LambdaQueryWrapper<FacilitieEquipment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FacilitieEquipment::getCommunityId, facilitieEquipment.getCommunityId());
        queryWrapper.eq(FacilitieEquipment::getProductNumber, facilitieEquipment.getProductNumber());
        List<FacilitieEquipment> equipmentList = facilitieEquipmentService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(equipmentList)) {
            for (FacilitieEquipment equipment : equipmentList) {
                if (!StringUtils.equals(equipment.getId(), facilitieEquipment.getId())) {
                    return AjaxResult.error("产品编号已存在请勿重复添加");
                }
            }
        }

        //facilitieEquipment.setUpdateBy(SecurityUtils.getUsername());
        //facilitieEquipment.setUpdateTime(new Date());
        facilitieEquipment.setQrCode(facilitieEquipmentService.getQrCode(facilitieEquipment));
        facilitieEquipmentService.updateById(facilitieEquipment);
        return AjaxResult.success();
    }

    /**
     * 批量更新二维码内容
     *
     * @return
     */
    @GetMapping("bachUpdateQrCode")
    public AjaxResult bachUpdateQrCode() {
        List<FacilitieEquipment> equipmentList = facilitieEquipmentService.list();
        for (FacilitieEquipment facilitieEquipment : equipmentList) {
            facilitieEquipment.setQrCode(facilitieEquipmentService.getQrCode(facilitieEquipment));
            facilitieEquipmentService.updateById(facilitieEquipment);
        }
        return AjaxResult.success();
    }

    /**
     * @param id
     * @param equipmentStatus
     * @return
     */
    @ApiOperation("修改状态")
    @GetMapping("updateEquipmentStatus")
    public AjaxResult updateEquipmentStatus(@RequestParam("id") String id, @RequestParam("equipmentStatus") String equipmentStatus) {

        FacilitieEquipment facilitieEquipment = facilitieEquipmentService.getById(id);
        facilitieEquipment.setEquipmentStatus(equipmentStatus);
        facilitieEquipmentService.updateById(facilitieEquipment);
        return AjaxResult.success();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ApiOperation("设施设备删除")
    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("id") String id) {
        facilitieEquipmentService.removeById(id);
        return AjaxResult.success();
    }

    /**
     * 导出
     */

    @Log(title = "设施设备信息导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FacilitieEquipment facilitieEquipment) {

        LambdaQueryWrapper<FacilitieEquipment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FacilitieEquipment::getCommunityId, facilitieEquipment.getCommunityId());
        if (StringUtils.isNotEmpty(facilitieEquipment.getEquipmentName())) {
            queryWrapper.like(FacilitieEquipment::getEquipmentName, facilitieEquipment.getEquipmentName());
        }
        if (StringUtils.isNotEmpty(facilitieEquipment.getFacilitieEquipmentType())) {
            queryWrapper.eq(FacilitieEquipment::getFacilitieEquipmentType, facilitieEquipment.getFacilitieEquipmentType());
        }
        queryWrapper.select(FacilitieEquipment.class, r -> !r.getColumn().equals("qr_code"));
        queryWrapper.orderByDesc(FacilitieEquipment::getCreateTime);
        List<FacilitieEquipment> list = facilitieEquipmentService.list(queryWrapper);
        ExcelUtil<FacilitieEquipment> util = new ExcelUtil(FacilitieEquipment.class);
        util.exportExcel(response, list, "设施设备数据");

    }


    @Log(title = "批量导入设备信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ApiOperation("批量导入设备信息")
    public AjaxResult importData(MultipartFile file, Long communityId) throws Exception {
        ExcelUtil<FacilitieEquipment> util = new ExcelUtil<FacilitieEquipment>(FacilitieEquipment.class);
        List<FacilitieEquipment> dataList = util.importExcel(file.getInputStream());
        String message = facilitieEquipmentService.importData(dataList, communityId);
        return AjaxResult.success(message);
    }

    @ApiOperation("导出模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<FacilitieEquipment> util = new ExcelUtil<FacilitieEquipment>(FacilitieEquipment.class);
        util.importTemplateExcel(response, "设备信息");
    }

}
