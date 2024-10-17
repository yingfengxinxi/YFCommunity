package com.wr.api.base.service;

import com.wr.remote.domain.ApiOperLog;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 11:45:47
 * @Desc: API 日志服务层
 */
public interface OperateLogService {
    /**
     * 添加 API 接口使用日志
     * @param operLog 日志信息
     * @return java.lang.Integer
     */
    Integer insertOperateLog(ApiOperLog operLog);
}
