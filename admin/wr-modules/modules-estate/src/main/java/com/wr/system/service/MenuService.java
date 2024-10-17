package com.wr.system.service;

import com.wr.remote.domain.SysMenu;
import com.wr.remote.domain.TreeSelect;

import java.util.List;

/**
 * 菜单 业务层
 * 
 * @author wr
 */
public interface MenuService
{
    /**
     * 根据用户查询系统菜单列表
     * @return 菜单列表
     */
    List<SysMenu> selectMenuList();

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    public List<Long> selectMenuListByRoleId(Long roleId);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);

}
