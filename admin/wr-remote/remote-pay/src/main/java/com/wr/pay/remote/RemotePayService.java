package com.wr.pay.remote;

import com.wr.common.core.constant.SecurityConstants;
import com.wr.common.core.constant.ServiceNameConstants;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.payment.domain.AliPay;
import com.wr.common.payment.domain.WechatOpen;
import com.wr.common.payment.domain.dto.*;
import com.wr.pay.remote.factory.RemotePayFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Author: RainCity
 * @Date: 2021-11-05 08:37:05
 * @Desc: 支付服务
 */
@FeignClient(contextId = "remotePayService",url ="http://172.16.205.32:9100/payment/" ,value = ServiceNameConstants.PAY_SERVICE, fallbackFactory = RemotePayFallbackFactory.class)
//@FeignClient(contextId = "remotePayService",url ="http://127.0.0.1:9100/payment/" ,value = ServiceNameConstants.PAY_SERVICE, fallbackFactory = RemotePayFallbackFactory.class)
public interface RemotePayService {

    /**
     * 支付宝支付
     * @param aliPay 请求参数
     * @see AliPay#toString()
     * @return 结果
     */
    @PostMapping(value = "/ali/payment")
    AjaxResult aliPayment(@RequestBody AliPay aliPay);

    /**
     * 支付宝订单 查询、撤销、关闭
     * @param aliPayDto 请求参数
     * @see AliPayDTO#toString()
     * @return 结果
     */
    @PostMapping(value = "/ali/after")
    AjaxResult aliAfter(@RequestBody AliPayDTO aliPayDto);

    /**
     * 小程序获取openid
     * @param openDto 请求参数
     * @see OpenDTO#toString()
     * @return 结果
     */
    @PostMapping(value = "/wechat/open_id")
    AjaxResult wechatOpenId(@RequestBody OpenDTO openDto);

    /**
     * 微信支付
     * @param wechatOpen 请求参数
     * @see WechatOpen#toString()
     * @return 结果
     */
    @PostMapping(value = "/wechat/payment")
    AjaxResult wechatPayment(@RequestBody WechatOpen wechatOpen, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 支付宝订单 查询、撤销、关闭
     * @param wechatDto 请求参数
     * @see WechatDTO#toString()
     * @return 结果
     */
    @PostMapping(value = "/wechat/after")
    AjaxResult wechatAfter(@RequestBody WechatDTO wechatDto);


}
