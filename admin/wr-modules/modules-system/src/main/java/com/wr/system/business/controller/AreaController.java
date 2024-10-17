package com.wr.system.business.controller;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.system.business.domain.AreaVo;
import com.wr.system.business.service.AreaService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2021-06-15 15:14:07
 * @Desc: 地区管理
 */
@RestController
@RequestMapping("/area")
public class AreaController extends BaseController {
    @Autowired
    private AreaService areaService;

    /**
     * 获取地区列表
     */
    @RequiresPermissions("system:area:list")
    @GetMapping("/list")
    public AjaxResult list(AreaVo area) {
        List<AreaVo> areas = areaService.selectAreaList(area);
        return AjaxResult.success(areas);
    }

    /**
     * 查询列表（排除节点）
     */
    @RequiresPermissions("system:area:list")
    @GetMapping("/exclude/district")
    public AjaxResult excludeDistrict() {
        List<AreaVo> areas = areaService.selectAreaList(new AreaVo());
        Iterator<AreaVo> it = areas.iterator();
        while (it.hasNext()) {
            AreaVo a = (AreaVo) it.next();
            String[] ancestors = StringUtils.split(a.getAncestors(), ",");
            if (ancestors.length>=3) {
                it.remove();
            }
        }
        return AjaxResult.success(areas);
    }

    /**
     * 新增地区
     */
    @RequiresPermissions("system:area:add")
    @Log(title = "地区管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AreaVo area) {
        if (UserConstants.NOT_UNIQUE.equals(areaService.checkAreaNameUnique(area))) {
            return AjaxResult.error("新增地区'" + area.getAreaName() + "'失败，地区名称已存在");
        }
        area.setCreateBy(SecurityUtils.getUsername());
        return toAjax(areaService.insertArea(area));
    }

    /**
     * 根据编号获取详细信息
     */
    @RequiresPermissions("system:area:query")
    @GetMapping(value = "/{areaId}")
    public AjaxResult getInfo(@PathVariable Long areaId) {

        return AjaxResult.success(areaService.selectAreaById(areaId));
    }

    /**
     * 查询列表（排除节点）
     */
    @RequiresPermissions("system:area:list")
    @GetMapping("/list/exclude/{areaId}")
    public AjaxResult excludeChild(@PathVariable(value = "areaId", required = false) Long areaId) {
        List<AreaVo> areas = areaService.selectAreaList(new AreaVo());
        Iterator<AreaVo> it = areas.iterator();
        while (it.hasNext()) {
            AreaVo a = (AreaVo) it.next();
            String[] ancestors = StringUtils.split(a.getAncestors(), ",");
            if (a.getAreaId().intValue() == areaId
                    || ArrayUtils.contains(ancestors, areaId + "")) {
                it.remove();
            }
        }
        return AjaxResult.success(areas);
    }

    /**
     * 修改地区
     */
    @RequiresPermissions("system:area:edit")
    @Log(title = "地区管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AreaVo area) {
        if (UserConstants.NOT_UNIQUE.equals(areaService.checkAreaNameUnique(area))) {
            return AjaxResult.error("修改地区'" + area.getAreaName() + "'失败，地区名称已存在");
        }
        else if (area.getParentId().equals(area.getAreaId())) {
            return AjaxResult.error("修改地区'" + area.getAreaName() + "'失败，上级不能是自己");
        }
        area.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(areaService.updateArea(area));
    }

    /**
     * 删除地区
     */
    @RequiresPermissions("system:area:remove")
    @Log(title = "地区管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{areaId}")
    public AjaxResult remove(@PathVariable Long areaId) {
        if (areaService.hasChildByAreaId(areaId)) {
            return AjaxResult.error("存在下级,不允许删除");
        }
        if (areaService.checkAreaExistCommunity(areaId)) {
            return AjaxResult.error("存在小区,不允许删除");
        }
        return toAjax(areaService.deleteAreaById(areaId));
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/tree")
    public AjaxResult TreeSelectVo() {
        List<AreaVo> areas = areaService.selectAreaList(new AreaVo());
        return AjaxResult.success(areaService.buildAreaTreeSelectVo(areas));
    }
}
