package com.wr.remote.system.factory;

import com.wr.common.core.domain.R;
import com.wr.remote.model.LoginUser;
import com.wr.remote.system.RemoteUserAiIotService;
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
public class RemoteUserAiIotFallbackFactory implements FallbackFactory<RemoteUserAiIotService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserAiIotFallbackFactory.class);

    @Override
    public RemoteUserAiIotService create(Throwable throwable)
    {
        log.error("物联网用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserAiIotService()
        {
            @Override
            public R<LoginUser> getUserInfo(String username, String source)
            {
                return R.fail("获取用户失败:" + throwable.getMessage());
            }

        };
    }
}
