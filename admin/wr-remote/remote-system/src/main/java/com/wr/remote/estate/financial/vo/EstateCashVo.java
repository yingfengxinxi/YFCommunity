package com.wr.remote.estate.financial.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-12 14:02:33
 * @Desc:
 */
public class EstateCashVo extends BaseEntity {

    /** '小区ID' */
    private Long communityId;
    /** '楼栋ID' */
    private Long buildingId;
    /** '户主数量' */
    private Long ownerCount;
    /** '单元ID' */
    private Long unitId;
    /** '房屋ID' */
    private Long roomId;
    /** '住宅总面积' */
    private BigDecimal totalArea;
    /** '收费类型' */
    private String feeType;
    /** '删除标记' */
    private Long delFlag;
    /** '业主名称' */
    private String ownerName;
    /** '收费标准' */
    private BigDecimal feeAmount;
    /** '楼栋名称' */
    private String buildingName;
    /** '已缴金额' */
    private BigDecimal payAmount;
    /** '应缴金额' */
    private BigDecimal shouldPay;
    /** '缴费年限' */
    private Long payYear;
    /** '未缴费户数' */
    private Long noPay;
    /** '创建时间' */
    @JsonFormat(pattern ="yyyy-MM-dd" )
    private Date createTime;
    /** '更新时间' */
    @JsonFormat(pattern ="yyyy-MM-dd" )
    private Date updateTime;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getOwnerCount() {
        return ownerCount;
    }

    public void setOwnerCount(Long ownerCount) {
        this.ownerCount = ownerCount;
    }

    public BigDecimal getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(BigDecimal totalArea) {
        this.totalArea = totalArea;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Long getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Long delFlag) {
        this.delFlag = delFlag;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(BigDecimal shouldPay) {
        this.shouldPay = shouldPay;
    }

    public Long getPayYear() {
        return payYear;
    }

    public void setPayYear(Long payYear) {
        this.payYear = payYear;
    }

    public Long getNoPay() {
        return noPay;
    }

    public void setNoPay(Long noPay) {
        this.noPay = noPay;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public String toString() {
        return "EstateCashVo{" +
                "communityId=" + communityId +
                ", buildingId=" + buildingId +
                ", ownerCount=" + ownerCount +
                ", unitId=" + unitId +
                ", roomId=" + roomId +
                ", totalArea=" + totalArea +
                ", feeType='" + feeType + '\'' +
                ", delFlag=" + delFlag +
                ", ownerName='" + ownerName + '\'' +
                ", feeAmount=" + feeAmount +
                ", buildingName='" + buildingName + '\'' +
                ", payAmount=" + payAmount +
                ", shouldPay=" + shouldPay +
                ", payYear=" + payYear +
                ", noPay=" + noPay +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
