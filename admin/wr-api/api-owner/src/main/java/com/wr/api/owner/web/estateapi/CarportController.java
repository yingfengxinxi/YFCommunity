package com.wr.api.owner.web.estateapi;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wr.api.owner.entity.estateapi.CarportImportVo;
import com.wr.api.owner.service.estateapi.CarportService;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.center.vo.BusCarportLogVo;
import com.wr.remote.estate.center.vo.BusCarportVo;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 车位管理
 *
 * @Author: lvzy
 * @Date: 2022-10-14 13:54:20
 * @Desc: 车位管理
 */
@RestController
@RequestMapping("/estateapi/carport")
@Api(tags = "业主端-车位管理")
public class CarportController extends BaseController {

    @Autowired
    private CarportService service;

    @ApiOperation("车位管理分页")
    @GetMapping("/listPage")
    public TableDataInfo listPage(BusCarportVo carportVo) {
        startPage();
        List<BusCarportVo> carportVos = service.listPage( carportVo );
        return getDataTable( carportVos );
    }

    @GetMapping("/detail")
    public AjaxResult detail(BusCarportVo carportVo) {
        BusCarportVo carportVos = service.detail( carportVo.getCarportId() );
        return AjaxResult.success( carportVos );
    }

    @ApiOperation("车位详情")
    @GetMapping("/detailList")
    public AjaxResult detailList(BusCarportVo carportVo) {
        List<BusCarportLogVo> carportLog = service.detailList( carportVo.getCarportId() );
        return AjaxResult.success( carportLog );
    }

    /**
     * 新增或修改车辆信息
     *
     * @param vehicleVo
     * @return
     */
    @Log(title = "车位绑定车辆信息", businessType = BusinessType.INSERT)
    @ApiOperation("车位绑定车辆信息")
    @PostMapping("/bindVehicle")
    public AjaxResult saveCar(@RequestBody BusVehicleVo vehicleVo) {
        return service.saveCar( vehicleVo );
    }

    @ApiOperation("查询所有车位")
    @GetMapping("/getCommunityCarportList")
    public AjaxResult getCommunityCarportList(@RequestParam("communityId") Long communityId) {

        return AjaxResult.success( service.getCommunityCarportList( communityId ) );
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */

    @Log(title = "批量删除车位信息", businessType = BusinessType.DELETE)
    @PostMapping("/removes")
    public AjaxResult removes(@RequestBody Long[] ids) {
        Integer removes = service.removes( ids );
        return AjaxResult.success( removes );
    }

    /**
     * 转让 -> 新增车位记录表数据
     *
     * @param carportVo
     * @return
     */

    @Log(title = "新增车位记录表数据", businessType = BusinessType.INSERT)
    @PostMapping("/cede")
    public AjaxResult cedeSubmit(BusCarportLogVo carportVo) {
        return AjaxResult.success( service.cedeSubmit( carportVo ) );
    }

    /**
     * 新增或修改
     *
     * @param carportVo
     * @return
     */

    @Log(title = "新增或修改车位记录", businessType = BusinessType.INSERT)
    @PostMapping("/saveOrUpdate")
    public AjaxResult saveOrUpdate(BusCarportVo carportVo) {
        return AjaxResult.success( service.saveOrUpdate( carportVo ) );
    }

    /**
     * 修改下详情展示
     *
     * @param carportVo
     * @return
     */
    @GetMapping("/updateDetail")
    public AjaxResult updateDetail(BusCarportVo carportVo) {
        return AjaxResult.success( service.updateDetail( carportVo ) );
    }

    /**
     * 修改车位状态
     *
     * @param carportVo
     * @return
     */
    @Log(title = "修改车位状态", businessType = BusinessType.UPDATE)
    @PostMapping("/changeCarportStatus")
    public AjaxResult changeCarportStatus(BusCarportVo carportVo) {
        return AjaxResult.success( service.changeCarportStatus( carportVo ) );
    }

    @ApiOperation("导出模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<CarportImportVo> util = new ExcelUtil<CarportImportVo>( CarportImportVo.class );
        util.importTemplateExcel( response, "车位信息" );
    }


    @Log(title = "批量导入车位信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, Long communityId) throws Exception {
        ExcelUtil<CarportImportVo> util = new ExcelUtil<CarportImportVo>( CarportImportVo.class );
        List<CarportImportVo> dataList = util.importExcel( file.getInputStream() );
        String message = service.importData( dataList, communityId );
        return AjaxResult.success( message );
    }

    @ApiOperation("查询未出租的车位管理")
    @PostMapping("/selectBusCarportNoRental")
    public AjaxResult selectBusCarportNoRental(@RequestBody BusCarportVo carportVo) {
        return AjaxResult.success( service.selectBusCarportNoRental( carportVo ) );
    }

    /**
     * 车位续费
     *
     * @param carport
     * @return
     */

    @Log(title = "车位续费", businessType = BusinessType.UPDATE)
    @PostMapping("/renewalBusCarport")
    @Transactional
    public AjaxResult renewalBusCarport(@RequestBody BusCarportVo carport) {

        return service.renewalBusCarport( carport );
    }

    /**
     * 车位清理
     *
     * @param carportId
     * @return
     */

    @Log(title = "车位清理", businessType = BusinessType.UPDATE)
    @GetMapping("/clearBusCarport")
    public void clearBusCarport(@RequestParam String carportId) {
        service.clearBusCarport( carportId );
    }
}