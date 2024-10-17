package com.wr.api.base.mapper;

import com.wr.remote.domain.ApiOperLog;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 11:48:08
 * @Desc: API 日志数据层
 */
public interface OperateLogMapper {

    /**
     * 添加 API 接口使用日志
     * @param operLog 日志信息
     * @return java.lang.Integer
     */
    Integer insertOperateLog(ApiOperLog operLog);
}
