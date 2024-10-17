package com.wr.govern.web.manage;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.service.manage.GridService;
import com.wr.govern.service.security.CameraService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.govern.association.vo.DonationVO;
import com.wr.remote.govern.manage.Grid;
import com.wr.remote.govern.manage.vo.GridVO;
import com.wr.remote.govern.security.Camera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 09:20:10
 * @Desc: 网格管理控制层
 */
@RestController
@RequestMapping("/grid")
public class GridController extends BaseController {

    @Autowired
    private GridService gridService;

    /**
     * 获取网格管理集合
     * @param grid
     * @return
     */
    @RequiresPermissions("govern:grid:list")
    @GetMapping("/list")
    public TableDataInfo getList(GridVO grid){
        startPage();
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        grid.setEstateId(estateId);
        List<GridVO> gridList=gridService.getList(grid);
        return getDataTable(gridList);
    }

    /**
     * 添加网格管理信息
     * @param gridVO
     * @return
     */
    @RequiresPermissions("govern:grid:add")
    @Log(title = "添加网格管理信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addGrid(@RequestBody GridVO gridVO){
        return toAjax(gridService.addGrid(gridVO));
    }

    /**
     * 修改网格管理信息
     * @param gridVO
     * @return
     */
    @RequiresPermissions("govern:grid:edit")
    @Log(title = "修改网格管理信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateGrid(@RequestBody GridVO gridVO){
        return toAjax(gridService.updateGrid(gridVO));
    }

    /**
     * 网格管理详情
     * @param gridId
     * @return
     */
    @RequiresPermissions("govern:grid:list")
    @GetMapping("/{gridId}")
    public AjaxResult getGridDetail(@PathVariable Long gridId){
        return AjaxResult.success(gridService.getGridDetail(gridId));
    }

    /**
     * 删除网格管理
     * @param gridId
     * @return
     */
    @RequiresPermissions("govern:grid:remove")
    @Log(title = "删除网格管理信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{gridId}")
    public AjaxResult delGrid(@PathVariable Long gridId){
        return toAjax(gridService.delGrid(gridId));
    }

    /**
     * 获取员工信息集合
     * @param
     * @return
     */
    @RequiresPermissions("govern:grid:list")
    @GetMapping("/staffList/{communityId}")
    public AjaxResult staffList(@PathVariable Long communityId){
        startPage();
        List<Staff> staffList=gridService.selectStaffList(communityId);
        return AjaxResult.success(staffList);
    }

}
