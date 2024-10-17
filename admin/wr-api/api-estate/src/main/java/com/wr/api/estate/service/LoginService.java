package com.wr.api.estate.service;

import com.wr.api.estate.entity.dto.UserDTO;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.model.ApiUser;
import com.wr.remote.domain.EstateMobileMenu;
import com.wr.remote.domain.SysUser;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 13:11:41
 * @Desc: 登录处理
 */
public interface LoginService {
    /**
     * 登录
     *
     * @param apiUser 用户表单
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult login(ApiUser apiUser);

    /**
     *
     * @param userId
     * @return
     */
    public List<EstateMobileMenu> getByUserIdMenuList(Long userId);
    /**
     * 忘记密码--> 密码重置
     *
     * @param userDTO 校验数据
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult updatePassword(UserDTO userDTO);

    /**
     * 退出登录
     *
     * @param loginName 登录名
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult logout(String loginName);

    SysUser getUserNameNikeName(String userName);

    SysUser getByUserIdUser(String userId);
}
