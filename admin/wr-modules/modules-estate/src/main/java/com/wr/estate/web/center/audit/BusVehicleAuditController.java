package com.wr.estate.web.center.audit;

import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.BusVehicleAuditvo;
import com.wr.estate.entity.vo.BusVehicleImportVo;
import com.wr.estate.service.center.audit.BusVehicleAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-18 16:31:35
 * @Desc:
 */
@RestController
@RequestMapping("/cl")
@Api(tags = "物管平台-车辆审核")
public class BusVehicleAuditController extends BaseController {

    @Autowired
    private BusVehicleAuditService busVehicleAuditService;


    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('center:audit:audit:carAudit:list')")
    @ApiOperation("审核列表")
    public TableDataInfo list(BusVehicleAuditvo busVehicleAuditvo) {
        startPage();
        busVehicleAuditvo.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId().intValue());
        List<BusVehicleAuditvo> owneAuditVos = busVehicleAuditService.listOwnerAuditQure(busVehicleAuditvo);
        return getDataTable(owneAuditVos);
    }


    /**
     * 删除业主审核信息
     */
    @ApiOperation("删除业主审核信息")
    @PreAuthorize("@ss.hasPermi('center:audit:audit:carAudit:del')")
    @DeleteMapping("/{vehicleId}")
    public AjaxResult remove(@PathVariable Long vehicleId) {
        busVehicleAuditService.deleteConfigByIds(vehicleId);
        return success();
    }


    /**
     * 根据参数键名查询参数值
     */
    @ApiOperation("根据参数键名查询参数值")
    @PreAuthorize("@ss.hasPermi('center:audit:audit:carAudit:select')")
    @GetMapping(value = "/{vehicleId}")
    public AjaxResult getOwneAuditVo(@PathVariable Long vehicleId) {
        return AjaxResult.success(busVehicleAuditService.selectOneOwneAuditVo(vehicleId));
    }

    /**
     * 审核
     */
    @ApiOperation("审核")
    @PreAuthorize("@ss.hasPermi('center:audit:audit:carAudit:passAndRejection')")
    @PutMapping(value = "/audit")
    public AjaxResult getConfigKey(@RequestBody BusVehicleAuditvo busVehicleAuditvo) {
        return AjaxResult.success(busVehicleAuditService.passAndRejection(busVehicleAuditvo.getVehicleId(), busVehicleAuditvo.getAuditStatus(), busVehicleAuditvo.getRejectReason()));
    }

    /**
     * 描述：导出业主车辆登记模板
     *
     * @return 导出业主车辆登记模板
     * @author qih
     * @date 2023-09-06
     */
    @Log(title = "导出业主车辆登记模板", businessType = BusinessType.EXPORT)
    @ApiOperation("导出业主车辆登记模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<BusVehicleImportVo> util = new ExcelUtil<>(BusVehicleImportVo.class);
        util.importTemplateExcel(response, "业主车辆登记模板");
    }

    /**
     * @param file
     * @return
     * @throws Exception
     */
    @Log(title = "导入业主车辆", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<BusVehicleImportVo> util = new ExcelUtil<BusVehicleImportVo>(BusVehicleImportVo.class);
        List<BusVehicleImportVo> userList = util.importExcel(file.getInputStream());
        int estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId().intValue();
        String message = busVehicleAuditService.importVehicle(userList, estateId);
        return success(message);
    }
}
