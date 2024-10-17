package com.wr.system.business.controller;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.SysRole;
import com.wr.system.business.domain.RoleVerVO;
import com.wr.system.business.service.RoleVerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-09-22 11:25:30
 * @Desc: 物业权限管理控制层
 */
@RestController
@RequestMapping("/role_ver")
public class RoleVerController extends BaseController {

    @Autowired
    private RoleVerService roleVerService;

    /**
     * 查询列表数据
     * @param propRole
     * @return
     */
    @RequiresPermissions("system:role_ver:list")
    @GetMapping("/list")
    public TableDataInfo list(RoleVerVO propRole) {
        startPage();
        List<RoleVerVO> list = roleVerService.selectProRoleList(propRole);
        return getDataTable(list);
    }

    /**
     * 查询服务版本数据
     * @return
     */
    @RequiresPermissions("system:role_ver:list")
    @GetMapping("/ver_list")
    public AjaxResult verList() {
        List<RoleVerVO> list = roleVerService.selectRoleVersionList();
        return AjaxResult.success(list);
    }


    /**
     * 新增角色
     */
    @RequiresPermissions("system:role_ver:add")
    @Log(title = "物业权限管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RoleVerVO propRole) {
        if (UserConstants.NOT_UNIQUE.equals(roleVerService.checkRoleNameUnique(propRole))) {
            return AjaxResult.error("新增角色'" + propRole.getRoleName() + "'失败，角色名称已存在");
        }
        propRole.setCreateBy(SecurityUtils.getUsername());
        return toAjax(roleVerService.insertPropRole(propRole));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("system:role_ver:edit")
    @Log(title = "物业权限管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysRole sysRole) {

        return toAjax(roleVerService.updatePropRoleStatus(sysRole));
    }

    /**
     * 根据角色编号获取详细信息
     */
    //@RequiresPermissions("system:role_ver:query")
    @GetMapping(value = "/{roleId}")
    public AjaxResult getInfo(@PathVariable Long roleId) {

        return AjaxResult.success(roleVerService.selectPropRoleById(roleId));
    }

    /**
     * 修改保存角色
     */
    @RequiresPermissions("system:role_ver:edit")
    @Log(title = "物业权限管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RoleVerVO propRole) {
        if (UserConstants.NOT_UNIQUE.equals(roleVerService.checkRoleNameUnique(propRole))) {
            return AjaxResult.error("修改角色'" + propRole.getRoleName() + "'失败，角色名称已存在");
        }
        propRole.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(roleVerService.updatePropRole(propRole));
    }

    /**
     * 删除角色
     */
    @RequiresPermissions("system:role_ver:remove")
    @Log(title = "物业权限管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public AjaxResult remove(@PathVariable Long[] roleIds) {

        return toAjax(roleVerService.deletePropRoleByIds(roleIds));
    }

}
