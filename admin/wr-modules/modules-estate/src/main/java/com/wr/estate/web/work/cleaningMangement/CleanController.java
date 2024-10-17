package com.wr.estate.web.work.cleaningMangement;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.entity.vo.CleanExecuteNameAndnumbersVO;
import com.wr.estate.entity.vo.CleanExecuteNameVO;
import com.wr.estate.entity.vo.CleanVo;
import com.wr.estate.mapper.work.cleaningMangement.CleanMapper;
import com.wr.estate.service.work.cleaningMangement.CleanPlanService;
import com.wr.estate.service.work.cleaningMangement.CleanService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.cleaningManagement.Clean;
import com.wr.remote.work.cleaningManagement.CleanPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-31 16:21:58
 * @Desc:
 */
@RestController
@RequestMapping("/cleaningManagement/HealthWork")
public class CleanController extends BaseController {

    @Autowired
    private CleanService cleanService;

    @Autowired
    private CleanMapper cleanMapper;

    /**
     * 根据计划ID查出生成的计划保洁计工单
     * @param clean
     * @return
     */
    @GetMapping("/ByPlanId")
    @PreAuthorize("@ss.hasPermi('work:sanitation:cleanersSingle:select')")
    public AjaxResult ByPlanIdlist(Clean clean) {
        return AjaxResult.success(cleanService.selectListByPlanId(clean.getPlanId(),clean.getCommunityId()));
    }


    /**
     * 查询保洁工单集合
     * @param clean
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('work:sanitation:cleanersSingle:list')")
    public TableDataInfo list(Clean clean) {
        startPage();
        CleanExecuteNameAndnumbersVO cleanPlans = cleanService.selectCleanList(clean);
        final TableDataInfo dataTable = getDataTable(cleanPlans.getCleanExecuteNameVOList());
        dataTable.setTotal(cleanPlans.getNumbers());
        return dataTable;
    }

    /**
     * 根据工单ID查询的单个工单信息
     * @param cleanId
     * @return
     */
    @GetMapping("/One/{cleanId}")
    @PreAuthorize("@ss.hasPermi('work:sanitation:cleanersSingle:selectOne')")
    public AjaxResult One(@PathVariable Long cleanId) {
        return AjaxResult.success(cleanService.selectCleanOne(cleanId));
    }

    /**
     * 新增或修改工单信息
     * @param clean
     * @return
     */
    @PutMapping("/updataAndinsert")
    @PreAuthorize("@ss.hasPermi('work:sanitation:cleanersSingle:selectAndupdate')")
    public AjaxResult updataAndinsert(@RequestBody Clean clean) {
        return AjaxResult.success(cleanService.insertAndupdataCleanOneand(clean));
    }

    /**
     * 删除工单信息
     */
    @DeleteMapping("/removes/{cleanIds}")
    @PreAuthorize("@ss.hasPermi('work:sanitation:cleanersSingle:del')")
    public AjaxResult remove(@PathVariable Long[] cleanIds)
    {
        cleanService.delectClean(cleanIds);
        return success();
    }

    /**
     * 工单分配执行人和负责人
     */
    @PutMapping("/UpdatestaffIdAndexecuteId")
    @PreAuthorize("@ss.hasPermi('work:sanitation:cleanersSingle:UpdatestaffIdAndexecuteId')")
    public AjaxResult UpdatestaffIdAndexecuteId(Clean clean)
    {
        return  AjaxResult.success(cleanService.updataEexecuteIdAndstaffId(clean));
    }


    /**
     * 查询工单执行人列表
     */
    @GetMapping("/executorList/{cleanId}")
    @PreAuthorize("@ss.hasPermi('work:sanitation:cleanersSingle:executorList')")
    public AjaxResult GetexecutorList(@PathVariable Long cleanId)
    {
        return  AjaxResult.success(cleanService.getExecutorList(cleanId));
    }

    /**
     * 查询工单的执行流程过程
     */
    @GetMapping("/OrderRecordList/{cleanId}")
    @PreAuthorize("@ss.hasPermi('work:sanitation:cleanersSingle:OrderRecordList')")
    public AjaxResult OrderRecordList(@PathVariable Long cleanId)
    {
        return  AjaxResult.success(cleanService.selectOrderRecordList(cleanId));
    }
}
