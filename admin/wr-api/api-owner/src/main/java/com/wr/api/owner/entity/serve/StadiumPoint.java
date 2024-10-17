package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 11:29:05
 * @Desc: 场馆预约信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadiumPoint {
    /** 主键 */
    private Long appointId;

    /** 小区id */
    private Long communityId;

    /** 预约人姓名 */
    private String userName;

    /** 预约人手机号 */
    private String userPhone;

    /** 预约人身份证号 */
    private String cardNo;

    /** 活动id */
    private Long activityId;

    /** 活动名称 */
    private String activityName;

    /** 场馆id */
    private Long venueId;

    /** 计划开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date planStart;

    /** 计划结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date planEnd;

    /** 所付押金 */
    private BigDecimal deposit;

    /** 实际结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date currentEnd;

    /** 状态：0=未支付，1=已支付，2=已退款，3=无需退款 */
    private String appointStatus;

    /** 支付方式：0=微信，1=支付宝，2=现金 */
    private String payWay;

    /** 支付金额 */
    private BigDecimal payAmount;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;

    //退款方式：0=微信，1=支付宝，2=现金
    private Long refundWay;

    //退款金额
    private  BigDecimal refundAmount;

    //退款原因

    private String refundReason;

    private String statusName;


    private String venueName;

    private String actName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String createBy;

    private String accountName;

    //与下场间隔时间
    private Integer gapHour;

}
