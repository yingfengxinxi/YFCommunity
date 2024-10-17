package com.wr.auth.controller;

import com.wr.auth.form.LoginBody;
import com.wr.auth.form.RegisterBody;
import com.wr.auth.service.SysLoginService;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.JwtUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.auth.AuthUtil;
import com.wr.common.security.service.TokenService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * token 控制
 * 
 * @author wr
 */
@RestController
public class TokenController
{
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form) {
        // 用户登录
        LoginUser userInfo = sysLoginService.adminLogin(form.getUsername(), form.getPassword(), form.getUserType());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo, form.getUserType()));
    }

    @PostMapping("estate_login")
    public R<?> estateLogin(@RequestBody LoginBody form) {
        // 用户登录
        LoginUser userInfo = sysLoginService.estateLogin(form.getUsername(), form.getPassword(), form.getUserType());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo, form.getUserType()));
    }

    @PostMapping("govern_login")
    public R<?> governLogin(@RequestBody LoginBody form) {
        // 用户登录
        LoginUser userInfo = sysLoginService.governLogin(form.getUsername(), form.getPassword(), form.getUserType());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo, form.getUserType()));
    }

    @PostMapping("iot_login")
    public R<?> iotLogin(@RequestBody LoginBody form) {
        // 用户登录
        LoginUser userInfo = sysLoginService.iotLogin(form.getUsername(), form.getPassword(), form.getUserType());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo, form.getUserType()));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String username = JwtUtils.getUserName(token);
            String userType = JwtUtils.getUserType(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username, userType);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody) {
        // 用户注册
        sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        return R.ok();
    }

    public static void main(String[] args) {
        String pwd=SecurityUtils.encryptPassword("admin1234");
        System.out.printf(pwd);
    }
}
