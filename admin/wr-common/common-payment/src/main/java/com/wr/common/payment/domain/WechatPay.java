package com.wr.common.payment.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author: RainCity
 * @Date: 2021-11-04 19:23:17
 * @Desc: 微信基础类
 */
@Data
public class WechatPay extends Payment {
    private static final long serialVersionUID = -3169594649435154096L;
    private Long wechatId;

    /**直连商户号*/
    private String mchId;
    /**商户API证书的证书序列号*/
    private String serialNumber;
    /**API v3密钥*/
    private String apiKey;
    /**商户私钥存储地址*/
    private String privateKeyUrl;

    private String clientIp;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("mchId", mchId)
                .append("serialNumber", serialNumber)
                .append("apiKey", apiKey)
                .append("privateKeyUrl", privateKeyUrl)
                .append("clientIp", clientIp)
                .toString() + super.toString();
    }
}
