package com.wr.remote.system.factory;

import com.wr.common.core.domain.R;
import com.wr.remote.domain.SysLogininfor;
import com.wr.remote.domain.SysOperLog;
import com.wr.remote.system.RemoteLogAiIotService;
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
public class RemoteLogAiIotFallbackFactory implements FallbackFactory<RemoteLogAiIotService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteLogAiIotFallbackFactory.class);

    @Override
    public RemoteLogAiIotService create(Throwable throwable)
    {
        log.error("物联网日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogAiIotService()
        {
            @Override
            public R<Boolean> saveAiIotLog(SysOperLog sysOperLog, String source)
            {
                return null;
            }

            @Override
            public R<Boolean> saveAiIotLoginInfo(SysLogininfor sysLogininfor, String source)
            {
                return null;
            }
        };

    }
}
