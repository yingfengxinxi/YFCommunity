package com.wr.remote.estate.customer.decorate;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 11:25:18
 * @Desc: 装修类
 */
public class Decorate extends BaseEntity {

    private static final long serialVersionUID = -420508548893332238L;

    /**
     * 主键
     */
    private Long decorateId;
    /**
     * 小区id，表bus_community
     */
    private Long communityId;
    /**
     * 楼栋id，表bus_building
     */
    private Long buildingId;
    /**
     * 单元id，表bus_unit
     */
    private Long unitId;
    /**
     * 当前所住房间id，表bus_room
     */
    private Long roomId;
    /**
     * 业主id，表bus_owner
     */
    private Long ownerId;
    /**
     * 计划开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planStart;
    /**
     * 计划结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planEnd;
    /**
     * 装修押金
     */
    private BigDecimal deposit;
    /**
     * 审核状态：0=审核中，1=驳回，2=通过（进行中），3=延期待确认,4=已延期，5=已完成
     */
    private String auditStatus;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    /**
     * 驳回理由
     */
    private String rejectReason;
    /**
     * 延期天数（天）
     */
    private Integer delayDay;
    /**
     * 完成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date finishDate;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    /**
     * 申请类型：0=装修申请，1=延期申请
     */
    private String decorateType;
    /**
     * 审批人
     */
    private String auditBy;
    /**
     * 业主手机号
     */
    private String ownerPhone;
    /**
     * 业主姓名
     */
    private String ownerName;
    /**
     * 装修名称
     */
    private String decorateName;
    /**
     * 房间号
     */
    private String roomName;
    /**
     * 地址
     */
    private String address;

    public Long getDecorateId() {
        return decorateId;
    }

    public void setDecorateId(Long decorateId) {
        this.decorateId = decorateId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
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

    public Integer getDelayDay() {
        return delayDay;
    }

    public void setDelayDay(Integer delayDay) {
        this.delayDay = delayDay;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDecorateName() {
        return decorateName;
    }

    public void setDecorateName(String decorateName) {
        this.decorateName = decorateName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
