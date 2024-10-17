package com.wr.estate.web.center;

import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.estate.service.center.CarVehicleService;
import com.wr.remote.estate.center.vo.SpaceVehicleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 车辆管理
 *
 * @Author: lvzy
 * @Date: 2022-10-12 09:59:59
 * @Desc: 车辆管理
 */
@RestController
@RequestMapping("/carVehicle")
@Api(tags = "物管平台-车辆管理")
public class CarVehicleController extends BaseController {

    @Autowired
    private CarVehicleService service;

    @ApiOperation("车辆管理分页")
    @GetMapping("/listPage")
    public TableDataInfo listPage(BusVehicleVo vehicleVo) {
        startPage();
        List<BusVehicleVo> busVehicleVos = service.listPage(vehicleVo);
        return getDataTable(busVehicleVos);
    }

    @GetMapping("/detail")
    public AjaxResult detail(Long vehicleId) {
        BusVehicleVo detail = service.detail(vehicleId);
        return AjaxResult.success(detail);
    }

    /**
     * 单个删除
     *
     * @param vehicleId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('estate:vehicle:remove')")
    @Log(title = "车辆单个删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public AjaxResult remove(Long vehicleId) {
        return AjaxResult.success(service.remove(vehicleId));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Log(title = "车辆批量删除", businessType = BusinessType.DELETE)
    @PostMapping("/removes")
    public AjaxResult removes(@RequestBody Long[] ids) {
        Integer removes = service.removes(ids);
        return AjaxResult.success(removes);
    }

    /**
     * 查找业主手机号
     *
     * @param ownerId
     * @return
     */
    @GetMapping("/selectOwnerName")
    public AjaxResult selectOwnerName(Long ownerId) {
        BusVehicleVo detail = service.selectOwnerName(ownerId);
        return AjaxResult.success(detail);
    }

    /**
     * 查找业主车辆
     *
     * @param ownerId
     * @return
     */
    @GetMapping("/selectOwnerCar")
    public AjaxResult selectOwnerCar(Long ownerId) {
        List<BusVehicleVo> busVehicleVos = service.selectOwnerCar(ownerId);
        return AjaxResult.success(busVehicleVos);
    }

    /**
     * 新增或修改车辆信息
     *
     * @param vehicleVo
     * @return
     */
    @ApiOperation("新增或修改车辆信息")
    @Log(title = "新增或修改车辆信息", businessType = BusinessType.INSERT)
    @PostMapping("/saveOrUpdate")
    public AjaxResult saveCar(@RequestBody BusVehicleVo vehicleVo) {
        return service.saveCar(vehicleVo);
    }


    /**
     * 编辑页面详情展示
     *
     * @param vehicleId
     * @return
     */
    @GetMapping("/updateDetail")
    public AjaxResult updateDetail(Long vehicleId) {
        return AjaxResult.success(service.updateDetail(vehicleId));
    }

    /**
     * 查询当前业主下车辆信息
     *
     * @param communityId
     * @param ownerId
     * @return
     */
    @GetMapping("/getByOwnerIdVehicleList")
    public AjaxResult getByOwnerIdVehicleList(@RequestParam("communityId") Long communityId, @RequestParam("ownerId") Long ownerId) {

        return AjaxResult.success(service.getByOwnerIdVehicleList(communityId, ownerId));
    }

    /**
     * 查询当前业主下可绑定车辆信息
     *
     * @param communityId
     * @param accountId
     * @return
     */
    @GetMapping("/getByAccountIdVehicleList")
    public AjaxResult getByAccountIdVehicleList(@RequestParam("communityId") Long communityId, @RequestParam("accountId") Long accountId) {

        return AjaxResult.success(service.getByAccountIdVehicleList(communityId, accountId));
    }

    // 上传模板
    @ApiOperation("导入车辆信息")
    @Log(title = "批量导入车辆信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, Long communityId) throws Exception {
        ExcelUtil<BusVehicleVo> util = new ExcelUtil<BusVehicleVo>(BusVehicleVo.class);
        List<BusVehicleVo> dataList = util.importExcel(file.getInputStream());
        String message = service.importData(dataList, communityId);
        return AjaxResult.success(message);
    }


    // 下载模板
    @ApiOperation("下载模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<BusVehicleVo> util = new ExcelUtil<BusVehicleVo>(BusVehicleVo.class);
        util.importTemplateExcel(response, "车辆信息");
    }

}
