package com.wr.system.mapper;

import com.wr.remote.domain.SysOperLog;

/**
 * @Author: RainCity
 * @Date: 2022-09-18 11:42:51
 * @Desc:
 */
public interface AiotOperateMapper {
    /**
     * 添加操作记录
     * @param operLog
     * @return java.lang.Integer
     */
    Integer insertOperateLog(SysOperLog operLog);
}
