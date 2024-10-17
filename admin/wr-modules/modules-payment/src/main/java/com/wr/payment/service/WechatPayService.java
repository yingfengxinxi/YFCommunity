package com.wr.payment.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.domain.WechatOpen;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 20:42:54
 * @Desc: 微信支付服务
 */
public interface WechatPayService {
    /**
     * 支付方式验证
     * @param method
     * @return
     */
    Boolean support(String method);

    /**
     * 支付执行
     * @param wechatOpen
     * @return
     */
    AjaxResult execute(WechatOpen wechatOpen);
}
