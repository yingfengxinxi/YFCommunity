package com.wr.system.business.mapper;

import com.wr.remote.domain.SysRole;
import com.wr.system.business.domain.RoleVerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-09-22 11:49:11
 * @Desc:
 */
public interface RoleVerMapper {

    /**
     * 列表查询
     * @param propRole
     * @return
     */
    List<RoleVerVO> selectProRoleList(RoleVerVO propRole);

    /**
     * 修改角色状态
     * @param sysRole
     * @return
     */
    int updatePropRoleStatus(SysRole sysRole);

    /**
     * 根据角色名查询数据
     * @param roleName
     * @return
     */
    RoleVerVO checkRoleNameUnique(String roleName);

    /**
     * 获取下一个自增id
     * @return
     */
    Long getAutoIncrement();

    /**
     * 添加数据
     * @param propRole
     * @return
     */
    void insertPropRole(RoleVerVO propRole);

    /**
     * 添加服务版本-角色关联
     * @param roleId
     * @param versionId
     * @return
     */
    Integer insertVerRole(@Param("roleId")Long roleId, @Param("versionId")Long versionId);

    /**
     * 根据ID查询菜单树信息
     * @param versionId
     * @return
     */
    List<Long> selectMenuListByVersionId(Long versionId);

    /**
     * 查询数据列表
     * @return
     */
    List<RoleVerVO> selectRoleVersionList();


    /**
     * 根据id查询数据
     * @param roleId
     * @return
     */
    RoleVerVO selectPropRoleById(Long roleId);

    /**
     * 通过角色ID查询角色使用数量
     * @param roleId
     * @return
     */
    int countPropUserRoleByRoleId(Long roleId);

    /**
     * 删除服务版本-角色关联
     * @param roleIds
     * @return
     */
    Integer deleteVerRole(Long[] roleIds);

    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    Integer deletePropRoleByIds(Long[] roleIds);

    /**
     * 修改角色信息
     * @param propRole
     * @return
     */
    Integer updatePropRole(RoleVerVO propRole);
}
