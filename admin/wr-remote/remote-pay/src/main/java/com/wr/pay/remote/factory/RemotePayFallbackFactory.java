package com.wr.pay.remote.factory;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.pay.remote.RemotePayService;
import com.wr.common.payment.domain.AliPay;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.domain.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: RainCity
 * @Date: 2021-11-05 08:36:16
 * @Desc: 支付服务降级处理
 */
@Component
public class RemotePayFallbackFactory implements FallbackFactory<RemotePayService> {
    private static final Logger log = LoggerFactory.getLogger(RemotePayFallbackFactory.class);

    @Override
    public RemotePayService create(Throwable throwable) {
        log.error("支付服务调用失败:{}", throwable.getMessage());

        return new RemotePayService() {
            @Override
            public AjaxResult aliPayment(AliPay aliPay) {

                return AjaxResult.error("支付宝支付方式："+aliPay.getMethod()+"，支付失败:" + throwable.getMessage());
            }

            @Override
            public AjaxResult aliAfter(AliPayDTO aliPayDto) {

                return AjaxResult.error("支付宝订单："+aliPayDto.getMethod()+"失败:" + throwable.getMessage());
            }

            @Override
            public AjaxResult wechatOpenId(OpenDTO openDto) {

                return AjaxResult.error("获取open_id失败：" + throwable.getMessage());
            }

            @Override
            public AjaxResult wechatPayment(WechatOpen wechatOpen,String source) {

                return AjaxResult.error("微信支付方式："+ wechatOpen.getMethod()+"，支付失败:" + throwable.getMessage());
            }

            @Override
            public AjaxResult wechatAfter(WechatDTO wechatDto) {

                return AjaxResult.error("微信订单："+wechatDto.getMethod()+"失败:" + throwable.getMessage());
            }

        };
    }
}
