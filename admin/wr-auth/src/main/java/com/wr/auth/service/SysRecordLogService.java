package com.wr.auth.service;

import com.wr.common.core.constant.Constants;
import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.utils.ServletUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.ip.IpUtils;
import com.wr.remote.domain.SysLogininfor;
import com.wr.remote.system.RemoteLogAiIotService;
import com.wr.remote.system.RemoteLogEstateService;
import com.wr.remote.system.RemoteLogGovernService;
import com.wr.remote.system.RemoteLogSysService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: RainCity
 * @Date: 2022-08-16 09:06:35
 * @Desc: 记录日志方法
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired} )
public class SysRecordLogService {
    private final RemoteLogSysService remoteLogSysService;
    private final RemoteLogEstateService remoteLogEstateService;
    private final RemoteLogAiIotService remoteLogAiIotService;
    private final RemoteLogGovernService remoteLogGovernService;

    /**
     * 记录登录信息
     * @param username 用户名
     * @param userType 用户类型
     * @param status 状态
     * @param message 消息内容
     * @return
     */
    public void recordLoginInfo(String username, String userType, String status, String message) {
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(username);
        logininfor.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        logininfor.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
            logininfor.setStatus(Constants.LOGIN_SUCCESS_STATUS);
        } else if (Constants.LOGIN_FAIL.equals(status)) {
            logininfor.setStatus(Constants.LOGIN_FAIL_STATUS);
        }
        if(UserConstants.USER_SYS.equals(userType)){
            remoteLogSysService.saveSysLoginInfo(logininfor, SecurityConstants.INNER);
        } else if(UserConstants.USER_ESTATE.equals(userType)){
            remoteLogEstateService.saveEstateLoginInfo(logininfor, SecurityConstants.INNER);
        } else if(UserConstants.USER_AI_IOT.equals(userType)){
            remoteLogAiIotService.saveAiIotLoginInfo(logininfor, SecurityConstants.INNER);
        } else if(UserConstants.USER_GOVERN.equals(userType)){
            remoteLogGovernService.saveGovernLoginInfo(logininfor, SecurityConstants.INNER);
        }
    }
}
