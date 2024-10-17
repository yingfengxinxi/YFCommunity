package com.wr.api.estate.web;

import com.wr.api.estate.entity.dto.UserDTO;
import com.wr.api.estate.service.LoginService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.model.ApiUser;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 12:54:17
 * @Desc: 物业端登录
 */
@RestController
@RequestMapping("/v2/login")
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param apiUser 登陆数据
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @PostMapping
    @ApiLog(title="物业端-登录", businessType = BusinessType.INSERT)
    public AjaxResult login(@RequestBody ApiUser apiUser){
        Assert.notNull(apiUser.getLoginName(), "登录名不能为空");
        Assert.notNull(apiUser.getPassword(), "密码不能为空");
        return loginService.login(apiUser);
    }

    /**
     * 修改密码 仅针对员工自己修改密码
     * @param userDTO 校验参数
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @PostMapping("/update_pwd")
    @ApiLog(title="物业端-修改密码", businessType = BusinessType.UPDATE)
    public AjaxResult updatePassword(@RequestBody UserDTO userDTO){
        Assert.notNull(userDTO.getLoginName(), "登录名不能为空");
        Assert.notNull(userDTO.getIdCardNo(), "身份证号不能为空");
        return loginService.updatePassword(userDTO);
    }

    /**
     * 退出登录
     * @param loginName 登录名
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/logout")
    @ApiLog(title="物业端-退出登录", businessType = BusinessType.OTHER)
    public AjaxResult logout( String loginName){

        return loginService.logout(loginName);
    }
}
