package com.wr.payment.service.impl;

import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.domain.dto.WechatDTO;
import com.wr.common.payment.util.WechatPayUtil;
import com.wr.payment.service.WechatTradeAfterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * @Author: RainCity
 * @Date: 2021-11-04 19:46:05
 * @Desc: 微信订单关闭
 */
@Service
public class WechatTradeCloseServiceImpl implements WechatTradeAfterService {
    private static final Logger log = LoggerFactory.getLogger(WechatTradeCloseServiceImpl.class);

    @Override
    public Boolean support(String method) {

        return PayConstants.CLOSE.equals(method);
    }

    @Override
    public AjaxResult execute(WechatDTO wechatDto) {
        try {
            return WechatPayUtil.transactionsClose(wechatDto);
        } catch (IOException | GeneralSecurityException | NotFoundException | HttpCodeException e) {
            log.error("微信订单关闭，商户号：{}，商户订单号：{}",wechatDto.getMchId(),wechatDto.getOutTradeNo());
            e.printStackTrace();
            return AjaxResult.error("订单关闭失败");
        }
    }
}
