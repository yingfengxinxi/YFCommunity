package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 11:30:35
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadiumPointVo {

    private Long appointId;

    /** 主键 */
    private Long venueId;

    /** 活动名称 */
    private String activityName;


    /**
     * 场馆名称
     */
    private String venueName;

    /**
     * 场馆地址
     */
    private String address;

    /** 计划开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date planStart;

    /** 计划结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date planEnd;


    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String userName;

    private String userPhone;

    private String cardNo;

    /**
     * 状态：0=未支付，1=已支付，2=已退款，3=无需退款
     */
    private String appointStatus;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 每小时费用
     */
    private BigDecimal hourFee;


    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 活动类型
     */
    private String activityType;

}
