package com.wr.remote.govern.party;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 党费缴费记录(BusPartyPay)实体类
 *
 * @author SJiang
 * @since 2022-11-01 14:34:58
 */
@Data
public class PartyPay extends BaseEntity {

    private static final long serialVersionUID = -1351017915108695869L;
    /**
     * 主键id
     */
    private Long logId;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 党组织id
     */
    private Long partyId;
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
     * 类型:1=正常,2=预缴,3=补缴
     */
    private String payType;
    /**
     * 缴费金额
     */
    private BigDecimal partyFee;
    /**
     * 缴费时间
     */
    @JsonFormat(pattern = "yyyy-MM")
    private Date payTime;
    /**
     * 缴费方式:1=支付宝,2=微信,3=线下
     */
    private String payWay;
    /**
     * 物业id
     */
    private Long estateId;
    /**
     * 缴费月份
     */
    private String payMonth;
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
        return "PartyPay{" +
                "logId=" + logId +
                ", communityId=" + communityId +
                ", partyId=" + partyId +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", payType='" + payType + '\'' +
                ", partyFee=" + partyFee +
                ", payTime=" + payTime +
                ", payWay='" + payWay + '\'' +
                ", estateId=" + estateId +
                ", payMonth='" + payMonth + '\'' +
                ", collectStatus='" + collectStatus + '\'' +
                ", collectName='" + collectName + '\'' +
                '}';
    }
}

