package com.wr.remote.system;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.constant.ServiceNameConstants;
import com.wr.common.core.domain.R;
import com.wr.remote.domain.SysLogininfor;
import com.wr.remote.domain.SysOperLog;
import com.wr.remote.system.factory.RemoteLogSysFallbackFactory;

/**
 * 日志服务
 * 
 * @author wr
 */
@FeignClient(contextId = "remoteLogSysService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogSysFallbackFactory.class)
public interface RemoteLogSysService
{
    /**
     * 保存系统日志
     *
     * @param sysOperLog 日志实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/operlog")
    public R<Boolean> saveLog(@RequestBody SysOperLog sysOperLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 保存访问记录
     *
     * @param sysLogininfor 访问实体
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/logininfor")
    public R<Boolean> saveSysLoginInfo(@RequestBody SysLogininfor sysLogininfor, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
