package com.wr.api.owner.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: RainCity
 * @Date: 2022-11-17 13:48:29
 * @Desc: 物业缴费实体
 */
@Data
public class PropertyDTO implements Serializable {

    private static final long serialVersionUID = -7019541489644974799L;

    private Long communityId;

    private Long buildingId;

    private Long unitId;

    private Long roomId;

    private Long wechatId;

    private Long alipayId;

    /** 支付方式：H5 WAP APP*/
    private String payWay;

    private BigDecimal totalAmount;

    private Integer payYear;
}
