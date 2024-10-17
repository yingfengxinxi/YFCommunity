package com.wr.remote.aiot.remote;

import com.wr.common.core.constant.ServiceNameConstants;
import com.wr.common.core.domain.R;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.aiot.Access;
import com.wr.remote.aiot.remote.factory.RemoteAccessFallbackFactory;
import com.wr.remote.domain.ApiOperLog;
import com.wr.remote.system.factory.RemoteApiLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 11:06:06
 * @Desc: API 日志服务
 */
@FeignClient(contextId = "remoteAccessService", value = ServiceNameConstants.AI_IOT_SERVICE, fallbackFactory = RemoteAccessFallbackFactory.class)
public interface RemoteAccessService {
    /**
     *
     * @param access
     * @return
     */
    @GetMapping("/access/estateListPage")
    public R<TableDataInfo> estateListPage(Access access);

    
}
