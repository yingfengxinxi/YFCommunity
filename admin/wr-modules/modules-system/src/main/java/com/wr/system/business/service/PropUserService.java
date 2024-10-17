package com.wr.system.business.service;

import com.wr.remote.domain.SysUser;
import com.wr.system.business.domain.SysUserVO;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-09-22 16:47:29
 * @Desc:
 */
public interface PropUserService {

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
    String checkUserNameUnique(String userName);

    /**
     * 校验手机号是否唯一
     * @param user
     * @return
     */
    String checkPhoneUnique(SysUser user);

    /**
     * 添加数据
     * @param user
     * @return
     */
    int insertPropUser(SysUser user);

    /**
     * 修改数据
     * @param user
     * @return
     */
    int updatePropUser(SysUser user);

    /**
     * 修改状态
     * @param user
     * @return
     */
    int resetPwd(SysUser user);

    /**
     * 修改状态
     * @param user
     * @return
     */
    int updateUserStatus(SysUser user);

    /**
     * 批量删除
     * @param userIds
     * @return
     */
    int deleteUserByIds(Long[] userIds);
}
