package com.wr.common.payment.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * @Author: RainCity
 * @Date: 2021-11-02 10:47:19
 * @Desc: 微信支付相关账号
 */
@Data
public class WechatOpen extends WechatPay {
    private static final long serialVersionUID = -8116507424265889936L;

    /**
     * 应用ID
     */
    private String appId;
    /**
     * appSecret
     */
    private String appSecret;

    /**
     * 微信个人唯一标识
     */
    private String openId;

    /**
     * 商品描述
     */
    private String description;
    //微信接口地址
    private String url;
    //请求参数
    private String body;
    //商品订单号
    private String outTradeNo;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("appId", appId)
                .append("appSecret", appSecret)
                .append("openId", openId)
                .append("description", description)
                .toString() + super.toString();
    }
}
