//package com.wr.estate.web.work.patrol;
//
//import com.wr.common.core.utils.poi.ExcelUtil;
//import com.wr.common.core.web.controller.BaseController;
//import com.wr.common.core.web.domain.AjaxResult;
//import com.wr.common.core.web.page.TableDataInfo;
//import com.wr.common.log.annotation.Log;
//import com.wr.common.log.enums.BusinessType;
//import com.wr.common.security.annotation.RequiresPermissions;
//import com.wr.estate.service.work.patrol.PatrolPlanService;
//import com.wr.estate.service.work.patrol.PointService;
//import com.wr.remote.work.patrol.PatrolPlan;
//import com.wr.remote.work.patrol.Point;
//import com.wr.remote.work.patrol.vo.PatrolPlanListVO;
//import com.wr.remote.work.patrol.vo.PatrolPlanQueryVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * 巡查计划Controller
// *
// * @author 奔波儿灞
// * @date 2022-10-31
// */
//@RestController
//@RequestMapping("/work/patrolPlan")
//public class PatrolPlanController extends BaseController {
//    @Autowired
//    private PatrolPlanService patrolPlanService;
//
//    @Autowired
//    private PointService pointService;
//
//    /**
//     * 查询巡查计划列表
//     */
//    @GetMapping("/listPage")
//    public TableDataInfo listPage(PatrolPlanQueryVO query) {
//        startPage();
//        List<PatrolPlanListVO> listPage = patrolPlanService.listPage(query);
//        return getDataTable(listPage);
//    }
//
//    /**
//     * 查询巡查点列表
//     *
//     * @param communityId 社区id
//     * @return com.wr.common.core.web.domain.AjaxResult
//     * @author yx
//     * @date 2022/10/31 14:59
//     */
//    @GetMapping("/selectPointList/{communityId}")
//    public AjaxResult selectPointList(@PathVariable("communityId") Long communityId) {
//        Point point = new Point();
//        point.setCommunityId(communityId);
//        return AjaxResult.success(pointService.selectList2(point));
//    }
//
//    /**
//     * 获取巡查计划详细信息
//     */
//    @GetMapping(value = "/{planId}")
//    public AjaxResult getInfo(@PathVariable("planId") Long planId) {
//        return AjaxResult.success(patrolPlanService.selectPatrolPlanByPlanId(planId));
//    }
//
//    /**
//     * 详情查看
//     *
//     * @param planId 计划id
//     * @return com.wr.common.core.web.domain.AjaxResult
//     * @author yx
//     * @date 2022/11/1 11:40
//     */
//    @GetMapping(value = "/detail/{planId}")
//    public AjaxResult detail(@PathVariable("planId") Long planId) {
//        return AjaxResult.success(patrolPlanService.detail(planId));
//    }
//
//    /**
//     * 新增巡查计划
//     */
//    @Log(title = "巡查计划", businessType = BusinessType.INSERT)
//    @RequiresPermissions("patrol:plan:add")
//    @PostMapping
//    public AjaxResult add(@RequestBody PatrolPlan patrolPlan) {
//        return toAjax(patrolPlanService.insertPatrolPlan(patrolPlan));
//    }
//
//    /**
//     * 修改巡查计划
//     */
//    @Log(title = "巡查计划", businessType = BusinessType.UPDATE)
//    @RequiresPermissions("patrol:plan:edit")
//    @PutMapping
//    public AjaxResult edit(@RequestBody PatrolPlan patrolPlan) {
//        return toAjax(patrolPlanService.updatePatrolPlan(patrolPlan));
//    }
//
//    /**
//     * 修改巡查计划是否生成工单状态
//     *
//     * @param patrolPlan 工单修改信息
//     * @return com.wr.common.core.web.domain.AjaxResult
//     * @author yx
//     * @date 2022/10/31 16:32
//     */
//    @Log(title = "修改巡查计划是否生成工单状态", businessType = BusinessType.UPDATE)
//    @RequiresPermissions("patrol:plan:updateStatus")
//    @PutMapping("/updatePatrolPlanOrder")
//    public AjaxResult updatePatrolPlanOrder(@RequestBody PatrolPlan patrolPlan) {
//        patrolPlanService.updatePatrolPlanOrder(patrolPlan);
//        return success();
//    }
//
//    /**
//     * 删除巡查计划
//     */
//    @Log(title = "巡查计划", businessType = BusinessType.DELETE)
//    @RequiresPermissions("patrol:plan:delete")
//    @DeleteMapping("/{planIds}")
//    public AjaxResult remove(@PathVariable Long[] planIds) {
//        return toAjax(patrolPlanService.deletePatrolPlanByPlanIds(planIds));
//    }
//}
//
