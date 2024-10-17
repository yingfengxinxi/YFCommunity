package com.wr.system.business.service.impl;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.exception.CustomException;
import com.wr.common.core.utils.StringUtils;
import com.wr.remote.domain.SysRole;
import com.wr.system.business.domain.RoleVerVO;
import com.wr.system.business.mapper.RoleVerMapper;
import com.wr.system.business.service.RoleVerService;
import com.wr.remote.domain.SysRoleMenu;
import com.wr.system.mapper.SysRoleMenuMapper;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-09-22 11:42:30
 * @Desc:
 */
@Service
public class RoleVerServiceImpl implements RoleVerService {

    @Autowired
    private RoleVerMapper roleVerMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;


    @Override
    public List<RoleVerVO> selectProRoleList(RoleVerVO propRole) {
        return roleVerMapper.selectProRoleList(propRole);
    }

    @Override
    public int updatePropRoleStatus(SysRole sysRole) {
        return roleVerMapper.updatePropRoleStatus(sysRole);
    }

    @Override
    public String checkRoleNameUnique(RoleVerVO propRole) {
        Long roleId = StringUtils.isNull(propRole.getRoleId()) ? -1L : propRole.getRoleId();
        RoleVerVO info = roleVerMapper.checkRoleNameUnique(propRole.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional(readOnly = false)
    public int insertPropRole(RoleVerVO propRole) {
        // 新增角色信息
        Long nextId = roleVerMapper.getAutoIncrement();
        propRole.setRoleKey("roleKey"+nextId);
        propRole.setRoleId(nextId);
        roleVerMapper.insertPropRole(propRole);
        //添加服务版本-角色关联
        roleVerMapper.insertVerRole(propRole.getRoleId(),propRole.getVersionId());
        return insertRoleMenu(propRole);
    }

    @Override
    public List<RoleVerVO> selectRoleVersionList() {
        return roleVerMapper.selectRoleVersionList();
    }

    @Override
    public int deletePropRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            SysRole role = selectPropRoleById(roleId);
            int count = roleVerMapper.countPropUserRoleByRoleId(roleId);
            if (count > 0) {
                throw new CustomException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        //删除服务版本-角色关联
        roleVerMapper.deleteVerRole(roleIds);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenu(roleIds);
        return roleVerMapper.deletePropRoleByIds(roleIds);
    }

    @Override
    public RoleVerVO selectPropRoleById(Long roleId) {

        return roleVerMapper.selectPropRoleById(roleId);
    }

    @Transactional(readOnly = false)
    @Override
    public Integer updatePropRole(RoleVerVO propRole) {
        // 修改角色信息
        roleVerMapper.updatePropRole(propRole);
        Long[] roleIds = new Long[1];
        roleIds[0] = propRole.getRoleId();
        //删除服务版本-角色关联
        roleVerMapper.deleteVerRole(roleIds);
        //添加服务版本-角色关联
        roleVerMapper.insertVerRole(propRole.getRoleId(),propRole.getVersionId());
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(propRole.getRoleId());
        return insertRoleMenu(propRole);
    }

    /**
     * 新增角色菜单信息
     * @param propRole
     * @return
     */
    private int insertRoleMenu(RoleVerVO propRole) {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = Lists.newArrayList();
        List<Long> menuIds = roleVerMapper.selectMenuListByVersionId(propRole.getVersionId());
        for (Long menuId : menuIds) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(propRole.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }
}
