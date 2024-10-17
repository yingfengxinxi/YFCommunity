//package com.wr.estate.web.work.patrol;
//
//import com.wr.common.core.web.controller.BaseController;
//import com.wr.common.core.web.domain.AjaxResult;
//import com.wr.common.core.web.page.TableDataInfo;
//import com.wr.common.security.utils.SecurityUtils;
//import com.wr.estate.service.work.patrol.RouteService;
//import com.wr.remote.work.patrol.Point;
//import com.wr.remote.work.patrol.Route;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @Author lvzy
// * @Date 2022/10/27 13:28
// * @Version 1.5
// */
//@RestController
//@RequestMapping("/route")
//
//public class RouteController extends BaseController {
//
//
//    @Autowired
//    private RouteService service;
//
//    /**
//     * 分页查询
//     *
//     * @param route
//     * @return
//     */
//    @GetMapping("/list")
//    @PreAuthorize("@ss.hasPermi('estate:route:list')")
//    public TableDataInfo selectList(Route route) {
//        startPage();
//        List<Route> list = service.selectList(route);
//        return getDataTable(list);
//    }
//
//
//    /**
//     * 批量删除
//     *
//     * @param routeIds
//     * @return
//     */
////    @PreAuthorize("@ss.hasPermi('estate:vehicle:remove')")
////    @Log(title = "车辆批量删除", businessType = BusinessType.DELETE)
//    @PreAuthorize("@ss.hasPermi('estate:route:remove')")
//    @PostMapping("/removes")
//    public AjaxResult removes(@RequestBody Long[] routeIds) {
//        Integer removes = service.delete(routeIds);
//        return AjaxResult.success(removes);
//    }
//
//    /**
//     * 巡更路线列表
//     *
//     * @param communityId 社区id
//     * @return com.wr.common.core.web.domain.AjaxResult
//     * @author yx
//     * @date 2022/11/1 16:11
//     */
//    @GetMapping("/listRoutes/{communityId}")
//    public AjaxResult listRoutes(@PathVariable("communityId") Long communityId) {
//        List<Route> routes = service.listRoutes(communityId);
//        return AjaxResult.success(routes);
//    }
//
//    /**
//     * 单个删除
//     *
//     * @param routeId
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('estate:route:remove')")
//    @PostMapping("/remove")
//    public AjaxResult remove(Long routeId) {
//        Integer remove = service.delete(routeId);
//        return AjaxResult.success(remove);
//    }
//
//    /**
//     * 根据id查询
//     *
//     * @param routeId
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('estate:route:query')")
//    @GetMapping("/{routeId}")
//    public AjaxResult selectById(@PathVariable("routeId") long routeId) {
//        Route route = service.selectById(routeId);
//        return AjaxResult.success(route);
//    }
//
//    /**
//     * 更改状态
//     *
//     * @param route
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('estate:route:edit')")
//    @PostMapping("/status")
//    public AjaxResult updateStatus(@RequestBody Route route) {
//        int row = service.updateStatus(route);
//        return toAjax(row);
//    }
//
//    /**
//     * 新增和修改
//     *
//     * @param route
//     * @return
//     * @throws Exception
//     */
//    @PreAuthorize("@ss.hasPermi('estate:route:add')")
//    @PostMapping("/addUpdate")
//    public AjaxResult addUpdate(@RequestBody Route route) throws Exception {
//        String Name = SecurityUtils.getUsername();
//        route.setCreateBy(Name);
//        route.setUpdateBy(Name);
//        //校验巡更路线名称不能重复
//        int count = service.checkRouteNameUnique(route.getRouteName());
//        if (route.getRouteId() == null) {
//            if (count > 0) {
//                return AjaxResult.error("添加失败，'"+ route.getRouteName() + "'巡更路线名称已存在");
//            }
//        } else {
//            if (count > 1) {
//                return AjaxResult.error("修改失败，'"+ route.getRouteName() + "'巡更路线名称已存在");
//            }
//        }
//        int row = service.saveUpdate(route);
//        return AjaxResult.success("操作成功", row);
//    }
//
//
//    /**
//     * 新增页面根据小区id查询巡点集合
//     *
//     * @param
//     * @return
//     */
//    @GetMapping("/point1/{communityId}")
//    public List<Point> selectPointList1(@PathVariable("communityId") Long communityId) {
//        List<Point> list = service.selectPointList1(communityId);
//        return list;
//    }
//
//    /**
//     * 点击事件查询巡点详细信息
//     *
//     * @param
//     * @return
//     */
//    @GetMapping("/point/{pointId}")
//    public Point selectPoint(@PathVariable("pointId") Long pointId) {
//        return service.selectPoint(pointId);
//    }
//
//    /**
//     * 查看页面根据id 查巡点集合
//     */
//    @GetMapping("pointlist")
//    public TableDataInfo selectPointList(Route route) {
//        startPage();
//        List<Point> list = service.selectPointList(route);
//        return getDataTable(list);
//    }
//
//    /**
//     * 查看页面根据id 查巡点集合
//     */
//    @GetMapping("pointlist/{routeId}")
//    public List<Point> selectPointList(@PathVariable("routeId") Long routeId) {
//        List<Point> list = service.selectPointListUrl(routeId);
//        return list;
//    }
//}
