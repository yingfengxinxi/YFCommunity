package com.wr.api.owner.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 党费缴费实体
 * @Author: lvzy
 * @Date: 2022-11-17 17:18:03
 * @Desc:党费缴费实体
 */
@Data
public class PartyDTO implements Serializable {

    private static final long serialVersionUID = -4800385510687498820L;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 所属党组织id，表xccj_govern.bus_party
     */
    private Long partyId;

    /**
     * 党员id，表xccj_govern.bus_member
     */
    private Long memberId;

    /**
     * 党员姓名
     */
    private String memberName;

    /**
     * 党员手机号
     */
    private String memberPhone;

    /**
     * 类型:1=正常,2=预缴,3=补缴
     */
    private String payType;

    /**
     * 缴费金额
     */
    private BigDecimal partyFee;

    /**
     * 缴费月份（yyyy-MM）
     */
    private String payMonth;

    private Long wechatId;

    private Long alipayId;

    /** 支付方式：H5 WAP APP*/
    private String payWay;
}
