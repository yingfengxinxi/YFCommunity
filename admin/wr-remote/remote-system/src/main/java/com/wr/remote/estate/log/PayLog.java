package com.wr.remote.estate.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: RainCity
 * @Date: 2021-11-18 14:00:12
 * @Desc: 支付日志
 */
@Data
public class PayLog implements Serializable {
    private static final long serialVersionUID = -4041722363900586222L;

    private Long logId;

    /**缴费类型：0=停车缴费，2=水费，3=电费，4=其他*/
    private String payType;
    /**缴费金额*/
    private BigDecimal payAmount;
    /**缴费时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    /**人员类型：0=业主，1=租客，2=访客，3=物业人员*/
    private String userType;
    /**缴费人姓名*/
    private String userName;
    /**缴费人手机号*/
    private String userPhone;
    /**车牌号*/
    private String plateNo;
    /**所属小区*/
    private Long communityId;
    private Long buildingId;
    private Long unitId;
    private Long roomId;
    /**小区名称*/
    private String communityName;
    /**支付类型：0=支付宝，1=微信，2=农行，3=其它，4=线下*/
    private String payWay;
    /**缴费状态：1 正常  2 预缴  3 补缴*/
    private String payStatus;
    /**交易订单号*/
    private String outTradeNo;
    /**微信支付订单号*/
    private String transactionId;

    /**农行签约成功后返回的签约编号*/
    private String agentSignNo;
    /**农行账单编号*/
    private String billNo;
    /**农行流水号*/
    private String traceNo;
    /**农行扣款状态:0=默认状态，1=扣款成功，2=扣款失败，3=扣款异常*/
    private String tradeStatus;

    /**删除标记：0=未删除，1=已删除*/
    private String delFlag;

    private String tableName;

}
