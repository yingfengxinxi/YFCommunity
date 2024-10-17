package com.wr.api.payment.mapper;

import com.wr.remote.estate.log.PayLog;

/**
 * @Author: RainCity
 * @Date: 2021-11-18 13:52:33
 * @Desc: 缴费记录数据层
 */
public interface PayLogMapper {
    /**
     * 添加缴费记录
     * @param payLog
     * @return
     */
    Integer insertPayLog(PayLog payLog);
}
