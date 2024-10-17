package com.wr.api.estate.entity.vo.application.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: bajie
 * @create: 2022/10/26
 * @Description:
 * @FileName: VenueAppoint
 * @History:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VenueAppointApiVo extends BaseEntity {


    private static final long serialVersionUID = 1L;

    private String venueImage;
    private String address;
    private String staffName;
    private String staffPhone;

    /** 主键 */
    private Long appointId;

    /** 小区id */
    private Long communityId;

    /** 预约人姓名 */
    @Excel(name = "预约人姓名")
    private String userName;

    /** 预约人手机号 */
    @Excel(name = "预约人手机号")
    private String userPhone;

    /** 预约人身份证号 */
    private String cardNo;

    /** 活动id */
    @Excel(name = "活动id")
    private Long activityId;

    /** 活动名称 */
    private String activityName;

    /** 场馆id */
    @Excel(name = "场馆id")
    private Long venueId;

    /** 计划开始时间 */
    @Excel(name = "计划开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date planStart;

    /** 计划结束时间 */
    @Excel(name = "计划结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date planEnd;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @JsonFormat(pattern = "HH:mm")
    private Date planStart1;

    @JsonFormat(pattern = "HH:mm")
    private Date planEnd1;





    /** 所付押金 */
    private BigDecimal deposit;

    /** 实际结束时间 */
    private Date currentEnd;

    /** 状态：0=未支付，1=已支付，2=已退款，3=无需退款 */
    @Excel(name = "状态：0=未支付，1=已支付，2=已退款，3=无需退款")
    private String appointStatus;

    /** 支付方式：0=微信，1=支付宝，2=现金 */
    @Excel(name = "支付方式：0=微信，1=支付宝，2=现金")
    private String payWay;

    /** 支付金额 */
    @Excel(name = "支付金额")
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

    /** 每小时费用 */
    @Excel(name = "每小时费用")
    private BigDecimal hourFee;
}
