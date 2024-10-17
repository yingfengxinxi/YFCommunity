package com.wr.payment.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.domain.dto.AliPayDTO;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 14:07:13
 * @Desc: 支付宝支付后服务
 */
public interface AliTradeAfterService {

    /**
     * 支付方式验证
     * @param method
     * @return
     */
    Boolean support(String method);

    /**
     * 支付执行
     * @param aliPayDTO
     * @return
     */
    AjaxResult execute(AliPayDTO aliPayDTO);
}
