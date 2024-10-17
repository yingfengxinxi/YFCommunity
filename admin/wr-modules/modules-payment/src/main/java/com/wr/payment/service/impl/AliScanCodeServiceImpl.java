package com.wr.payment.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.wr.common.core.web.domain.AjaxResult;
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
 * @Desc: 支付宝支付-当面扫码支付
 */
@Service
public class AliScanCodeServiceImpl implements AliPayService {
    private static final Logger log = LoggerFactory.getLogger(AliScanCodeServiceImpl.class);

    @Override
    public Boolean support(String method) {

        return PayConstants.SCAN_CODE.equals(method);
    }

    @Override
    public AjaxResult execute(AliPay aliPay) {

        try {
            //创建订单
            AlipayTradePrecreateResponse response = AliPayUtil.tradePreCreate(aliPay);
            if(response.isSuccess()){
                AjaxResult ajaxResult = AjaxResult.success();
                ajaxResult.put("outTradeNo",response.getOutTradeNo());
                ajaxResult.put("codeUrl",response.getQrCode());
                return ajaxResult;
            }
            return AjaxResult.error(response.getMsg()+"；"+response.getSubMsg());
        } catch (AlipayApiException e) {
            log.error("收款码生成失败：errCode:{},errMsg:{}",e.getErrCode(),e.getErrMsg());
            e.printStackTrace();
            return AjaxResult.error("收款码生成失败");
        }
    }
}
