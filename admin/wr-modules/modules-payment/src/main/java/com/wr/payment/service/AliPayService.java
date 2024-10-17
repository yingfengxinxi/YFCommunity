package com.wr.payment.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.domain.AliPay;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 14:07:13
 * @Desc: 支付宝支付服务
 */
public interface AliPayService {

    /**
     * 支付方式验证
     * @param method
     * @return
     */
    Boolean support(String method);

    /**
     * 支付执行
     * @param aliPay
     * @return
     */
    AjaxResult execute(AliPay aliPay);
}
