package com.wr.remote.system.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import com.wr.common.core.domain.R;
import com.wr.remote.system.RemoteUserSysService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.model.LoginUser;

/**
 * 用户服务降级处理
 * 
 * @author wr
 */
@Component
public class RemoteUserSysFallbackFactory implements FallbackFactory<RemoteUserSysService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserSysFallbackFactory.class);

    @Override
    public RemoteUserSysService create(Throwable throwable)
    {
        log.error("通用端用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserSysService()
        {
            @Override
            public R<LoginUser> getUserInfo(String username, String source)
            {
                return R.fail("获取用户失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> registerUserInfo(SysUser sysUser, String source)
            {
                return R.fail("注册用户失败:" + throwable.getMessage());
            }
        };
    }
}
