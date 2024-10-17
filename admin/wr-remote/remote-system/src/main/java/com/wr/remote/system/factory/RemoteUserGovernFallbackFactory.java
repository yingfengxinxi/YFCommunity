package com.wr.remote.system.factory;

import com.wr.common.core.domain.R;
import com.wr.remote.model.LoginUser;
import com.wr.remote.system.RemoteUserGovernService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 * 
 * @author wr
 */
@Component
public class RemoteUserGovernFallbackFactory implements FallbackFactory<RemoteUserGovernService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserGovernFallbackFactory.class);

    @Override
    public RemoteUserGovernService create(Throwable throwable)
    {
        log.error("社区治理用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserGovernService()
        {
            @Override
            public R<LoginUser> getUserInfo(String username, String source)
            {
                return R.fail("获取用户失败:" + throwable.getMessage());
            }

        };
    }
}
