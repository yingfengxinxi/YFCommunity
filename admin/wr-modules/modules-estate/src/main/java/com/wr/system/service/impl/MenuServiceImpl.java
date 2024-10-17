package com.wr.system.service.impl;

import com.wr.common.customize.util.TreeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.SysMenu;
import com.wr.remote.domain.SysRole;
import com.wr.remote.domain.SysUser;
import com.wr.remote.domain.TreeSelect;
import com.wr.system.mapper.MenuMapper;
import com.wr.system.mapper.RoleMapper;
import com.wr.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单 业务层处理
 * 
 * @author wr
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据用户查询系统菜单列表
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList() {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if("10".equals(sysUser.getUserType())){
            return menuMapper.selectMenuListByVer(sysUser.getUserId());
        }
        return menuMapper.selectMenuListByRole(sysUser.getUserId());
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return menuMapper.selectMenuListByRoleId(roleId, role.isMenuCheckStrictly());
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus)
    {
        List<SysMenu> menuTrees = TreeUtils.buildLongTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

}
