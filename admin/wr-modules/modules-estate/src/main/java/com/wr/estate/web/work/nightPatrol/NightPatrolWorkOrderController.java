//package com.wr.estate.web.work.nightPatrol;
//
//import com.wr.common.core.web.controller.BaseController;
//import com.wr.common.core.web.domain.AjaxResult;
//import com.wr.common.core.web.page.TableDataInfo;
//import com.wr.common.log.annotation.Log;
//import com.wr.common.log.enums.BusinessType;
//import com.wr.common.security.annotation.RequiresPermissions;
//import com.wr.estate.service.work.nightPatrol.NightPatrolWorkOrderService;
//import com.wr.remote.work.nightPatrol.Watchman;
//import com.wr.remote.work.nightPatrol.vo.NightPatrolOrderListVO;
//import com.wr.remote.work.nightPatrol.vo.NightPatrolOrderQueryVO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * 巡更工单
// *
// * @Author: 奔波儿灞
// * @Date: 2022-10-27 14:25:03
// * @Desc:
// */
//@RestController
//@RequestMapping("/work/nightPatrolOrder")
//public class NightPatrolWorkOrderController extends BaseController {
//
//    @Autowired
//    private NightPatrolWorkOrderService nightPatrolWorkOrderService;
//
//    /**
//     * 查询巡更工单列表
//     */
//    @GetMapping("/list")
//    public TableDataInfo list(Watchman watchman) {
//        startPage();
//        List<Watchman> list = nightPatrolWorkOrderService.selectWatchmanList(watchman);
//        return getDataTable(list);
//    }
//
//    /**
//     * 获取巡更工单详细信息
//     */
//    @GetMapping(value = "/{watchmanId}")
//    public AjaxResult getInfo(@PathVariable("watchmanId") Long watchmanId) {
//        return AjaxResult.success(nightPatrolWorkOrderService.selectWatchmanByWatchmanId(watchmanId));
//    }
//
//    /**
//     * 查询工单详情
//     *
//     * @param id) 工单id
//     * @return com.wr.common.core.web.domain.AjaxResult
//     * @author yx
//     * @date 2022/11/3 16:41
//     */
//    @GetMapping(value = "/detail/{id}")
//    public AjaxResult getDetail(@PathVariable("id") Long id) {
//        return AjaxResult.success(nightPatrolWorkOrderService.getDetail(id));
//    }
//
//    /**
//     * 新增巡更工单
//     */
//    @Log(title = "巡更工单", businessType = BusinessType.INSERT)
//    @RequiresPermissions("nightPatrol:order:add")
//    @PostMapping
//    public AjaxResult add(@RequestBody Watchman watchman) {
//        nightPatrolWorkOrderService.insertWatchman(watchman);
//        return success();
//    }
//
//    /**
//     * 修改巡更工单
//     */
//    @Log(title = "巡更工单", businessType = BusinessType.UPDATE)
//    @RequiresPermissions("nightPatrol:order:edit")
//    @PutMapping
//    public AjaxResult edit(@RequestBody Watchman watchman) {
//        return toAjax(nightPatrolWorkOrderService.updateWatchman(watchman));
//    }
//
//    /**
//     * 巡更工单分配
//     *
//     * @param watchman 分配信息
//     * @return com.wr.common.core.web.domain.AjaxResult
//     * @author yx
//     * @date 2022/11/3 13:47
//     */
//    @Log(title = "巡更工单分配", businessType = BusinessType.UPDATE)
//    @RequiresPermissions("nightPatrol:order:allot")
//    @PutMapping("/allot")
//    public AjaxResult allot(@RequestBody Watchman watchman) {
//        nightPatrolWorkOrderService.updateStaff(watchman);
//        return success();
//    }
//
//    /**
//     * 删除巡更工单
//     */
//    @Log(title = "巡更工单", businessType = BusinessType.DELETE)
//    @RequiresPermissions("nightPatrol:order:delete")
//    @DeleteMapping("/{watchmanIds}")
//    public AjaxResult remove(@PathVariable Long[] watchmanIds) {
//        return toAjax(nightPatrolWorkOrderService.deleteWatchmanByWatchmanIds(watchmanIds));
//    }
//
//    /**
//     * 分页查询列表
//     *
//     * @param query
//     * @return com.wr.common.core.web.page.TableDataInfo
//     * @author yx
//     * @date 2022/11/3 17:42
//     */
//    @GetMapping("/listPage")
//    public TableDataInfo listPage(NightPatrolOrderQueryVO query) {
//        startPage();
//        List<NightPatrolOrderListVO> listPage = nightPatrolWorkOrderService.listPage(query);
//        return getDataTable(listPage);
//    }
//
//
//}
