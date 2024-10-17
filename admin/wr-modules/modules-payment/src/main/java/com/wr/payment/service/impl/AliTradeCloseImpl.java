package com.wr.payment.service.impl;

import com.alipay.api.response.AlipayTradeCloseResponse;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.dto.AliPayDTO;
import com.wr.common.payment.util.AliPayUtil;
import com.wr.payment.service.AliTradeAfterService;
import org.springframework.stereotype.Service;

/**
 * @Author: RainCity
 * @Date: 2021-11-04 18:03:40
 * @Desc: 支付宝订单关闭
 */
@Service
public class AliTradeCloseImpl implements AliTradeAfterService {

    @Override
    public Boolean support(String method) {

        return PayConstants.CLOSE.equals(method);
    }

    @Override
    public AjaxResult execute(AliPayDTO aliPayDto) {
        AlipayTradeCloseResponse response = AliPayUtil.tradeClose(aliPayDto);
        if(null == response || !response.isSuccess()){
            return AjaxResult.error("订单关闭失败");
        }
        return AjaxResult.success("订单已关闭");
    }
}
