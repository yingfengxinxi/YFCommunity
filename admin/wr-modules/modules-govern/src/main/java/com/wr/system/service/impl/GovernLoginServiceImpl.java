package com.wr.system.service.impl;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.*;
import com.wr.remote.util.MenuUtil;
import com.wr.system.mapper.GovernLoginMapper;
import com.wr.system.service.GovernLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: RainCity
 * @Date: 2022-09-18 11:42:30
 * @Desc:
 */
@Service
public class GovernLoginServiceImpl implements GovernLoginService {
    @Autowired
    private GovernLoginMapper loginMapper;

    @Override
    public Integer insertLoginInfo(SysLogininfor logininfor) {
        return loginMapper.insertLoginInfo(logininfor);
    }

    @Override
    public SysUser selectUserById(Long userId) {
        SysUser sysUser = loginMapper.selectUserById(userId);
        setEstateAndDept(sysUser);
        List<SysRole> roleList = loginMapper.selectRoleListByUserId(sysUser.getUserId());
        sysUser.setRoles(roleList);
        return sysUser;
    }

    @Override
    public SysUser selectUserByUserName(String username) {
        SysUser sysUser = loginMapper.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser)) {
            return null;
        }
        setEstateAndDept(sysUser);
        List<SysRole> roleList = loginMapper.selectRoleListByUserId(sysUser.getUserId());
        sysUser.setRoles(roleList);
        return sysUser;
    }

    private void setEstateAndDept(SysUser sysUser){
        // 物业管理员==> user_type="10"
        if("10".equals(sysUser.getUserType())){
            sysUser.setEstateId(sysUser.getDeptId());
            SysDept sysDept = loginMapper.selectDeptByEstateId(sysUser.getEstateId());
            if(null != sysDept){
                sysUser.setDeptId(sysDept.getDeptId());
                sysUser.setDept(sysDept);
            }
            return;
        }
        // 物业自建员工==> user_type="11"
        if("11".equals(sysUser.getUserType())){
            SysDept sysDept = loginMapper.selectDeptByDeptId(sysUser.getDeptId());
            sysUser.setDept(sysDept);
            Long estateId = selectEstateIdByDeptId(sysDept);
            sysUser.setEstateId(estateId);
        }
    }

    private Long selectEstateIdByDeptId(SysDept sysDept) {
        if("10".equals(sysDept.getDeptType())){
            return loginMapper.selectEstateIdByDeptId(sysDept.getDeptId());
        }
        SysDept temp = loginMapper.selectDeptByDeptId(sysDept.getParentId());

        return selectEstateIdByDeptId(temp);
    }

    @Override
    public Set<String> getRolePermission(SysUser sysUser) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (sysUser.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(selectRoleListByUserId(sysUser.getUserId()));
        }
        return roles;
    }

    @Override
    public Set<String> getMenuPermission(SysUser sysUser) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (sysUser.isAdmin()) {
            perms.add("*:*:*");
        } else {
            List<SysRole> roles = loginMapper.selectRoleListByUserId(sysUser.getUserId());
            if (!roles.isEmpty() && roles.size() > 1) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRole role : roles) {
                    Set<String> rolePerms = selectMenuPermsByRoleId(sysUser, role.getRoleId());
                    role.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            } else {
                perms.addAll(selectMenuPermsByUserId(sysUser));
            }
        }
        return perms;
    }

    @Override
    public List<SysMenu> selectMenuTreeByUserId() {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        List<SysMenu> menus = null;
        if (SecurityUtils.isAdmin(sysUser.getUserId())) {
            menus = loginMapper.selectMenuTreeAll();
        } else {
            menus = loginMapper.selectMenuTreeByUserId(sysUser.getUserId());
            if("11".equals(sysUser.getUserType())){
                menus = loginMapper.selectMenuTreeByRoleUserId(sysUser.getUserId());
            }
        }
        return MenuUtil.getChildPerms(menus, 0);
    }

    private Set<String> selectRoleListByUserId(Long userId) {
        List<SysRole> perms = loginMapper.selectRoleListByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据角色ID查询权限
     * @param roleId 角色ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByRoleId(SysUser sysUser, Long roleId) {
        List<String> perms = loginMapper.selectMenuPermsByRoleId(roleId);
        if("11".equals(sysUser.getUserType())){
            perms = loginMapper.selectMenuPermsByUserRoleId(roleId);
        }
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询权限
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(SysUser sysUser) {
        List<String> perms = loginMapper.selectMenuPermsByUserId(sysUser.getUserId());
        if("11".equals(sysUser.getUserType())){
            perms = loginMapper.selectMenuPermsByRoleUserId(sysUser.getUserId());
        }
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }
}
