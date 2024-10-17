package com.wr.payment.service.impl;

import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.util.WechatPayUtil;
import com.wr.payment.service.WechatPayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * @Author: RainCity
 * @Date: 2021-11-02 10:41:44
 * @Desc: APP支付
 *  商户通过在移动端应用APP中集成开放SDK调起微信支付模块来完成支付
 */
@Service
public class WechatAppServiceImpl implements WechatPayService {
    private static final Logger log = LoggerFactory.getLogger(WechatAppServiceImpl.class);

    @Override
    public Boolean support(String method) {

        return PayConstants.APP.equals(method);
    }

    @Override
    public AjaxResult execute(WechatOpen wechatOpen) {
        try {
            String prepayId =  WechatPayUtil.transactionsApp(wechatOpen);
            if(StringUtils.isNotEmpty(prepayId)){
                AjaxResult ajaxResult = AjaxResult.success();
                ajaxResult.put("outTradeNo", wechatOpen.getOutTradeNo());
                ajaxResult.put("prepayId",prepayId);
                return ajaxResult;
            }
            return AjaxResult.error("下单失败");
        } catch (IOException | GeneralSecurityException | NotFoundException | HttpCodeException e) {
            log.error("APP创建订单失败...");
            e.printStackTrace();
            return AjaxResult.error("下单失败");
        }
    }
}
