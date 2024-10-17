package com.wr.api.owner.service;

import com.wr.api.owner.entity.dto.ChangeDTO;
import com.wr.api.owner.entity.dto.UserDTO;
import com.wr.api.owner.entity.vo.CommunityVO;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.model.ApiUser;
import com.wr.remote.estate.manage.contract.Owner;

import java.util.List;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 13:11:41
 * @Desc: 登录处理
 */
public interface LoginService {

    /**
     * 登录
     * @param apiUser 用户表单
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult login(ApiUser apiUser);

    /**
     * 根据区域查询登录人有（房产/出租）的小区
     * @param changeDTO 过滤条件
     * @return java.util.List<com.wr.api.owner.entity.vo.CommunityVO>
     */
    List<CommunityVO> selectCommunityList(ChangeDTO changeDTO);

    /**
     * 忘记密码--> 密码重置
     * @param userDTO 校验数据
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult updatePassword(UserDTO userDTO);

    /**
     * 退出登录
     * @param loginName 登录名
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult logout(String loginName);

    /**
     * 根据账号id查询业主信息
     *
     * @param accountId 业主id
     * @return java.lang.Long
     */
    AjaxResult selectOwnerByAccountId(Long accountId);
    
    /**
    * @Author SUNk
    * @Description 根据手机号查询是否注册
    * @Date 15:02 2024/3/2
    * @Param [phone]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    AjaxResult findRegisterByphone(String phone);

    /**
     * 获取手机号登录
     * @param apiUser 用户表单
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    AjaxResult loginByWeChat(ApiUser apiUser);

    /**
    * @Author SUNk
    * @Description 忘记密码修改密码
    * @Date 14:16 2024/4/9
    * @Param [userDTO]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    AjaxResult updatePasswordByIDCard(UserDTO userDTO);
}
