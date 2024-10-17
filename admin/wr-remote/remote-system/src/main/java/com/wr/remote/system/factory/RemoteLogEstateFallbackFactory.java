package com.wr.remote.system.factory;

import com.wr.common.core.domain.R;
import com.wr.remote.domain.SysLogininfor;
import com.wr.remote.domain.SysOperLog;
import com.wr.remote.system.RemoteLogEstateService;
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
public class RemoteLogEstateFallbackFactory implements FallbackFactory<RemoteLogEstateService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteLogEstateFallbackFactory.class);

    @Override
    public RemoteLogEstateService create(Throwable throwable)
    {
        log.error("物业端日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogEstateService()
        {
            @Override
            public R<Boolean> saveEstateLog(SysOperLog sysOperLog, String source)
            {
                return null;
            }

            @Override
            public R<Boolean> saveEstateLoginInfo(SysLogininfor sysLogininfor, String source)
            {
                return null;
            }
        };

    }
}
