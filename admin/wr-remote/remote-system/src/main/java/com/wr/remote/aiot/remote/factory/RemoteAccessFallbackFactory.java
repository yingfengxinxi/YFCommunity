package com.wr.remote.aiot.remote.factory;

import com.wr.common.core.domain.R;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.aiot.Access;
import com.wr.remote.aiot.remote.RemoteAccessService;
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
public class RemoteAccessFallbackFactory implements FallbackFactory<RemoteAccessService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteAccessFallbackFactory.class);

    @Override
    public RemoteAccessService create(Throwable throwable) {
        log.error("门禁服务调用失败:{}", throwable.getMessage());
        return new RemoteAccessService() {
            @Override
            public R<TableDataInfo> estateListPage(Access access) {
                return null;
            }

        };

    }
}
