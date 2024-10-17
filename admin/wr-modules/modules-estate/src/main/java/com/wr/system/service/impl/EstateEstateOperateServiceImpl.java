package com.wr.system.service.impl;

import com.wr.system.mapper.EstateOperateMapper;
import com.wr.system.service.EstateOperateService;
import com.wr.remote.domain.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: RainCity
 * @Date: 2022-09-18 11:56:20
 * @Desc:
 */
@Service
public class EstateEstateOperateServiceImpl implements EstateOperateService {
    @Autowired
    private EstateOperateMapper estateOperateMapper;

    @Override
    public Integer insertOperateLog(SysOperLog operLog) {

        return estateOperateMapper.insertOperateLog(operLog);
    }
}
