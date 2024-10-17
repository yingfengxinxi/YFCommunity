package com.wr.system.mapper;

import com.wr.remote.domain.*;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-09-18 11:42:51
 * @Desc:
 */
public interface EstateLoginMapper {
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
     * 根据id查询物业对应的部门
     * @param estateId 物业id
     * @return {@link Estate}
     * @ver v1.0.0
     */
    SysDept selectDeptByEstateId(Long estateId);

    /**
     * 根据用户ID查询角色
     * @param userId
     * @return java.util.List<com.wr.remote.domain.SysRole>
     */
    List<SysRole> selectRoleListByUserId(Long userId);

    /**
     * 根据角色ID查询权限
     * @param roleId
     * @return java.util.List<java.lang.String>
     */
    List<String> selectMenuPermsByRoleId(Long roleId);

    /**
     * 根据角色ID查询权限
     * @param roleId
     * @return java.util.List<java.lang.String>
     */
    List<String> selectMenuPermsByUserRoleId(Long roleId);



    /**
     * 根据用户ID查询权限
     * @param userId
     * @return java.util.List<java.lang.String>
     */
    List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询权限
     * @param userId
     * @return java.util.List<java.lang.String>
     */
    List<String> selectMenuPermsByRoleUserId(Long userId);


    /**
     * 查询所有菜单
     * @return java.util.List<com.wr.remote.domain.SysMenu>
     */
    List<SysMenu> selectMenuTreeAll();

    /**
     * 根据用户查询菜单
     * @param userId
     * @return java.util.List<com.wr.remote.domain.SysMenu>
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 根据用户查询菜单
     * @param userId
     * @return java.util.List<com.wr.remote.domain.SysMenu>
     */
    List<SysMenu> selectMenuTreeByRoleUserId(Long userId);

}
