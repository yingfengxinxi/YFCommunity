package com.wr.estate.web.work.nightPatrol;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.patrol.TourService;
import com.wr.remote.work.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 巡更类型
 *
 * @Author: 奔波儿灞
 * @Date: 2022-10-27 14:27:10
 * @Desc:
 */
@RestController
@RequestMapping("/work/nightPatrolType")
public class NightPatrolTypeController extends BaseController {
    @Autowired
    private TourService tourService;

    /**
     * 查询巡X类型列表
     */
    @GetMapping("/list")
    public AjaxResult list(Tour tour) {
        tour.setTourType("1");
        tour.setTourStatus("0");
        List<Tour> list = tourService.selectTourList(tour);
        return AjaxResult.success(list);
    }

    /**
     * 分页查询巡更类型列表
     *
     * @param tour 查询条件
     * @return com.wr.common.core.web.page.TableDataInfo
     * @author yx
     * @date 2022/11/1 15:12
     */
    @GetMapping("/listPage")
    public TableDataInfo listPage(Tour tour) {
        startPage();
        tour.setTourType("1");
        List<Tour> list = tourService.selectTourList(tour);
        return getDataTable(list);
    }


    /**
     * 获取巡X类型详细信息
     */
    @GetMapping(value = "/{tourId}")
    public AjaxResult getInfo(@PathVariable("tourId") Long tourId) {
        return AjaxResult.success(tourService.selectTourByTourId(tourId));
    }

    /**
     * 新增巡查类型
     */
    @Log(title = "巡查类型", businessType = BusinessType.INSERT)
    @RequiresPermissions("nightPatrol:type:add")
    @PostMapping
    public AjaxResult add(@RequestBody Tour tour) {
        tour.setTourType("1");
        if (UserConstants.NOT_UNIQUE.equals(tourService.checkName(tour))) {
            return AjaxResult.error("此巡更类型已存在,请重新输入");
        }
        return toAjax(tourService.insertTour(tour));
    }

    /**
     * 修改巡X类型
     */
    @Log(title = "巡查类型", businessType = BusinessType.UPDATE)
    @RequiresPermissions("nightPatrol:type:edit")
    @PutMapping
    public AjaxResult edit(@RequestBody Tour tour) {
        if (UserConstants.NOT_UNIQUE.equals(tourService.checkName(tour))) {
            return AjaxResult.error("此巡查类型已存在,请重新输入");
        }
        return toAjax(tourService.updateTour(tour));
    }

    /**
     * 删除巡X类型
     */
    @Log(title = "巡查类型", businessType = BusinessType.DELETE)
    @RequiresPermissions("nightPatrol:type:delete")
    @DeleteMapping("/{tourIds}")
    public AjaxResult remove(@PathVariable Long[] tourIds) {
        return toAjax(tourService.deleteTourByTourIds(tourIds));
    }
}
