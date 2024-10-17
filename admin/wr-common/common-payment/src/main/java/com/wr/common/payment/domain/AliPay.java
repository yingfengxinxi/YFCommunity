package com.wr.common.payment.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 支付宝支付相关账号
 * @Author RainCity
 * @Date 2021-11-01 15:03:50
 */
@Data
public class AliPay extends Payment {
    private static final long serialVersionUID = 7841089829356498764L;

    private Long alipayId;

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
     * 订单标题 不可使用特殊字符，如 /，=，& 等。
     */
    private String subject;

    /**
     * 付款码支付,用户付款中途退出返回商户网站的地址
     */
    private String quitUrl;

    /**
     * 用户支付授权码
     */
    private String authCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("appId", appId)
                .append("publicKey", publicKey)
                .append("privateKey", privateKey)
                .append("subject", subject)
                .append("quitUrl", quitUrl)
                .append("authCode", authCode)
                .toString() + super.toString();
    }
}
