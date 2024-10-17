package com.wr.estate.web.customer.Venue;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.estate.entity.VenueActEneity;
import com.wr.estate.service.customer.Venue.VenueActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/24
 * @Description:
 * @FileName: VenueActController
 * @History:
 */
@RestController
@RequestMapping("/venueact")
public class VenueActController extends BaseController {
    @Autowired
    VenueActService venueActService;
    /**
     * 查询场馆活动列表
     */
    @PreAuthorize("@ss.hasPermi('system:act:list')")
    @GetMapping("/list")
    public TableDataInfo list(VenueActEneity busVenueAct)
    {
        startPage();
        List<VenueActEneity> list = venueActService.getlist(busVenueAct);
        return getDataTable(list);
    }

    ///**
    // * 导出场馆活动列表
    // */
    //@PreAuthorize("@ss.hasPermi('system:act:export')")
    //@Log(title = "场馆活动", businessType = BusinessType.EXPORT)
    //@PostMapping("/export")
    //public void export(HttpServletResponse response, BusVenueAct busVenueAct)
    //{
    //    List<BusVenueAct> list = busVenueActService.selectBusVenueActList(busVenueAct);
    //    ExcelUtil<BusVenueAct> util = new ExcelUtil<BusVenueAct>(BusVenueAct.class);
    //    util.exportExcel(response, list, "场馆活动数据");
    //}

    /**
     * 获取场馆活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:act:query')")
    @GetMapping(value = "/{activityId}")
    public AjaxResult getInfo(@PathVariable("activityId") Long activityId)
    {
        return AjaxResult.success(venueActService.getAct(activityId));
    }

    /**
     * 新增场馆活动
     */
    @PreAuthorize("@ss.hasPermi('system:act:add')")
    @Log(title = "场馆活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VenueActEneity busVenueAct)
    {
        int i = venueActService.add(busVenueAct);
        if (i == -123){
           return AjaxResult.error("活动名称不能重复");
        }
        return toAjax(i);
    }

    /**
     * 修改场馆活动
     */
    @PreAuthorize("@ss.hasPermi('system:act:edit')")
    @Log(title = "场馆活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VenueActEneity busVenueAct)
    {
        return toAjax(venueActService.updateVenueAct(busVenueAct));
    }

    /**
     * 删除场馆活动
     */
    @PreAuthorize("@ss.hasPermi('system:act:remove')")
    @Log(title = "场馆活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds)
    {
        return toAjax(venueActService.delforeach(activityIds));
    }


}
