package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 党费缴费记录
 * @Author: lvzy
 * @Date: 2022-11-17 11:51:30
 * @Desc:党费缴费记录
 */
@Data
public class ApiPartyPay extends BaseEntity implements Serializable {

    /**
     * 主键id
     */
    private Long logId;

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

    /**
     * 缴费时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 缴费方式:1=支付宝,2=微信,3=线下
     */
    private String payWay;

    /**
     * 归集状态:0=未归集,1=已归集
     */
    private String collectStatus;

    /**
     * 归集人
     */
    private String collectName;

    /**
     * 归集时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date collectTime;

    /**
     * 删除状态 0 未删除 1 已删除
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}