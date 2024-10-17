package com.wr.remote.system.factory;

import com.wr.common.core.domain.R;
import com.wr.remote.domain.SysLogininfor;
import com.wr.remote.domain.SysOperLog;
import com.wr.remote.system.RemoteLogGovernService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 日志服务降级处理
 * 
 * @author wr
 */
@Component
public class RemoteLogGovernFallbackFactory implements FallbackFactory<RemoteLogGovernService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteLogGovernFallbackFactory.class);

    @Override
    public RemoteLogGovernService create(Throwable throwable)
    {
        log.error("社区治理日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogGovernService()
        {
            @Override
            public R<Boolean> saveGovernLog(SysOperLog sysOperLog, String source)
            {
                return null;
            }

            @Override
            public R<Boolean> saveGovernLoginInfo(SysLogininfor sysLogininfor, String source)
            {
                return null;
            }
        };

    }
}
