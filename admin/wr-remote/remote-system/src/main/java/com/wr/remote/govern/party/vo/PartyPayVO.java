package com.wr.remote.govern.party.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-11-01 14:57:56
 * @Desc: 党费缴费记录 VO
 */
@Data
public class PartyPayVO implements Serializable {
    private static final long serialVersionUID = 5978320346073166422L;

    /**
     * 主键id
     */
    private Long logId;
    /**
     * 党员id
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
     * 党组织id
     */
    private Long partyId;
    /**
     * 党组织
     */
    private String partyName;
    /**
     * 缴费金额
     */
    private BigDecimal partyFee;
    /**
     * 缴费方式:1=支付宝,2=微信,3=线下
     */
    private String payWay;
    /**
     * 缴费时间
     */
    private Date payTime;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 缴费月份
     */
    private String payMonth;
    /**
     * 类型:1=正常,2=预缴,3=补缴
     */
    private String payType;
    /**
     * 归集状态:0=未归集,2=已归集
     */
    private String collectStatus;
    /**
     * 归集人
     */
    private String collectName;

    @Override
    public String toString() {
        return "PartyPayVO{" +
                "logId=" + logId +
                ", memberName='" + memberName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", partyFee=" + partyFee +
                ", payWay='" + payWay + '\'' +
                ", payTime=" + payTime +
                ", createBy='" + createBy + '\'' +
                '}';
    }
}
