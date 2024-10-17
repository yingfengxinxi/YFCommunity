package com.wr.payment.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.util.WechatPayUtil;
import com.wr.common.payment.domain.dto.WechatDTO;
import com.wr.payment.service.WechatTradeAfterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

/**
 * @Author: RainCity
 * @Date: 2021-11-04 19:46:05
 * @Desc: 微信订单查询
 */
@Service
public class WechatTradeQueryServiceImpl implements WechatTradeAfterService {
    private static final Logger log = LoggerFactory.getLogger(WechatTradeQueryServiceImpl.class);

    @Override
    public Boolean support(String method) {

        return PayConstants.QUERY.equals(method);
    }

    @Override
    public AjaxResult execute(WechatDTO wechatDto) {
        try {
            JSONObject result = WechatPayUtil.transactionsQuery(wechatDto);
            if(null == result){
                return AjaxResult.error("订单查询失败");
            }
            return AjaxResult.success(result);
        } catch (URISyntaxException | IOException | GeneralSecurityException | NotFoundException | HttpCodeException e) {
            log.error("微信订单查询失败，商户号：{}，微信支付订单号：{}",wechatDto.getMchId(),wechatDto.getTransactionId());
            e.printStackTrace();
            return AjaxResult.error("订单查询失败");
        }
    }
}
