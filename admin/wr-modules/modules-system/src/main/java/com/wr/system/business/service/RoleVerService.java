package com.wr.system.business.service;

import com.wr.remote.domain.SysRole;
import com.wr.system.business.domain.RoleVerVO;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-09-22 11:42:16
 * @Desc:
 */
public interface RoleVerService {

    /**
     * 查询列表数据
     * @param propRole
     * @return
     */
    List<RoleVerVO> selectProRoleList(RoleVerVO propRole);

    /**
     * 修改角色信息
     * @param sysRole
     * @return
     */
    int updatePropRoleStatus(SysRole sysRole);

    /**
     * 校验角色名称是否唯一
     * @param propRole
     * @return
     */
    String checkRoleNameUnique(RoleVerVO propRole);

    /**
     * 添加数据
     * @param propRole
     * @return
     */
    int insertPropRole(RoleVerVO propRole);

    /**
     * 查询数据列表
     * @return
     */
    List<RoleVerVO> selectRoleVersionList();

    /**
     * 删除
     * @param roleIds
     * @return
     */
    int deletePropRoleByIds(Long[] roleIds);

    /**
     * 根据id查询数据
     * @param roleId
     * @return
     */
    RoleVerVO selectPropRoleById(Long roleId);

    /**
     * 修改数据
     * @param propRole
     * @return
     */
    Integer updatePropRole(RoleVerVO propRole);
}
