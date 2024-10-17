package com.wr.estate.web.customer.Venue;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.estate.entity.VenueEntity;
import com.wr.estate.entity.vo.VenueVo;
import com.wr.estate.service.customer.Venue.VenueService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 场馆信息Controller
 *
 * @author bajie
 * @date 2022-10-25
 */
@RestController
@RequestMapping("/venue")
public class VenueController extends BaseController
{
    @Autowired
    private VenueService venueService;

    /**
     * 查询场馆信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:venue:list')")
    @GetMapping("/list")
    public TableDataInfo list(VenueEntity venue)
    {
        startPage();
        List<VenueVo> list = venueService.selectVenueList(venue);
        return getDataTable(list);
    }

    /**
     * 导出场馆信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:venue:export')")
    @Log(title = "场馆信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VenueEntity venue)
    {
        List<VenueVo> list = venueService.selectVenueList(venue);
        ExcelUtil<VenueVo> util = new ExcelUtil<VenueVo>(VenueVo.class);
        util.exportExcel(response, list, "场馆信息数据");
    }

    /**
     * 获取场馆信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:venue:query')")
    @GetMapping(value = "/{venueId}")
    public AjaxResult getInfo(@PathVariable("venueId") Long venueId)
    {
        return AjaxResult.success(venueService.selectVenueByVenueId(venueId));
    }

    /**
     * 新增场馆信息
     */
    @PreAuthorize("@ss.hasPermi('system:venue:add')")
    @Log(title = "场馆信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VenueEntity venue)
    {
        return toAjax(venueService.insertVenue(venue));
    }

    /**
     * 修改场馆信息
     */
    @PreAuthorize("@ss.hasPermi('system:venue:edit')")
    @Log(title = "场馆信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VenueEntity venue)
    {
        return toAjax(venueService.updateVenue(venue));
    }

    /**
     * 删除场馆信息
     */
    @PreAuthorize("@ss.hasPermi('system:venue:remove')")
    @Log(title = "场馆信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{venueIds}")
    public AjaxResult remove(@PathVariable Long[] venueIds)
    {
        return toAjax(venueService.deleteVenueByVenueIds(venueIds));
    }

    /**
     * 获取管理员信息
     */
    @GetMapping("/g/{communityId}")
    public AjaxResult g(@PathVariable("communityId") Long communityId)
    {

        if (communityId == 0){
            return AjaxResult.error("请选择社区");
        }

        List<VenueVo> list = venueService.g(communityId);

        return AjaxResult.success(list);
    }
    /**
     * @Description 场馆管理下拉框
     * @Param [venueVo]
     * @return com.wr.common.core.web.domain.AjaxResult
     * @Date 2022/11/23 9:54
     * @Author DIANWEI
     **/
    @GetMapping("/venueList")
    public AjaxResult venueList(VenueVo venueVo)
    {
        List<VenueVo> list = venueService.downVenueList(venueVo);
        return AjaxResult.success(list);
    }




}
