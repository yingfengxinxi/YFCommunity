package com.wr.common.payment.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: RainCity
 * @Date: 2021-11-01 14:10:09
 * @Desc: 支付参数
 */
@Data
public class Payment implements Serializable {
    private static final long serialVersionUID = -3412987298214259851L;

    /**支付方式 */
    private String method;
    /**订单号*/
    private String outTradeNo;
    /**支付金额 */
    private BigDecimal totalAmount;
    /**
     * 交易绝对时间
     * 支付宝格式：yyyy-MM-dd HH:mm:ss
     * 微信格式：YYYY-MM-DDTHH:mm:ss+TIMEZONE
     */
    private String timeExpire;
    /**支付回调地址*/
    private String notifyUrl;
    /**支付跳转地址*/
    private String returnUrl;
    /**支付时的附加参数*/
    private String attach;

}
