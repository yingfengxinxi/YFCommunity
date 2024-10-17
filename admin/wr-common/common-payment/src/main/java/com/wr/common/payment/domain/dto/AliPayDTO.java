package com.wr.common.payment.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 18:39:05
 * @Desc: 支付宝支付，请求参数
 */
@Data
public class AliPayDTO implements Serializable {
    private static final long serialVersionUID = 2493258242955908992L;

    private Long alipayId;

    /**
     * 支付方式
     */
    private String method;
    /**
     * 商户appid
     */
    private String appId;
    /**
     * 支付宝公钥
     */
    private String publicKey;
    /**
     * 私钥 pkcs8格式的
     */
    private String privateKey;

    /**
     * 订单号
     */
    private String outTradeNo;

}
