package com.wr.remote.system;

import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.constant.ServiceNameConstants;
import com.wr.common.core.domain.R;
import com.wr.remote.model.LoginUser;
import com.wr.remote.system.factory.RemoteUserEstateFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 用户服务
 * 
 * @author wr
 */
@FeignClient(contextId = "remoteUserEstateService", value = ServiceNameConstants.ESTATE_SERVICE, fallbackFactory = RemoteUserEstateFallbackFactory.class)
public interface RemoteUserEstateService
{
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/estate_login/user/{username}")
    R<LoginUser> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
