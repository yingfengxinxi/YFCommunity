package com.wr.api.estate.service.impl.application.manage;

import com.wr.api.estate.mapper.application.info.UserLogMapper;
import com.wr.api.estate.mapper.application.manage.StaffMapper;
import com.wr.api.estate.service.application.manage.StaffService;
import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.CardNoUtil;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.manage.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 10:08:03
 * @Desc:
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private UserLogMapper userLogMapper;

    @Override
    public Staff getStaffById(Long userId) {
        SysUser sysUser = userLogMapper.selectUserById(userId);
        if (sysUser.getUserType().equals("10")) {
            //超级管理员
            Staff staff = new Staff();
            if (StringUtils.isNotEmpty(sysUser.getAvatar())) {
                staff.setAvatar(sysUser.getAvatar());
            }
            if (StringUtils.isNotEmpty(sysUser.getNickName())) {
                staff.setStaffName(sysUser.getNickName());
            }
            if (StringUtils.isNotEmpty(sysUser.getPhonenumber())) {
                staff.setStaffPhone(sysUser.getPhonenumber());
            }
            if (StringUtils.isNotEmpty(sysUser.getSex())) {
                staff.setGender(sysUser.getSex());
            }
            staff.setUserId(userId);
            return staff;
        } else {
            return staffMapper.getStaffById(userId);
        }

    }

    /**
     * 修改用户信息
     *
     * @param staff
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult updateStaff(Staff staff) {
        String username = SecurityUtils.getUsername();
        SysUser sysUser = userLogMapper.selectUserById(staff.getUserId());
        if (!sysUser.getUserType().equals("10")) {
            if (UserConstants.NOT_UNIQUE.equals(this.selectCardNum(staff))) {
                return AjaxResult.error("此身份证号已存在,请重新输入");
            }
        }

        if (StringUtils.isNotEmpty(staff.getAvatar())) {
            sysUser.setAvatar(staff.getAvatar());
        }
        if (StringUtils.isNotEmpty(staff.getStaffName())) {
            sysUser.setNickName(staff.getStaffName());
        }
        if (StringUtils.isNotEmpty(username)) {
            sysUser.setUpdateBy(username);
            sysUser.setUpdateTime(new Date());
        }

        userLogMapper.updateUser(sysUser);
        if (!sysUser.getUserType().equals("10")) {
            staff.setGender(CardNoUtil.judgeGender(staff.getCardNo()));
            staff.setUpdateTime(new Date());
            staffMapper.updateStaff(staff);
        }
        return AjaxResult.success();
    }


    /**
     * 修改员工信息密码
     *
     * @param staff 员工信息密码
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public void resetPass(Staff staff) {
        SysUser sysUser = userLogMapper.selectUserById(staff.getUserId());
        sysUser.setPassword(SecurityUtils.encryptPassword(staff.getPassword()));
        userLogMapper.updateUser(sysUser);
    }

    /**
     * 修改员工头像
     *
     * @param staff
     */
    @Override
    @Transactional(readOnly = false)
    public void resetAvatar(Staff staff) {
        SysUser sysUser = userLogMapper.selectUserById(staff.getUserId());
        sysUser.setAvatar(staff.getAvatar());
        userLogMapper.updateUser(sysUser);
        if (!sysUser.getUserType().equals("10")) {
            Staff staffById = staffMapper.getStaffById(staff.getUserId());
            staffById.setAvatar(staff.getAvatar());
            staffMapper.updateStaff(staffById);
        }
    }


    /**
     * 修改时判断手机号是否存在
     *
     * @param staff
     * @return
     */
    @Override
    public String selectPhoneNo(Staff staff) {
        int count = staffMapper.selectPhoneNo(staff);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    /**
     * 修改时判断身份证号是否存在
     *
     * @param staff
     * @return
     */
    @Override
    public String selectCardNum(Staff staff) {
        int count = staffMapper.selectCardNum(staff);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Staff getByUserIdStaff(String userId, String staffId) {
        return staffMapper.getByUserIdStaff(userId, staffId);
    }


}
