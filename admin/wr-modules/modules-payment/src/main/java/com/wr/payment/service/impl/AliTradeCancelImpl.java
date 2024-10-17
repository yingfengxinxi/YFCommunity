package com.wr.payment.service.impl;

import com.alipay.api.response.AlipayTradeCancelResponse;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.dto.AliPayDTO;
import com.wr.common.payment.util.AliPayUtil;
import com.wr.payment.service.AliTradeAfterService;
import org.springframework.stereotype.Service;

/**
 * @Author: RainCity
 * @Date: 2021-11-04 18:03:40
 * @Desc: 支付宝订单撤销
 */
@Service
public class AliTradeCancelImpl implements AliTradeAfterService {

    @Override
    public Boolean support(String method) {

        return PayConstants.CANCEL.equals(method);
    }

    @Override
    public AjaxResult execute(AliPayDTO aliPayDto) {
        AlipayTradeCancelResponse response = AliPayUtil.tradeCancel(aliPayDto);
        if(null == response || !response.isSuccess()){
            return AjaxResult.error("订单撤销失败");
        }
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("retryFlag",response.getRetryFlag());
        ajaxResult.put("action",response.getAction());
        return ajaxResult;
    }
}
