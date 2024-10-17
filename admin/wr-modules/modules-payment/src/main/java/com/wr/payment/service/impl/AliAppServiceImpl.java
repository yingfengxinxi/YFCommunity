package com.wr.payment.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.constants.AliConstants;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.AliPay;
import com.wr.common.payment.util.AliPayUtil;
import com.wr.payment.service.AliPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 14:36:04
 * @Desc: 支付宝支付-APP支付
 */
@Service
public class AliAppServiceImpl implements AliPayService {
    private static final Logger log = LoggerFactory.getLogger(AliAppServiceImpl.class);

    @Override
    public Boolean support(String method) {

        return PayConstants.APP.equals(method);
    }

    @Override
    public AjaxResult execute(AliPay aliPay) {
        try {
            //创建订单
            AlipayTradeAppPayResponse response = AliPayUtil.tradeAppPay(aliPay);
            if(response.isSuccess()){
                //就是orderString 可以直接给客户端请求，无需再做处理。
                AjaxResult ajaxResult = AjaxResult.success();
                ajaxResult.put(AliConstants.OUT_TRADE_NO,aliPay.getOutTradeNo());
                ajaxResult.put("result",response.getBody());
                return ajaxResult;
            }
            return AjaxResult.error(response.getMsg()+"；"+response.getSubMsg());
        } catch (AlipayApiException e) {
            log.error("APP创建订单失败：errCode:{},errMsg:{}",e.getErrCode(),e.getErrMsg());
            e.printStackTrace();
            return AjaxResult.error("支付发起失败");
        }
    }
}
