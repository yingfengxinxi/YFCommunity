package com.wr.api.payment.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: RainCity
 * @Date: 2021-11-17 09:50:00
 * @Desc: 支付参数
 */
@Data
public class PayDTO implements Serializable {
    private static final long serialVersionUID = -7271298042882633628L;

    private Long communityId;

    private Long wechatId;

    private Long alipayId;

    private String roadwayCode;

    private String userType;

    private String userName;

    private String userPhone;

    private BigDecimal totalAmount;

    private String plantNo;

    private Long inLogId;
    private String inTableName;
    private Long outLogId;
    private String outTableName;

}
