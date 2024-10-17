package com.wr.remote.system;

import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.constant.ServiceNameConstants;
import com.wr.common.core.domain.R;
import com.wr.remote.domain.SysLogininfor;
import com.wr.remote.domain.SysOperLog;
import com.wr.remote.system.factory.RemoteLogAiIotFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 日志服务
 * 
 * @author wr
 */
@FeignClient(contextId = "remoteLogAiIotService", value = ServiceNameConstants.AI_IOT_SERVICE, fallbackFactory = RemoteLogAiIotFallbackFactory.class)
public interface RemoteLogAiIotService
{
    /**
     * 保存系统日志
     *
     * @param sysOperLog 日志实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/operate")
    R<Boolean> saveAiIotLog(@RequestBody SysOperLog sysOperLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 保存访问记录
     *
     * @param sysLogininfor 访问实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/iot_login")
    R<Boolean> saveAiIotLoginInfo(@RequestBody SysLogininfor sysLogininfor, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
