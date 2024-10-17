//package com.wr.estate.web.work.inspect;
//
//import com.wr.common.core.web.controller.BaseController;
//import com.wr.common.core.web.domain.AjaxResult;
//import com.wr.common.core.web.page.TableDataInfo;
//import com.wr.common.log.annotation.Log;
//import com.wr.common.log.enums.BusinessType;
//import com.wr.estate.service.work.inspect.InspectPlanService;
//import com.wr.remote.work.vo.InspectPlanVo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * 巡检计划
// * @Author: lvzy
// * @Date: 2022-10-27 09:43:29
// * @Desc: 巡检计划
// */
//@RestController
//@RequestMapping("/inspect/plan")
//public class InspectPlanController extends BaseController {
//
//    @Autowired
//    private InspectPlanService service;
//
//    @GetMapping("/listPage")
//    public TableDataInfo listPage(InspectPlanVo inspectPlanVo) {
//        startPage();
//        List<InspectPlanVo> listPage = service.listPage(inspectPlanVo);
//        return getDataTable(listPage);
//    }
//
//    @PostMapping("/changeStatus")
//    public AjaxResult changeStatus(InspectPlanVo inspectPlanVo) {
//        return AjaxResult.success(service.changeStatus(inspectPlanVo));
//    }
//
//
//    @GetMapping("/detail")
//    public AjaxResult detail(InspectPlanVo inspectPlanVo) {
//        return AjaxResult.success(service.detail(inspectPlanVo.getPlanId()));
//    }
//
//    @GetMapping("/detailList")
//    public AjaxResult detailList(InspectPlanVo inspectPlanVo) {
//        return AjaxResult.success(service.detailList(inspectPlanVo.getPlanId()));
//    }
//
//    /**
//     * 详情 工单生成历史列表
//     * @param inspectPlanVo
//     * @return
//     */
//    @GetMapping("/generateList")
//    public AjaxResult generateList(InspectPlanVo inspectPlanVo) {
//        return AjaxResult.success(service.generateList(inspectPlanVo.getPlanId()));
//    }
//
//    /**
//     * 新增时获取巡检类型
//     * @param inspectPlanVo
//     * @return
//     */
//    @GetMapping("/getTourNameList")
//    public AjaxResult getTourNameList(InspectPlanVo inspectPlanVo) {
//        return AjaxResult.success(service.getTourNameList(inspectPlanVo.getCommunityId()));
//    }
//
//    /**
//     * 新增时获取设备编号
//     * @return
//     */
//    @GetMapping("/deviceNo/{communityId}")
//    public AjaxResult deviceNo(@PathVariable("communityId") Long communityId) {
//        return AjaxResult.success(service.deviceNo(communityId));
//    }
//
//    @PreAuthorize("@ss.hasPermi('estate:plan:add')")
//    @Log(title = "新增或修改巡检计划", businessType = BusinessType.INSERT)
//    @PostMapping("/saveOrUpdate")
//    public AjaxResult saveOrUpdate(@RequestBody InspectPlanVo inspectPlanVo) {
//        return AjaxResult.success(service.saveOrUpdate(inspectPlanVo));
//    }
//
//    @GetMapping("/updateDetail")
//    public AjaxResult updateDetail(InspectPlanVo inspectPlanVo) {
//        return AjaxResult.success(service.updateDetail(inspectPlanVo.getPlanId()));
//    }
//
//    @PreAuthorize("@ss.hasPermi('estate:plan:remove')")
//    @Log(title = "删除巡检计划", businessType = BusinessType.INSERT)
//    @PostMapping("/removes")
//    public AjaxResult removes(@RequestBody Long[] ids) {
//        return service.removes(ids);
//    }
//
//}
