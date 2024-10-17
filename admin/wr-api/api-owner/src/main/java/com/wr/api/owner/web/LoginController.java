package com.wr.api.owner.web;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.api.owner.entity.dto.ChangeDTO;
import com.wr.api.owner.entity.dto.UserDTO;
import com.wr.api.owner.entity.vo.CommunityVO;
import com.wr.api.owner.service.LoginService;
import com.wr.api.owner.service.estateapi.pay.PayService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.model.ApiUser;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.payment.pay.util.WechatPayUtils;
import com.wr.remote.domain.CommunityWechat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 12:54:17
 * @Desc: 业主端登录
 */
@RestController
@RequestMapping("/v2/login")
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private PayService payService;
    /**
     * 登录
     * @param apiUser 登陆数据
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @PostMapping
    @ApiLog(title="业主端-登录", businessType = BusinessType.INSERT)
    public AjaxResult login(@RequestBody ApiUser apiUser){
        Assert.notNull(apiUser.getLoginName(), "登录名不能为空");
        Assert.notNull(apiUser.getPassword(), "密码不能为空");
        return loginService.login(apiUser);
    }

    /**
     * 根据区域id查询有房子的小区
     * @param changeDTO 查询条件
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/community")
    public AjaxResult community(ChangeDTO changeDTO){
        if(!"0".equals(changeDTO.getAccountType()) && !"1".equals(changeDTO.getAccountType())){
            return AjaxResult.error("访客身份不可切换小区");
        }
        List<CommunityVO> communityList = loginService.selectCommunityList(changeDTO);
        return AjaxResult.success(communityList);
    }

    /**
     * 修改密码 仅针对员工自己修改密码
     * @param userDTO 校验参数
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/update_pwd")
    @ApiLog(title="业主端-修改密码", businessType = BusinessType.UPDATE)
    public AjaxResult updatePassword(@RequestBody UserDTO userDTO){
        Assert.notNull(userDTO.getLoginName(), "登录名不能为空");
//        Assert.notNull(userDTO.getIdCardNo(), "身份证号不能为空");
        return loginService.updatePassword(userDTO);
    }

    /**
     * 退出登录
     * @param loginName 登录名
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/logout")
    @ApiLog(title="业主端-退出登录", businessType = BusinessType.OTHER)
    public AjaxResult logout(String loginName){

        return loginService.logout(loginName);
    }

    /**
     * 根据账号id查询业主信息
     * @param accountId 账号
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/findOneOwnerByAccount")
    public AjaxResult findOneOwnerByAccount(Long accountId){
        Assert.notNull(accountId, "登录账号不能为空");
        return loginService.selectOwnerByAccountId(accountId);
    }

    /**
     * 根据手机号查询是否注册
     * @param phone 账号
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/findRegisterByphone")
    public AjaxResult findRegisterByphone(String phone){
        Assert.notNull(phone, "手机号不能为空");
        return loginService.findRegisterByphone(phone);
    }

    /**
     * 微信授权登录
     * @param map 登陆数据
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @PostMapping("/loginByWeChat")
    @ApiLog(title="业主端-微信登录", businessType = BusinessType.OTHER)
    public AjaxResult loginByWeChat(@RequestBody Map<String,Object> map){
        if(CollUtil.isEmpty( map ) || StringUtils.isBlank( MapUtil.getStr( map,"code" ) )){
            throw new ServiceException("获取相关登录参数失败，请重新进入小程序");
        }

        //获取配置的信息
        List<CommunityWechat> communityWechatList = payService.selectWechatById(null, null);
        if (CollectionUtils.isEmpty(communityWechatList)) {
            throw new ServiceException("获取配置的账号失败，请重新进入小程序");
        }
        CommunityWechat communityWechat = communityWechatList.get(0);
        String code = WechatPayUtils.getPhoneNumber( communityWechat.getOwnerAppId(), communityWechat.getOwnerAppSecret(), MapUtil.getStr( map, "code" ) );
        if(StringUtils.isBlank( code )){
            throw new ServiceException("微信获取手机号失败，请重新进入小程序");
        }
        ApiUser apiUser = new ApiUser();
        apiUser.setLoginName( code );
        apiUser.setPassword( "password" );
        return loginService.loginByWeChat(apiUser);
    }

    /**
     * 忘记密码修改密码
     * @param userDTO 校验参数
     * @return com.wr.common.core.web.domain.AjaxResult
     */
//    @AuthRequired
    @PostMapping("/updatePasswordByIDCard")
    @ApiLog(title="忘记密码修改密码", businessType = BusinessType.UPDATE)
    public AjaxResult updatePasswordByIDCard(@RequestBody UserDTO userDTO){
        Assert.notNull(userDTO.getLoginName(), "登录名不能为空");
        Assert.notNull(userDTO.getIdCardNo(), "身份证号不能为空");
        Assert.notNull(userDTO.getNewPass(), "修改密码不能为空");
        return loginService.updatePasswordByIDCard(userDTO);
    }
}
