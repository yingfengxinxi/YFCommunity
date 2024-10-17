package com.wr.system.business.controller;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.RoleVer;
import com.wr.remote.domain.SysMenu;
import com.wr.system.business.domain.ServerVerVo;
import com.wr.system.business.service.ServerVerVoService;
import com.wr.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  服务版本管理
 * @Date 2022/9/22 15:33
 * @Author DIANWEI
 **/
@RestController
@RequestMapping("/role_version")
public class ServerVerVoController extends BaseController {
    @Autowired
    private ServerVerVoService ServerVerVoService;
    @Autowired
    private ISysMenuService menuService;

    @RequiresPermissions("system:role_ver:list")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<ServerVerVo> list = ServerVerVoService.selectServerVerVoList();
        return getDataTable(list);
    }

    /**
     * 新增角色
     */
    @RequiresPermissions("system:role_ver:add")
    @Log(title = "服务版本管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ServerVerVo version) {
        if (UserConstants.NOT_UNIQUE.equals(ServerVerVoService.checkNameUnique(version)))
        {
            return AjaxResult.error("新增服务版本'" + version.getVersionName() + "'失败，名称已存在");
        }
        version.setCreateBy(SecurityUtils.getUsername());
        return toAjax(ServerVerVoService.insertServerVerVo(version));

    }

    /**
     * 加载对应菜单列表树
     */
    @GetMapping(value = "/verMenuTree/{versionId}")
    public AjaxResult roleMenuTreeselect(@PathVariable("versionId") Long versionId) {
        List<SysMenu> list = menuService.selectMenuList(SecurityUtils.getUserId());
        List<SysMenu> menus = list.stream().filter(m -> !"SYSTEM".equals(m.getPlatType())).collect(Collectors.toList());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", ServerVerVoService.selectMenuListByVersionId(versionId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    /**
     * 根据编号获取详细信息
     */
    @RequiresPermissions("system:role_ver:query")
    @GetMapping(value = "/{versionId}")
    public AjaxResult getInfo(@PathVariable("versionId") Long versionId) {
        return AjaxResult.success(ServerVerVoService.getServerVerVoById(versionId));
    }

    /**
     * 修改保存
     */
    @RequiresPermissions("system:role_ver:edit")
    @Log(title = "服务版本管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ServerVerVo version) {
        if (UserConstants.NOT_UNIQUE.equals(ServerVerVoService.checkNameUnique(version))) {
            return AjaxResult.error("修改服务版本'" + version.getVersionName() + "'失败，名称已存在");
        }
        version.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(ServerVerVoService.updateServerVerVo(version));
    }

    /**
     * 删除角色
     */
    @RequiresPermissions("system:role_ver:remove")
    @Log(title = "服务版本管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{versionId}")
    public AjaxResult remove(@PathVariable("versionId") Long versionId) {
        List<RoleVer> verRoles = ServerVerVoService.selectRoleVersByVersionId(versionId);
        if(!verRoles.isEmpty()){
            return AjaxResult.error("该服务版本已绑定角色，不允许删除");
        }
        return toAjax(ServerVerVoService.deleteServerVerVoById(versionId));
    }

}
