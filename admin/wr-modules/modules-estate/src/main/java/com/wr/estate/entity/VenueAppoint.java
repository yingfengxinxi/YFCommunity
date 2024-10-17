package com.wr.estate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: bajie
 * @create: 2022/10/26
 * @Description:
 * @FileName: VenueAppoint
 * @History:
 */
public class VenueAppoint extends BaseEntity {


    private static final long serialVersionUID = 1L;

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
    @Excel(name = "计划结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
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


    public BigDecimal getHourFee() {
        return hourFee;
    }

    public void setHourFee(BigDecimal hourFee) {
        this.hourFee = hourFee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getPlanStart1() {
        return planStart1;
    }

    public void setPlanStart1(Date planStart1) {
        this.planStart1 = planStart1;
    }

    public Date getPlanEnd1() {
        return planEnd1;
    }

    public void setPlanEnd1(Date planEnd1) {
        this.planEnd1 = planEnd1;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Long getRefundWay() {
        return refundWay;
    }

    public void setRefundWay(Long refundWay) {
        this.refundWay = refundWay;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getAppointId() {
        return appointId;
    }

    public void setAppointId(Long appointId) {
        this.appointId = appointId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }

    public Date getPlanStart() {
        return planStart;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;
    }

    public Date getPlanEnd() {
        return planEnd;
    }

    public void setPlanEnd(Date planEnd) {
        this.planEnd = planEnd;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Date getCurrentEnd() {
        return currentEnd;
    }

    public void setCurrentEnd(Date currentEnd) {
        this.currentEnd = currentEnd;
    }

    public String getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(String appointStatus) {
        this.appointStatus = appointStatus;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "VenueAppoint{" +
                "appointId=" + appointId +
                ", communityId=" + communityId +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", venueId=" + venueId +
                ", planStart=" + planStart +
                ", planEnd=" + planEnd +
                ", deposit=" + deposit +
                ", currentEnd=" + currentEnd +
                ", appointStatus='" + appointStatus + '\'' +
                ", payWay='" + payWay + '\'' +
                ", payAmount=" + payAmount +
                ", delFlag='" + delFlag + '\'' +
                ", refundWay=" + refundWay +
                ", refundAmount=" + refundAmount +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
