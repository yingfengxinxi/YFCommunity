package com.wr.api.estate.mapper;

import com.wr.remote.domain.Community;
import com.wr.remote.domain.Estate;
import com.wr.remote.domain.SysDept;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.manage.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 13:15:51
 * @Desc: 登录数据层
 */
public interface LoginMapper {
    /**
     * 根据登录名查询用户
     *
     * @param loginName 登录名
     * @return com.wr.remote.domain.SysUser
     */
    SysUser selectUserByLoginName(String loginName);

    /**
     * 修改登录数据
     *
     * @param sysUser 登录用户
     * @return java.lang.Integer
     */
    Integer updateLoginData(SysUser sysUser);

    /**
     * 根据当前登录人所在id查询部门
     *
     * @param deptId 登录人所在id
     * @return com.wr.remote.domain.SysDept
     */
    SysDept selectDeptByDeptId(Long deptId);

    /**
     * 根据 dept_id 查询对应的物业
     *
     * @param deptId dept_id
     * @return {@link Estate}
     * @ver v1.0.0
     */
    Estate selectEstateByDeptId(Long deptId);

    /**
     * 查询当前登录人所在物业id
     *
     * @param deptId 当前登录人所在物业id
     * @return java.lang.Long
     */
    Long getCurrentEstateId(Long deptId);

    /**
     * 根据 userId 查询对应的员工信息
     *
     * @param userId 用户账号id
     * @return com.wr.remote.estate.manage.Staff
     */
    Staff selectStaffByUserId(Long userId);

    /**
     * 根据小区id查询物业
     *
     * @param communityId 小区id
     * @return com.wr.remote.domain.Estate
     */
    Estate selectEstateIdByCommunityId(Long communityId);

    /**
     * 根据id查询物业
     *
     * @param estateId 物业id
     * @return com.wr.remote.domain.Estate
     */
    Estate selectEstateById(Long estateId);

    /**
     * 根据物业id查询小区
     *
     * @param estateId 物业id
     * @return java.util.List<com.wr.remote.domain.Community>
     */
    List<Community> selectCommunityList(Long estateId);

    /**
     * 根据身份证号查询员工
     *
     * @param cardNo 员工身份证号
     * @return com.wr.remote.estate.manage.Staff
     */
    Staff selectStaffByCardNo(String cardNo);

    /**
     * 修改密码
     *
     * @param userId   账号id
     * @param password 密码
     * @return java.lang.Integer
     */
    Integer updatePassword(@Param("userId") Long userId, @Param("password") String password);

    /**
     * @param userName
     * @return
     */
    SysUser getUserNameNikeName(@Param("userName") String userName);

    SysUser getByUserIdUser(@Param("userId") Long userId);

}
