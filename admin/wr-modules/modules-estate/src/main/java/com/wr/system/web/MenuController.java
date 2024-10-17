package com.wr.system.web;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.SysMenu;
import com.wr.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单信息
 * 
 * @author wr
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController
{
    @Autowired
    private MenuService menuService;


    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/tree")
    public AjaxResult treeselect() {
        List<SysMenu> list = menuService.selectMenuList();
        return AjaxResult.success(menuService.buildMenuTreeSelect(list));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTree/{roleId}")
    public AjaxResult roleMenuTree(@PathVariable("roleId") Long roleId) {
        List<SysMenu> menus = menuService.selectMenuList();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

}