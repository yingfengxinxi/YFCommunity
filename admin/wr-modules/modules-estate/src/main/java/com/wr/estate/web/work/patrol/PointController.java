//package com.wr.estate.web.work.patrol;
//
//import com.wr.common.core.web.controller.BaseController;
//import com.wr.common.core.web.domain.AjaxResult;
//import com.wr.common.core.web.page.TableDataInfo;
//import com.wr.common.customize.util.QrCodeUtils;
//import com.wr.common.security.utils.SecurityUtils;
//import com.wr.estate.service.work.patrol.PointService;
//import com.wr.remote.work.patrol.Point;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @Author lvzy
// * @Date 2022/10/26 10:35
// * @Version 1.5
// */
//@RestController
//@RequestMapping("/point")
//public class PointController extends BaseController {
//    @Autowired
//    private PointService pointService;
//
//    /**
//     * 分页查询
//     * @param point
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('estate:point:list')")
//    @GetMapping("/list")
//    public TableDataInfo selectList(Point point){
//        startPage();
//        List<Point> list=pointService.selectList(point);
//        return getDataTable(list);
//    }
//
//
//
//    /**
//     * 批量删除
//     * @param pointIds
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('estate:point:remove')")
////    @PreAuthorize("@ss.hasPermi('estate:vehicle:remove')")
////    @Log(title = "车辆批量删除", businessType = BusinessType.DELETE)
//    @PostMapping("/removes")
//    public AjaxResult removes(@RequestBody Long[] pointIds) {
//        Integer removes = pointService.delete(pointIds);
//        return AjaxResult.success(removes);
//    }
//
//    @PreAuthorize("@ss.hasPermi('estate:point:remove')")
//    @PostMapping("/remove")
//    public AjaxResult remove( Long pointId) {
//        Integer remove = pointService.delete1(pointId);
//        return AjaxResult.success(remove);
//    }
//
//
//
//    /**
//     * 根据id查询
//     * @param pointId
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('estate:point:query')")
//    @GetMapping("/{pointId}")
//    public AjaxResult selectById(@PathVariable("pointId") long pointId){
//        Point point=pointService.selectById(pointId);
//        return AjaxResult.success(point);
//    }
//
//
//
//
//    /**
//     * 更改状态
//     * @param point
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('estate:point:edit')")
//    @PostMapping("/status")
//    public AjaxResult updateStatus(@RequestBody  Point point){
//        int row =pointService.updateStatus(point);
//        return toAjax(row);
//    }
//
//
//    @PreAuthorize("@ss.hasPermi('estate:point:add')")
//    @PostMapping("/addUpdate")
//    public AjaxResult addUpdate (@RequestBody Point point)throws Exception{
//
//        //校验巡点名称是否存在
//        int count = pointService.isExist(point.getPointName(),point.getCommunityId());
//        if (point.getPointId() == null) {
//            if (count > 0) {
//                return AjaxResult.error("添加失败，'"+ point.getPointName() + "'巡点名称已存在");
//            }
//        } else {
//            if (count > 1) {
//                return AjaxResult.error("修改失败，'"+ point.getPointName() + "'巡点名称已存在");
//            }
//        }
//
//        String Name = SecurityUtils.getUsername();
//        point.setCreateBy(Name);
//        point.setUpdateBy(Name);
//        int row=pointService.saveUpdate(point);
//        return AjaxResult.success(row);
//    }
//
//
//
//}
