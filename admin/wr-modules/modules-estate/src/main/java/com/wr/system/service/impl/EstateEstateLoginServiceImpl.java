package com.wr.system.service.impl;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.EstateMapper;
import com.wr.system.mapper.EstateLoginMapper;
import com.wr.system.service.EstateLoginService;
import com.wr.remote.domain.*;
import com.wr.remote.util.MenuUtil;
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
public class EstateEstateLoginServiceImpl implements EstateLoginService {
    @Autowired
    private EstateLoginMapper estateLoginMapper;
    @Autowired
    private EstateMapper estateMapper;

    @Override
    public Integer insertLoginInfo(SysLogininfor logininfor) {

        return estateLoginMapper.insertLoginInfo(logininfor);
    }

    @Override
    public SysUser selectUserById(Long userId) {
        SysUser sysUser = estateLoginMapper.selectUserById(userId);
        setEstateAndDept(sysUser);
        List<SysRole> roleList = estateLoginMapper.selectRoleListByUserId(sysUser.getUserId());
        sysUser.setRoles(roleList);
        return sysUser;
    }

    @Override
    public Estate selectEstateByCommunityId(Long communityId) {
        return estateMapper.selectEstateByCommunityId(communityId);
    }

    @Override
    public SysUser selectUserByUserName(String username) {
        SysUser sysUser = estateLoginMapper.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser)) {
            return null;
        }
        setEstateAndDept(sysUser);
        List<SysRole> roleList = estateLoginMapper.selectRoleListByUserId(sysUser.getUserId());
        sysUser.setRoles(roleList);
        return sysUser;
    }

    private void setEstateAndDept(SysUser sysUser){
        // 物业管理员==> user_type="10"
        if("10".equals(sysUser.getUserType()) || "12".equals(sysUser.getUserType())){
            sysUser.setEstateId(sysUser.getDeptId());
            SysDept sysDept = estateLoginMapper.selectDeptByEstateId(sysUser.getEstateId());
            if(null != sysDept){
                sysUser.setDeptId(sysDept.getDeptId());
                sysUser.setDept(sysDept);
            }
            return;
        }
        // 物业自建员工==> user_type="11"
        if("11".equals(sysUser.getUserType())){
            SysDept sysDept = estateMapper.selectDeptByDeptId(sysUser.getDeptId());
            sysUser.setDept(sysDept);
            Long estateId = selectEstateIdByDeptId(sysDept);
            sysUser.setEstateId(estateId);
        }
    }

    private Long selectEstateIdByDeptId(SysDept sysDept) {
        if("10".equals(sysDept.getDeptType())){
            return estateMapper.selectEstateIdByDeptId(sysDept.getDeptId());
        }
        SysDept temp = estateMapper.selectDeptByDeptId(sysDept.getParentId());

        return selectEstateIdByDeptId(temp);
    }

    @Override
    public Set<String> getRolePermission(SysUser sysUser) {
        Set<String> roles = new HashSet<>();
        // 管理员拥有所有权限
        if (sysUser.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(getRoleListByUserId(sysUser.getUserId()));
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
            List<SysRole> roles = estateLoginMapper.selectRoleListByUserId(sysUser.getUserId());
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
            menus = estateLoginMapper.selectMenuTreeAll();
        } else {
            menus = estateLoginMapper.selectMenuTreeByUserId(sysUser.getUserId());
            if("11".equals(sysUser.getUserType())){
                menus = estateLoginMapper.selectMenuTreeByRoleUserId(sysUser.getUserId());
            }
        }
        return MenuUtil.getChildPerms(menus, 0);
    }

    private Set<String> getRoleListByUserId(Long userId) {
        List<SysRole> perms = estateLoginMapper.selectRoleListByUserId(userId);
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
        List<String> perms = estateLoginMapper.selectMenuPermsByRoleId(roleId);
        if("11".equals(sysUser.getUserType())){
            perms = estateLoginMapper.selectMenuPermsByUserRoleId(roleId);
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
     * @param sysUser 用户
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(SysUser sysUser) {
        List<String> perms = estateLoginMapper.selectMenuPermsByUserId(sysUser.getUserId());
        if("11".equals(sysUser.getUserType())){
            perms = estateLoginMapper.selectMenuPermsByRoleUserId(sysUser.getUserId());
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
