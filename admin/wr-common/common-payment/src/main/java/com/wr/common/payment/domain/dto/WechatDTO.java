package com.wr.common.payment.domain.dto;

import com.wr.common.payment.domain.WechatPay;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author: RainCity
 * @Date: 2021-11-02 10:06:44
 * @Desc: 微信支付请求参数
 */
@Data
public class WechatDTO extends WechatPay {
    private static final long serialVersionUID = 6829871211510243314L;

    /** 微信支付系统生成的订单号,回调返回 */
    private String transactionId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("transactionId", transactionId)
                .toString() + super.toString();
    }
}
