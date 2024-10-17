package com.wr.system.service;

import com.wr.remote.domain.SysDept;
import com.wr.remote.domain.TreeSelect;

import java.util.List;

/**
 * 部门管理 服务层
 * 
 * @author wr
 */
public interface DeptService
{
    /**
     * 查询物业树结构信息
     * @param deptType 数据类型 all：查询10、11的数据；10：查询10的数据；11：查询11的数据
     * @return {@link List<TreeSelect>} 部门树信息集合
     * @ver v1.0.0
     */
    List<TreeSelect> selectEstateTreeList(String deptType);

    /**
     * 查询部门树结构信息
     * @param parentId
     * @return java.util.List<com.wr.remote.domain.TreeSelect>
     */
    List<TreeSelect> selectDeptTreeList(Long parentId);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<SysDept> selectDeptList(SysDept dept);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    SysDept selectDeptById(Long deptId);

    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    boolean hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkDeptExistUser(Long deptId);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int deleteDeptById(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    String checkDeptNameUnique(SysDept dept);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int insertDept(SysDept dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int updateDept(SysDept dept);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * 只针对于user_type='11'
     * 根据物业部门id查询所在物业
     * @param deptId 物业部门id
     * @return {@link Long}
     * @ver v1.0.0
     */
    Long selectDeptIdForEstate(Long deptId);
}
