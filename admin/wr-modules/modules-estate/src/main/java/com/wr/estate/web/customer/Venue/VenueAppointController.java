package com.wr.estate.web.customer.Venue;

import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.VenueActEneity;
import com.wr.estate.entity.VenueAppoint;
import com.wr.estate.entity.vo.VenueVo;
import com.wr.estate.service.customer.Venue.VenueAppointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/26
 * @Description:
 * @FileName: VenueAppointController
 * @History:
 */
@RestController
@RequestMapping("/appoint")
public class VenueAppointController extends BaseController {

    @Autowired
    private VenueAppointService venueAppointService;

    /**
     * 查询场馆活动列表
     */
    @PreAuthorize("@ss.hasPermi('system:appoint:list')")
    @GetMapping("/list")
    public TableDataInfo list(VenueAppoint venueAppoint)
    {
        startPage();
        List<VenueAppoint> list = venueAppointService.selectVenueAppointList(venueAppoint);
        return getDataTable(list);
    }

    @PostMapping("/tlist")
    public AjaxResult tlist(@RequestBody VenueAppoint venueAppoint)
    {
        return AjaxResult.success(venueAppointService.tlist(venueAppoint));
    }

    /**
     * 导出场馆活动列表
     */
    @PreAuthorize("@ss.hasPermi('system:appoint:export')")
    @Log(title = "场馆活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VenueAppoint venueAppoint)
    {
        List<VenueAppoint> list = venueAppointService.selectVenueAppointList(venueAppoint);
        ExcelUtil<VenueAppoint> util = new ExcelUtil<VenueAppoint>(VenueAppoint.class);
        util.exportExcel(response, list, "场馆活动数据");
    }

    /**
     * 获取场馆活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:appoint:query')")
    @GetMapping(value = "/{appointId}")
    public AjaxResult getInfo(@PathVariable("appointId") Long appointId)
    {
        return AjaxResult.success(venueAppointService.selectVenueAppointByAppointId(appointId));
    }

    /**
     * 获取场馆活动详细信息1
     */
    @GetMapping(value = "getInfo/{appointId}")
    public AjaxResult getInfo1(@PathVariable("appointId") Long appointId)
    {
        return AjaxResult.success(venueAppointService.selectVenueAppointByAppointId1(appointId));
    }

    /**
     * 新增场馆活动
     */
    @PreAuthorize("@ss.hasPermi('system:appoint:add')")
    @Log(title = "场馆预约", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VenueAppoint venueAppoint)
    {
        int i = venueAppointService.insertVenueAppoint(venueAppoint);
        if (i == -6){
            return AjaxResult.error("该时间被预约，请重新选择");
        }
        return toAjax(i);
    }

    /**
     * 修改场馆活动
     */
    @PreAuthorize("@ss.hasPermi('system:appoint:edit')")
    @Log(title = "场馆活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VenueAppoint venueAppoint)
    {
        int i = venueAppointService.updateVenueAppoint(venueAppoint);
        if (i == -6){
            return AjaxResult.error("该时间可能被预约，或不能修改当前选择时间");
        }
        return toAjax(i);
    }

    /**
     * 删除场馆活动
     */
    @PreAuthorize("@ss.hasPermi('system:appoint:remove')")
    @Log(title = "场馆活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{appointIds}")
    public AjaxResult remove(@PathVariable Long[] appointIds)
    {
        return toAjax(venueAppointService.deleteVenueAppointByAppointIds(appointIds));
    }


    @GetMapping("/getvenue/{communityId}")
    public AjaxResult getvenue(@PathVariable("communityId") Long communityId)
    {
        List<VenueVo> list = venueAppointService.getvenue(communityId);
        return AjaxResult.success(list);
    }

    @GetMapping("/getvenue1")
    public AjaxResult getvenue1()
    {
        List<VenueVo> list = venueAppointService.getvenue1();
        return AjaxResult.success(list);
    }


    @GetMapping("/getvenueact/{communityId}")
    public AjaxResult getvenueact(@PathVariable("communityId") Long communityId)
    {
        List<VenueActEneity> list = venueAppointService.getact(communityId);
        return AjaxResult.success(list);
    }

    @PostMapping("jz")
    public AjaxResult jz(@RequestBody VenueAppoint venueAppoint)
    {
        venueAppoint.setAppointStatus("1");
        return toAjax(venueAppointService.updateVenueAppoint(venueAppoint));
    }

    @PostMapping("zz")
    public AjaxResult zz(@RequestBody VenueAppoint venueAppoint)
    {
        venueAppoint.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(venueAppointService.updateVenueAppoint(venueAppoint));
    }

    @GetMapping("/jzc/{appointId}")
    public AjaxResult jzc(@PathVariable("appointId") Long appointId)
    {
        VenueAppoint venueAppoint = venueAppointService.jzc(appointId);
        if (venueAppoint !=null){
            return AjaxResult.success(venueAppoint);
        }
        return AjaxResult.error("null");
    }


    @GetMapping("/getvenueByvenueId/{venueId}")
    public AjaxResult getvenueByvenueId(@PathVariable("venueId") Long venueId)
    {
        List<VenueAppoint> list = venueAppointService.getvenueByvenueId(venueId);
        if (list !=null){
            return AjaxResult.success(list);
        }
        return AjaxResult.error("null");
    }



    @GetMapping("/venueaz")
    public TableDataInfo venueaz(VenueAppoint venueAppoint)
    {
        startPage();
        List<VenueAppoint> list = venueAppointService.venueaz(venueAppoint);
        return getDataTable(list);
    }

    @GetMapping("/venueazById/{appointId}")
    public AjaxResult venueazById(@PathVariable("appointId") Long appointId)
    {
        VenueAppoint venueAppoint = venueAppointService.venueazById(appointId);
        return AjaxResult.success(venueAppoint);
    }

}
