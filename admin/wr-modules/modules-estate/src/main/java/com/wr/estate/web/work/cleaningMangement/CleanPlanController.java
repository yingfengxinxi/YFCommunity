package com.wr.estate.web.work.cleaningMangement;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.work.cleaningMangement.CleanPlanService;
import com.wr.remote.work.cleaningManagement.CleanPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-27 16:16:22
 * @Desc: 保洁计划控制层
 */
@RestController
@RequestMapping("/cleaningManagement/hygiene")
public class CleanPlanController extends BaseController {

    @Autowired
    private CleanPlanService cleanPlanService;

    /**
     * 查询保洁计划集合
     * @param cleanPlan
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('work:sanitation:plan:list')")
    public TableDataInfo list(CleanPlan cleanPlan) {
        startPage();
        List<CleanPlan> cleanPlans = cleanPlanService.selectCleanPlanList(cleanPlan);
        return getDataTable(cleanPlans);
    }

    /**
     * 根据ID查询的单个保洁计划
     * @param planId
     * @return
     */
    @GetMapping("/One/{planId}")
    @PreAuthorize("@ss.hasPermi('work:sanitation:plan:select')")
    public AjaxResult One(@PathVariable Long planId) {
        return AjaxResult.success(cleanPlanService.selectCleanPlanOne(planId));
    }


    /**
     * 新增或修改
     * @param cleanPlan
     * @return
     */
    @PutMapping("/updataAndinsert")
    @PreAuthorize("@ss.hasPermi('work:sanitation:plan:selectAndupdate')")
    public AjaxResult updataAndinsert(@RequestBody CleanPlan cleanPlan) {
        int i = cleanPlanService.insertAndupdataCleanPlanOneand(cleanPlan);
        if(i>0){
          return   AjaxResult.success("添加计划成功！");
        }else if (i == -2){
            return AjaxResult.error("该小区没有配置保洁分配方式！");
        }else {
            return AjaxResult.error("添加计划失败！");
        }
    }

    /**
     * 删除保洁计划信息
     */
    @DeleteMapping("/{planIds}")
    @PreAuthorize("@ss.hasPermi('work:sanitation:plan:del')")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        cleanPlanService.delectCleanPlan(planIds);
        return success();
    }

    /**
     * 修改状态
     */
    @GetMapping("/changeStatus/{planId}")
    @PreAuthorize("@ss.hasPermi('work:sanitation:plan:updataStatus')")
    public AjaxResult remove(@PathVariable Long planId)
    {
        int result= cleanPlanService.updataPlanOrderStatue(planId);

        return success(String.valueOf(result));
    }
}
