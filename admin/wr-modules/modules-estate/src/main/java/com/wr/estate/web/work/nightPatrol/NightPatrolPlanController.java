package com.wr.estate.web.work.nightPatrol;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.nightPatrol.NightPatrolPlanService;
import com.wr.remote.work.nightPatrol.WatchmanPlan;
import com.wr.remote.work.nightPatrol.vo.NightPatrolPlanListVO;
import com.wr.remote.work.nightPatrol.vo.NightPatrolPlanQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 巡更计划
 *
 * @Author: 奔波儿灞
 * @Date: 2022-10-27 14:19:53
 * @Desc:
 */
@RestController
@RequestMapping("/work/nightPatrolPlan")
public class NightPatrolPlanController extends BaseController {

    @Autowired
    private NightPatrolPlanService nightPatrolPlanService;

    /**
     * 查询巡更计划列表
     */
    @GetMapping("/list")
    public TableDataInfo list(WatchmanPlan watchmanPlan) {
        startPage();
        List<WatchmanPlan> list = nightPatrolPlanService.selectWatchmanPlanList(watchmanPlan);
        return getDataTable(list);
    }

    /**
     * 巡更计划列表分页查询
     *
     * @param query
     * @return com.wr.common.core.web.page.TableDataInfo
     * @author yx
     * @date 2022/11/1 15:05
     */
    @GetMapping("/listPage")
    public TableDataInfo listPage(NightPatrolPlanQueryVO query) {
        startPage();
        List<NightPatrolPlanListVO> nightPatrolPlanLists = nightPatrolPlanService.listPage(query);
        return getDataTable(nightPatrolPlanLists);
    }

    /**
     * 获取巡更计划详细信息
     */
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId) {
        return AjaxResult.success(nightPatrolPlanService.selectWatchmanPlanByPlanId(planId));
    }

    /**
     * 详情查看
     *
     * @param planId 计划id
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/1 11:40
     */
    @GetMapping(value = "/detail/{planId}")
    public AjaxResult detail(@PathVariable("planId") Long planId) {
        return AjaxResult.success(nightPatrolPlanService.getDetailById(planId));
    }

    /**
     * 新增巡更计划
     */
    @Log(title = "巡更计划", businessType = BusinessType.INSERT)
    @RequiresPermissions("nightPatrol:plan:add")
    @PostMapping
    public AjaxResult add(@RequestBody WatchmanPlan watchmanPlan) {
        return toAjax(nightPatrolPlanService.insertWatchmanPlan(watchmanPlan));
    }

    /**
     * 修改巡更计划
     */
    @Log(title = "巡更计划", businessType = BusinessType.UPDATE)
    @RequiresPermissions("nightPatrol:plan:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody WatchmanPlan watchmanPlan) {
        return toAjax(nightPatrolPlanService.updateWatchmanPlan(watchmanPlan));
    }

    /**
     * 修改巡更计划是否生成工单状态
     *
     * @param watchmanPlan 修改信息
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/10/31 16:32
     */
    @Log(title = "修改巡更计划是否生成工单状态", businessType = BusinessType.UPDATE)
    @RequiresPermissions("nightPatrol:plan:updateStatus")
    @PutMapping("/updatePatrolPlanOrder")
    public AjaxResult updatePatrolPlanOrder(@RequestBody WatchmanPlan watchmanPlan) {
        nightPatrolPlanService.updateNightPatrolPlanOrder(watchmanPlan);
        nightPatrolPlanService.scheduledCreateOrder();
        return success();
    }

    /**
     * 删除巡更计划
     */
    @Log(title = "巡更计划", businessType = BusinessType.DELETE)
    @RequiresPermissions("nightPatrol:plan:delete")
    @DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds) {
        return toAjax(nightPatrolPlanService.deleteWatchmanPlanByPlanIds(planIds));
    }

}
