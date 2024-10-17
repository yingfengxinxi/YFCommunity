package com.wr.remote.system;

import com.wr.common.core.constant.ServiceNameConstants;
import com.wr.common.core.domain.R;
import com.wr.remote.domain.ApiOperLog;
import com.wr.remote.system.factory.RemoteApiLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 11:06:06
 * @Desc: API 日志服务
 */
@FeignClient(contextId = "remoteApiLogService", value = ServiceNameConstants.API_BASE_SERVICE, fallbackFactory = RemoteApiLogFallbackFactory.class)
public interface RemoteApiLogService {
    /**
     * 保存操作日志
     * @param apiOperLog 日志实体
     * @return 结果
     */
    @PostMapping("/operate_log")
    public R<Boolean> saveLog(@RequestBody ApiOperLog apiOperLog);

    
}
