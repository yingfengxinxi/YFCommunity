package com.wr.remote.estate.order.ledger;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-03 16:58:55
 * @Desc: 居家维修工单表
 */
public class HomeOrder extends BaseEntity {

    private static final long serialVersionUID = 6894928450870988676L;
    /**
     * 主键
     */
    private Long orderId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 工单名称
     */
    private String orderName;

    /**
     * 业主id，表xccj_community.bus_owner
     */
    private Long ownerId;

    /**
     * 维修类型id，表xccj_work.bus_home_repair
     */
    private Long repairId;

    /**
     * 定金
     */
    private BigDecimal deposit;

    /**
     * 计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planStart;

    /**
     * 计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planEnd;

    /**
     * 地址
     */
    private String address;

    /**
     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
     */
    private String allotWay;

    /**
     * 负责人id，表xccj_community.bus_staff
     */
    private Long staffId;

    /**
     * 协助人数
     */
    private Integer assistNum;

    /**
     * 转交状态：0=无需转交，1=转交待确认
     */
    private String transferStatus;

    /**
     * 分配状态：0=待分配，1=已分配
     */
    private String allotStatus;

    /**
     * 接单状态：0=待接单，1=已接单
     */
    private String acceptStatus;

    /**
     * 执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
     */
    private String executeStatus;

    /**
     * 评定结果：0=合格，1=违规，2=整改完成
     */
    private String assessResult;

    /**
     * 执行人id，表xccj_community.bus_staff
     */
    private Long executeId;

    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date executeTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 状态：0=未支付，1=已支付
     */
    private String appointStatus;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 完成图片
     */
    private String imageUrl;

    /**
     * 评价状态：0=未评价，1=已评价
     */
    private String appraiseStatus;

    /**
     * 评价星级：0~5
     */
    private Integer appraiseStar;

    /**
     * 评价备注
     */
    private String appraiseRemark;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getRepairId() {
        return repairId;
    }

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAllotWay() {
        return allotWay;
    }

    public void setAllotWay(String allotWay) {
        this.allotWay = allotWay;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Integer getAssistNum() {
        return assistNum;
    }

    public void setAssistNum(Integer assistNum) {
        this.assistNum = assistNum;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getAllotStatus() {
        return allotStatus;
    }

    public void setAllotStatus(String allotStatus) {
        this.allotStatus = allotStatus;
    }

    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getAssessResult() {
        return assessResult;
    }

    public void setAssessResult(String assessResult) {
        this.assessResult = assessResult;
    }

    public Long getExecuteId() {
        return executeId;
    }

    public void setExecuteId(Long executeId) {
        this.executeId = executeId;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getAppointStatus() {
        return appointStatus;
    }

    public void setAppointStatus(String appointStatus) {
        this.appointStatus = appointStatus;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAppraiseStatus() {
        return appraiseStatus;
    }

    public void setAppraiseStatus(String appraiseStatus) {
        this.appraiseStatus = appraiseStatus;
    }

    public Integer getAppraiseStar() {
        return appraiseStar;
    }

    public void setAppraiseStar(Integer appraiseStar) {
        this.appraiseStar = appraiseStar;
    }

    public String getAppraiseRemark() {
        return appraiseRemark;
    }

    public void setAppraiseRemark(String appraiseRemark) {
        this.appraiseRemark = appraiseRemark;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
