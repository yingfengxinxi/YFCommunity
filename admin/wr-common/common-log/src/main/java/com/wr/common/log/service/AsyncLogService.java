package com.wr.common.log.service;

import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.constant.UserConstants;
import com.wr.remote.domain.ApiOperLog;
import com.wr.remote.domain.SysOperLog;
import com.wr.remote.system.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 * 
 * @author wr
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AsyncLogService {
    private final RemoteLogSysService remoteLogSysService;
    private final RemoteLogEstateService remoteLogEstateService;
    private final RemoteLogAiIotService remoteLogAiIotService;
    private final RemoteLogGovernService remoteLogGovernService;
    private final RemoteApiLogService remoteApiLogService;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveOperateLog(SysOperLog sysOperLog, String userType) {
        sysOperLog.setUserType(userType);

        if(UserConstants.USER_SYS.equals(userType)){
            remoteLogSysService.saveLog(sysOperLog, SecurityConstants.INNER);
        } else if(UserConstants.USER_ESTATE.equals(userType)){
            remoteLogEstateService.saveEstateLog(sysOperLog, SecurityConstants.INNER);
        } else if(UserConstants.USER_AI_IOT.equals(userType)){
            remoteLogAiIotService.saveAiIotLog(sysOperLog, SecurityConstants.INNER);
        } else if(UserConstants.USER_GOVERN.equals(userType)){
            remoteLogGovernService.saveGovernLog(sysOperLog, SecurityConstants.INNER);
        }
    }

    @Async
    public void saveApiLog(ApiOperLog apiOperLog){
        remoteApiLogService.saveLog(apiOperLog);
    }
}
