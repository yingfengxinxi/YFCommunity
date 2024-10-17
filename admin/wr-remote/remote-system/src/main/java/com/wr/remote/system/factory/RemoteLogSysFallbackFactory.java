package com.wr.remote.system.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import com.wr.common.core.domain.R;
import com.wr.remote.system.RemoteLogSysService;
import com.wr.remote.domain.SysLogininfor;
import com.wr.remote.domain.SysOperLog;

/**
 * 日志服务降级处理
 * 
 * @author wr
 */
@Component
public class RemoteLogSysFallbackFactory implements FallbackFactory<RemoteLogSysService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteLogSysFallbackFactory.class);

    @Override
    public RemoteLogSysService create(Throwable throwable)
    {
        log.error("通用端日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogSysService()
        {
            @Override
            public R<Boolean> saveLog(SysOperLog sysOperLog, String source)
            {
                return null;
            }

            @Override
            public R<Boolean> saveSysLoginInfo(SysLogininfor sysLogininfor, String source)
            {
                return null;
            }
        };

    }
}
