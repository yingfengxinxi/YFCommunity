package com.wr.api.owner.web.home.location;

import com.wr.api.owner.entity.dto.ApiComplaintDTO;
import com.wr.api.owner.entity.vo.location.DisasterVo;
import com.wr.api.owner.entity.home.location.ParkAudit;
import com.wr.api.owner.entity.vo.location.MaintainVo;
import com.wr.api.owner.service.home.location.ReportForRepairService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-12-01 11:14:01
 * @Desc: 报事报修
 */
@RequestMapping("/v2/work")
@RestController
public class ReportForRepairController extends BaseController {

    @Autowired
    private ReportForRepairService reportForRepairService;

    @GetMapping("home_type")
    @AuthRequired
    public AjaxResult homeType(Long estateId){
        return AjaxResult.success(reportForRepairService.homeTypeList(estateId));
    }
    @GetMapping("disaster_type")
    @AuthRequired
    public AjaxResult disasterType(Long estateId){

        return AjaxResult.success(reportForRepairService.disasterTypeList(estateId));
    }
    @GetMapping("park_type")
    @AuthRequired
    public AjaxResult parkType(Long estateId){
        return AjaxResult.success(reportForRepairService.parkTypeList(estateId));
    }

    @PostMapping("/savePark")
    public AjaxResult savePark(@RequestBody ParkAudit parkAudit){
        int num = reportForRepairService.savePark(parkAudit);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @GetMapping("search_park")
    @AuthRequired
    public TableDataInfo searchPark(Long ownerId){
        startPage();
        return getDataTable(reportForRepairService.searchParkByOwnerId(ownerId));
    }

    /**
     * 查询灾害报事
     * @param disasterVo
     * @return
     */
    @GetMapping("disastrous")
    @AuthRequired
    public TableDataInfo disastrousList(DisasterVo disasterVo){
        startPage();
        List<DisasterVo> voList = reportForRepairService.getDisastrousList(disasterVo);
        return getDataTable(voList);
    }
    /**
     * 添加灾害报事
     * @param disasterVo
     * @return
     */
    @PostMapping("/addAisastrous")
    @AuthRequired
    public AjaxResult addAisastrous(@RequestBody DisasterVo disasterVo){
        return reportForRepairService.addAisastrous(disasterVo);
    }

    /**
     * 添加工单评价
     */
    @PostMapping("/addOrderEnquiry")
    @AuthRequired
    public AjaxResult addOrderEnquiry(@RequestBody ApiComplaintDTO apiComplaint){
        return AjaxResult.success(reportForRepairService.addOrderEnquiry(apiComplaint));
    }

    /**
     * 添加园区工单评价
     */
    @PostMapping("/addOrderpark")
    @AuthRequired
    public AjaxResult addOrderpark(@RequestBody ApiComplaintDTO apiComplaint){
        return AjaxResult.success(reportForRepairService.addOrderpark(apiComplaint));
    }

    /**
     * 根据工单状态查询详情
     * @param orderId
     * @return
     */
    @GetMapping("/info")
    @AuthRequired
    public AjaxResult AisastrouInfo(Long orderId){
        return AjaxResult.success(reportForRepairService.aisastrouInfo(orderId));
    }

    /**
     * 园区工单评价
     */
    @PostMapping("appraise_park")
    @AuthRequired
    public AjaxResult appraisePark(@RequestBody Map<String,Object> params){

        return AjaxResult.success(reportForRepairService.appraisePark(params));
    }

    /**
     * 查询灾害类型
     * @param disasterVo
     * @return
     */
    @GetMapping("getDisaster")
    @AuthRequired
    public AjaxResult getDisaster(DisasterVo disasterVo){
        return reportForRepairService.getDisaster(disasterVo);
    }


    /**
     * 查询园区报事集合
     * @return
     */
    @AuthRequired
    @GetMapping("/listParkAuditPage")
    public TableDataInfo listParkAuditPage(Integer ownerId,Integer communityId) {
        startPage();
        List<MaintainVo> maintainVos = reportForRepairService.selectListParkAuditPage(ownerId,communityId);
        return getDataTable(maintainVos);
    }

    /**
     * 查询园区报事详细
     * @param id
     * @return
     */
    @AuthRequired
    @GetMapping("/listParkAuditOne")
    public AjaxResult listParkAudit(Long auditId) {
        return AjaxResult.success(reportForRepairService.selectListParkAuditOne(auditId));
    }

    /**
     * 查询灾害报事
     * @param auditId
     * @return
     */
    @AuthRequired
    @GetMapping("/listdisasterAudit")
    public AjaxResult listdisasterAudit(Long auditId) {
        return AjaxResult.success( reportForRepairService.selectListdisasterAudit(auditId));
    }
}
