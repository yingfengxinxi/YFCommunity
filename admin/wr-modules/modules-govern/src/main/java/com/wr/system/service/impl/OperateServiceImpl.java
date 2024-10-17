package com.wr.system.service.impl;

import com.wr.remote.domain.SysOperLog;
import com.wr.system.mapper.GovernOperateMapper;
import com.wr.system.service.OperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: RainCity
 * @Date: 2022-09-18 11:56:20
 * @Desc:
 */
@Service
public class OperateServiceImpl implements OperateService {
    @Autowired
    private GovernOperateMapper operateMapper;

    @Override
    public Integer insertOperateLog(SysOperLog operLog) {

        return operateMapper.insertOperateLog(operLog);
    }
}
