package com.wr.system.mapper;

import com.wr.remote.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表 数据层
 * 
 * @author wr
 */
public interface RoleMapper
{
    /**
     * 根据条件分页查询角色数据
     * 
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    List<SysRole> selectRoleList(SysRole role);

    /**
     * 通过角色ID查询角色
     * 
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    public SysRole selectRoleById(Long roleId);

    /**
     * 根据用户ID查询角色
     * 
     * @param userName 用户名
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserName(String userName);

    /**
     * 校验角色名称是否唯一
     * 
     * @param roleName 角色名称
     * @return 角色信息
     */
    public SysRole checkRoleNameUnique(@Param("roleName") String roleName, @Param("deptId") Long deptId);

    /**
     * 校验角色权限是否唯一
     * 
     * @param roleKey 角色权限
     * @return 角色信息
     */
    public SysRole checkRoleKeyUnique(@Param("roleKey") String roleKey, @Param("deptId") Long deptId);

    /**
     * 修改角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int updateRole(SysRole role);

    /**
     * 新增角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    public int insertRole(SysRole role);

    /**
     * 新增角色与物业的关联
     * @param role
     * @return java.lang.Integer
     */
    Integer insertRoleDept(SysRole role);

    /**
     * 批量删除角色信息
     * 
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    public int deleteRoleByIds(Long[] roleIds);

    /**
     * 批量删除角色部门关联信息
     *
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    int deleteRoleDept(Long[] roleIds);

    /**
     * 获取下一个自增id
     * @return
     */
    Long getAutoIncrement();

    /**
     * 根据物业id查询角色
     * @param deptId
     * @return java.util.List<com.wr.remote.domain.SysRole>
     */
    List<SysRole> selectRoleListByEstateId(Long deptId);

    List<Long> getRoleIdsByUserId(Long userId);
}
