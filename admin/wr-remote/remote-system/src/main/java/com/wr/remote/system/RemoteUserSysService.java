package com.wr.remote.system;

import com.wr.remote.system.factory.RemoteUserSysFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.constant.ServiceNameConstants;
import com.wr.common.core.domain.R;
import com.wr.remote.domain.SysUser;
import com.wr.remote.model.LoginUser;

/**
 * 用户服务
 * 
 * @author wr
 */
@FeignClient(contextId = "remoteUserSysService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserSysFallbackFactory.class)
public interface RemoteUserSysService
{
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/user/info/{username}")
    public R<LoginUser> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/user/register")
    public R<Boolean> registerUserInfo(@RequestBody SysUser sysUser, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
