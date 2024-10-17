package com.wr.estate.web.work.inspect;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.work.patrol.TourService;
import com.wr.remote.work.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 巡检类型
 * @Author: lvzy
 * @Date: 2022-11-02 19:39:28
 * @Desc: 巡检类型
 */
@RestController
@RequestMapping("/inspect/tour")
public class TourController extends BaseController {

    @Autowired
    private TourService tourService;

    /**
     * 查询巡X类型列表分页
     */
    @GetMapping("/list")
    public TableDataInfo list(Tour tour) {
        startPage();
        tour.setTourType("2");
        List<Tour> list = tourService.selectTourList(tour);
        return getDataTable(list);
    }

    @GetMapping("/lists")
    public AjaxResult lists(Tour tour) {
        List<Tour> list = tourService.selectTourList(tour);
        return AjaxResult.success(list);
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
    @PreAuthorize("@ss.hasPermi('estate:tour:add')")
    @PostMapping
    public AjaxResult add(@RequestBody Tour tour) {
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        tour.setTourType("2");
        tour.setEstateId(estateId);
        if (UserConstants.NOT_UNIQUE.equals(tourService.checkName(tour))) {
            return AjaxResult.error("此巡检类型已存在,请重新输入");
        }
        return AjaxResult.success(tourService.insertTour(tour));
    }

    /**
     * 修改巡X类型
     */
    @Log(title = "巡查类型", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('estate:tour:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody Tour tour) {
        tour.setTourType("2");
        if (UserConstants.NOT_UNIQUE.equals(tourService.checkName(tour))) {
            return AjaxResult.error("此巡检类型已存在,请重新输入");
        }
        return toAjax(tourService.updateTour(tour));
    }

    /**
     * 删除巡X类型
     */
    @Log(title = "巡查类型", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('estate:tour:remove')")
    @DeleteMapping("/{tourIds}")
    public AjaxResult remove(@PathVariable Long[] tourIds) {
        return toAjax(tourService.deleteTourByTourIds(tourIds));
    }
}
