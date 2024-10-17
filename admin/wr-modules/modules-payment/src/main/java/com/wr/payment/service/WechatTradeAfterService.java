package com.wr.payment.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.domain.dto.WechatDTO;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 14:07:13
 * @Desc: 微信支付后服务
 */
public interface WechatTradeAfterService {

    /**
     * 支付方式验证
     * @param method
     * @return
     */
    Boolean support(String method);

    /**
     * 支付执行
     * @param wechatDto
     * @return
     */
    AjaxResult execute(WechatDTO wechatDto);
}
