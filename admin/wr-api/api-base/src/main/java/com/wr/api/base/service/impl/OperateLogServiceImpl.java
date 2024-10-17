package com.wr.api.base.service.impl;

import com.wr.api.base.mapper.OperateLogMapper;
import com.wr.api.base.service.OperateLogService;
import com.wr.remote.domain.ApiOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 11:47:18
 * @Desc: API 日志业务处理层
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public Integer insertOperateLog(ApiOperLog operLog) {

        return operateLogMapper.insertOperateLog(operLog);
    }
}
