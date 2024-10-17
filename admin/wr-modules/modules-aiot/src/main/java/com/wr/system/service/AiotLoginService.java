package com.wr.system.service;

import com.wr.remote.domain.SysLogininfor;
import com.wr.remote.domain.SysMenu;
import com.wr.remote.domain.SysUser;

import java.util.List;
import java.util.Set;

/**
 * @Author: RainCity
 * @Date: 2022-09-18 11:42:11
 * @Desc: 登录记录
 */
public interface AiotLoginService {
    /**
     * 添加登录记录
     * @param logininfor
     * @return java.lang.Integer
     */
    Integer insertLoginInfo(SysLogininfor logininfor);

    /**
     * 根据id查询用户
     * @param userId
     * @return com.wr.remote.domain.SysUser
     */
    SysUser selectUserById(Long userId);

    /**
     * 根据登录名查询用户信息
     * @param username
     * @return com.wr.remote.domain.SysUser
     */
    SysUser selectUserByUserName(String username);

    /**
     * 角色集合
     * @param sysUser
     * @return java.util.Set<java.lang.String>
     */
    Set<String> getRolePermission(SysUser sysUser);

    /**
     * 权限集合
     * @param sysUser
     * @return java.util.Set<java.lang.String>
     */
    Set<String> getMenuPermission(SysUser sysUser);

    /**
     * 根据用户ID查询菜单树信息
     * @return java.util.List<com.wr.remote.domain.SysMenu>
     */
    List<SysMenu> selectMenuTreeByUserId();

}
