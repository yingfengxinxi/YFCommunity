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
import java.net.URLEncoder;
import java.security.GeneralSecurityException;

/**
 * @Author: RainCity
 * @Date: 2021-11-02 10:41:44
 * @Desc: H5支付
 *  商户系统先调用该接口在微信支付服务后台生成预支付交易单
 */
@Service
public class WechatH5ServiceImpl implements WechatPayService {
    private static final Logger log = LoggerFactory.getLogger(WechatH5ServiceImpl.class);

    @Override
    public Boolean support(String method) {

        return PayConstants.H5.equals(method);
    }

    @Override
    public AjaxResult execute(WechatOpen wechatOpen) {
        try {
            String h5Url =  WechatPayUtil.transactionsH5(wechatOpen);
            if(StringUtils.isNotEmpty(h5Url)){
                AjaxResult ajaxResult = AjaxResult.success();
                if(StringUtils.isNotEmpty(wechatOpen.getReturnUrl())){
                    h5Url += "&redirect_url="+URLEncoder.encode(wechatOpen.getReturnUrl(),"UTF-8");
                }
                ajaxResult.put("outTradeNo", wechatOpen.getOutTradeNo());
                ajaxResult.put("h5Url",h5Url);
                return ajaxResult;
            }
            return AjaxResult.error("下单失败");
        } catch (IOException | GeneralSecurityException | NotFoundException | HttpCodeException e) {
            log.error("H5创建订单失败...");
            e.printStackTrace();
            return AjaxResult.error("下单失败");
        }
    }
}
