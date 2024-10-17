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
 * @Desc: NATIVE支付
 *  商户系统按微信支付协议生成支付二维码，用户再用微信“扫一扫”完成支付的模式
 */
@Service
public class WechatNativeServiceImpl implements WechatPayService {
    private static final Logger log = LoggerFactory.getLogger(WechatNativeServiceImpl.class);

    @Override
    public Boolean support(String method) {

        return PayConstants.NATIVE.equals(method);
    }

    @Override
    public AjaxResult execute(WechatOpen wechatOpen) {
        try {
            String codeUrl =  WechatPayUtil.transactionsNative(wechatOpen);
            if(StringUtils.isNotEmpty(codeUrl)){
                AjaxResult ajaxResult = AjaxResult.success();
                ajaxResult.put("codeUrl",codeUrl);
                return ajaxResult;
            }
            return AjaxResult.error("下单失败");
        }  catch (IOException | GeneralSecurityException | NotFoundException | HttpCodeException e) {
            log.error("NATIVE创建订单失败...");
            e.printStackTrace();
            return AjaxResult.error("下单失败");
        }
    }
}
