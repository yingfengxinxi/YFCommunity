package com.wr.system.business.service.impl;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.StringUtils;
import com.wr.remote.domain.SysUser;
import com.wr.system.business.domain.SysUserVO;
import com.wr.system.business.mapper.PropUserMapper;
import com.wr.system.business.service.PropUserService;
import com.wr.remote.domain.SysUserRole;
import com.wr.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-09-22 16:47:41
 * @Desc:
 */
@Service
public class PropUserServiceImpl implements PropUserService {

    @Autowired
    private PropUserMapper propUserMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;


    @Override
    public List<SysUserVO> selectPropUserList(SysUser user) {
        return propUserMapper.selectPropUserList(user);
    }

    @Override
    public SysUser selectPropUserById(Long userId) {
        return propUserMapper.selectPropUserById(userId);
    }

    @Override
    public String checkUserNameUnique(String userName) {
        int count = propUserMapper.checkUserNameUnique(userName);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkPhoneUnique(SysUser user) {
        SysUser info = propUserMapper.checkPhoneUnique(user);
        if (StringUtils.isNotNull(info)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertPropUser(SysUser user) {
        //默认是10
        user.setUserType("10");
        // 新增用户信息
        int resultUser = propUserMapper.insertPropUser(user);
        // 新增用户与角色管理
        int resultRole = userRoleMapper.insertUserRoleInfo(user.getUserId(), user.getRoleId());
//        insertUserRole(user);
        return resultRole+resultUser;
    }

    @Override
    public int updatePropUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        userRoleMapper.insertUserRoleInfo(user.getUserId(),user.getRoleId());
        // 修改员工信息
//        propUserMapper.updateStaff(user.getNickName(),user.getPhonenumber(),user.getUserName());
        return propUserMapper.updatePropUser(user);
    }

    @Transactional(readOnly = false)
    @Override
    public int resetPwd(SysUser user) {
        return propUserMapper.resetPassword(user);
    }

    @Transactional(readOnly = false)
    @Override
    public int updateUserStatus(SysUser user) {
        return propUserMapper.updateUserStatus(user);
    }

    @Override
    public int deleteUserByIds(Long[] userIds) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        return propUserMapper.deletePropUserByIds(userIds);
    }


    private void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }
}
