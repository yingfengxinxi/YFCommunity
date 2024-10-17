package com.wr.remote.system.factory;

import com.wr.common.core.domain.R;
import com.wr.remote.domain.ApiOperLog;
import com.wr.remote.system.RemoteApiLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 11:06:06
 * @Desc: API 日志服务降级处理
 */
@Component
public class RemoteApiLogFallbackFactory implements FallbackFactory<RemoteApiLogService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteApiLogFallbackFactory.class);

    @Override
    public RemoteApiLogService create(Throwable throwable) {
        log.error("日志服务调用失败:{}", throwable.getMessage());
        return new RemoteApiLogService() {
            @Override
            public R<Boolean> saveLog(ApiOperLog apiOperLog) {
                return null;
            }

        };

    }
}
