package com.wr.system.business.mapper;

import com.wr.remote.domain.SysUser;
import com.wr.system.business.domain.SysUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-09-22 16:49:09
 * @Desc:
 */
public interface PropUserMapper {
    /**
     * 查询数据列表
     * @param user
     * @return
     */
    List<SysUserVO> selectPropUserList(SysUser user);

    /**
     * 根据id查询数据
     * @param userId
     * @return
     */
    SysUser selectPropUserById(Long userId);
    /**
     * 校验名称是否唯一
     * @param userName
     * @return
     */
    int checkUserNameUnique(String userName);

    /**
     * 根据手机号查询数据
     * @param user
     * @return
     */
    SysUser checkPhoneUnique(SysUser user);

    /**
     * 添加数据
     * @param user
     * @return
     */
    int insertPropUser(SysUser user);


    Integer updateStaff(@Param("staffName") String staffName, @Param("staffPhone") String staffPhone, @Param("accountNumber") String accountNumber);

    /**
     * 修改数据
     * @param user
     * @return
     */
    int updatePropUser(SysUser user);

    /**
     * 密码重置
     * @param user
     * @return
     */
    int resetPassword(SysUser user);

    /**
     * 修改状态
     * @param user
     * @return
     */
    int updateUserStatus(SysUser user);

    /**
     * 批量删除数据
     * @param userIds
     * @return
     */
    int deletePropUserByIds(Long[] userIds);
}
