package com.wr.system.service;

import com.wr.remote.domain.SysOperLog;

/**
 * @Author: RainCity
 * @Date: 2022-09-18 11:42:11
 * @Desc: 记录操作
 */
public interface OperateService {
    /**
     * 添加操作记录
     * @param operLog
     * @return java.lang.Integer
     */
    Integer insertOperateLog(SysOperLog operLog);
}
