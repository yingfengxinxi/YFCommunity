package com.wr.remote.estate.customer.vo.decorate;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 晁盖
 * @Date: 2022-10-13 20:04:31
 * @Desc: 装修工人vo
 */
public class WorkerVo extends BaseEntity {

    private static final long serialVersionUID = -420840472513675859L;

    /**
     * 主键
     */
    private Long workerId;
    /**
     * 装修id
     */
    private Long decorateId;
    /**
     * 业主姓名
     */
    private String ownerName;
    /**
     * 联系方式
     */
    private String ownerPhone;
    /**
     * 所属楼栋
     */
    private String buildingName;
    /**
     * 单元号
     */
    private String unitName;
    /**
     * 门牌号
     */
    private String roomName;
    /**
     * 数据类型：0=首次，1=非首次
     */
    private String workerType;
    /**
     * 审核状态：0=审核中，1=驳回，2=通过
     */
    private String auditStatus;
    /**
     * 装修工姓名
     */
    private String workerName;
    /**
     * 手机号
     */
    private String workerPhone;
    /**
     * 身份证号码
     */
    private String cardNo;
    /**
     * 小区id
     */
    private Long communityId;
    /**
     * 计划装修开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planStart;
    /**
     * 计划装修结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planEnd;
    /**
     * 装修押金
     */
    private BigDecimal deposit;
    /**
     * 申请类型：0=装修申请，1=延期申请
     */
    private String decorateType;
    /**
     * 审批人
     */
    private String auditBy;
    /**
     * 装修工审批人
     */
    private String workerAuditBy;
    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    /**
     * 装修工审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date workerAuditTime;
    /**
     * 驳回理由
     */
    private String rejectReason;
    /**
     * 装修工驳回理由
     */
    private String workerRejectReason;

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Long getDecorateId() {
        return decorateId;
    }

    public void setDecorateId(Long decorateId) {
        this.decorateId = decorateId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getWorkerType() {
        return workerType;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
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

    public String getDecorateType() {
        return decorateType;
    }

    public void setDecorateType(String decorateType) {
        this.decorateType = decorateType;
    }

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getWorkerAuditTime() {
        return workerAuditTime;
    }

    public void setWorkerAuditTime(Date workerAuditTime) {
        this.workerAuditTime = workerAuditTime;
    }

    public String getWorkerAuditBy() {
        return workerAuditBy;
    }

    public void setWorkerAuditBy(String workerAuditBy) {
        this.workerAuditBy = workerAuditBy;
    }

    public String getWorkerRejectReason() {
        return workerRejectReason;
    }

    public void setWorkerRejectReason(String workerRejectReason) {
        this.workerRejectReason = workerRejectReason;
    }
}
