package com.wr.remote.estate.financial;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-10-25 11:33:11
 * @Desc:
 */
public class EstateCash extends BaseEntity {

    private static final long serialVersionUID = 2769687374275034658L;
    /** '小区ID' */
    private Long communityId;
    /** '拨款情况' */
    private String grantStatus;
    /** '住宅总面积' */
    private BigDecimal totalArea;
    /** '表明' */
    private String tableName;
    /** '门牌名称' */
    private String needName;
    /** '房屋名称' */
    @Excel(name = "门牌号",sort = 3)
    private String roomName;
    /** '门牌名称' */
    @Excel(name = "单元号",sort = 2)
    private String unitName;
    /** '拨款金额' */
    private BigDecimal grantAmount;
    /** '拨款方式' */
    private String grantType;
    /** '同意数量' */
    private Long agreeSum;
    /** 物业id */
    private Long estateId;
    private BigDecimal reallyPay;
    /** '不同意数量' */
    private Long disagreeSum;
    /** '同意率' */
    private String agreeRate;
    /** '投票主键' */
    private Long voteId;
    /** '投票名称' */
    private String voteTitle;
    /** '投票类型' */
    private Long voteType;
    /** '发起人Id' */
    private Long ownerId;
    /** '申请时间' */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;
    /** '投票开始时间' */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /** '投票结束时间' */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entTime;

    /** '审核状态' */
    private Long auditStatus;
    /** '审核时间' */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    /** '驳回理由' */
    private String rejectReason;
    /** '驳回理由' */
    private String content;
    /** '投票结果' */
    private Long voteStatus;
    @Excel(name = "投票结果",sort = 3)
    private String voteStatusValue;
    /** '投票时间' */
    @Excel(name = "投票时间",sort = 4, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date voteTime;
    /** '小区名称' */
    private String communityName;
    /** '楼栋ID' */
    private Long buildingId;
    /** '单元ID' */
    private Long unitId;
    /** '房屋ID' */
    private Long roomId;
    /** '楼栋名称' */
    private String buildingName;
    /** '业主名称' */
    @Excel(name = "业主名称",sort = 1)
    private String ownerName;
    /** '收费' */
    private BigDecimal feeAmount;
    /** '押金总计' */
    private BigDecimal depositSum;
    /** '收费总计' */
    private BigDecimal payAmountSum;
    /** '退款总计' */
    private BigDecimal refundAmount;
    /** '收费类型' */
    private String feeType;
    /** '文件地址' */
    private String annexUrl;
    /** '删除标记' */
    private Long delFlag;
    /** '缴费年限' */
    private Long payYear;
    /** '年限' */
    private Long yearVo;
    /** '年限' */
    private String years;
    /** '缴费状态' */
    @Excel(name = "缴费状态",sort = 7)
    private String payStatus;
    /** '已缴金额' */
    @Excel(name = "已缴金额",sort = 6)
    private BigDecimal payAmount;
    /** '应缴金额' */
    @Excel(name = "应缴金额",sort = 5)
    private BigDecimal shouldPay;
    /** '累计金额' */
    private BigDecimal cashCount;
    /** '实缴金额' */

    /** '今年累计金额' */
    private BigDecimal cashCountNow;
    /** '今年累计金额' */
    private BigDecimal count;
    /** '今年累计金额' */
    private BigDecimal cashCountLast;
    /** '住宅面积' */
    @Excel(name = "住宅面积",sort = 4)
    private BigDecimal roomArea;

    /** '住宅面积' */
    private BigDecimal totalmoney;
    /** '手机号码' */
    @Excel(name = "手机号码",sort = 2)
    private String ownerPhone;
    /** '缴费时间' */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "缴费时间",sort = 8)
    private Date payTime;
    /** '更新时间' */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /** '创建时间' */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** '更新时间' */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date grantTime;

    /** '更新时间' */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date yearUse;

    /** '未缴费户数' */
    private Long noPay;

    /** '户主数量' */
    private Long ownerCount;

    /**
     * 是否已完成
     */
    private Boolean hasFinish;

    /**
     * 是否已结束
     */
    private String finish;

    public String getVoteStatusValue() {
        return voteStatusValue;
    }

    public void setVoteStatusValue(String voteStatusValue) {
        this.voteStatusValue = voteStatusValue;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public Boolean getHasFinish() {
        return hasFinish;
    }

    public void setHasFinish(Boolean hasFinish) {
        this.hasFinish = hasFinish;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getGrantStatus() {
        return grantStatus;
    }

    public void setGrantStatus(String grantStatus) {
        this.grantStatus = grantStatus;
    }

    public BigDecimal getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(BigDecimal totalArea) {
        this.totalArea = totalArea;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNeedName() {
        return needName;
    }

    public void setNeedName(String needName) {
        this.needName = needName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getGrantAmount() {
        return grantAmount;
    }

    public void setGrantAmount(BigDecimal grantAmount) {
        this.grantAmount = grantAmount;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public Long getAgreeSum() {
        return agreeSum;
    }

    public void setAgreeSum(Long agreeSum) {
        this.agreeSum = agreeSum;
    }

    public Long getEstateId() {
        return estateId;
    }

    public void setEstateId(Long estateId) {
        this.estateId = estateId;
    }

    public BigDecimal getReallyPay() {
        return reallyPay;
    }

    public void setReallyPay(BigDecimal reallyPay) {
        this.reallyPay = reallyPay;
    }

    public Long getDisagreeSum() {
        return disagreeSum;
    }

    public void setDisagreeSum(Long disagreeSum) {
        this.disagreeSum = disagreeSum;
    }

    public String getAgreeRate() {
        return agreeRate;
    }

    public void setAgreeRate(String agreeRate) {
        this.agreeRate = agreeRate;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public Long getVoteType() {
        return voteType;
    }

    public void setVoteType(Long voteType) {
        this.voteType = voteType;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEntTime() {
        return entTime;
    }

    public void setEntTime(Date entTime) {
        this.entTime = entTime;
    }

    public Long getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Long auditStatus) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(Long voteStatus) {
        this.voteStatus = voteStatus;
    }

    public Date getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Date voteTime) {
        this.voteTime = voteTime;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
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

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public BigDecimal getDepositSum() {
        return depositSum;
    }

    public void setDepositSum(BigDecimal depositSum) {
        this.depositSum = depositSum;
    }

    public BigDecimal getPayAmountSum() {
        return payAmountSum;
    }

    public void setPayAmountSum(BigDecimal payAmountSum) {
        this.payAmountSum = payAmountSum;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl;
    }

    public Long getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Long delFlag) {
        this.delFlag = delFlag;
    }

    public Long getPayYear() {
        return payYear;
    }

    public void setPayYear(Long payYear) {
        this.payYear = payYear;
    }

    public Long getYearVo() {
        return yearVo;
    }

    public void setYearVo(Long yearVo) {
        this.yearVo = yearVo;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
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

    public BigDecimal getCashCount() {
        return cashCount;
    }

    public void setCashCount(BigDecimal cashCount) {
        this.cashCount = cashCount;
    }

    public BigDecimal getCashCountNow() {
        return cashCountNow;
    }

    public void setCashCountNow(BigDecimal cashCountNow) {
        this.cashCountNow = cashCountNow;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getCashCountLast() {
        return cashCountLast;
    }

    public void setCashCountLast(BigDecimal cashCountLast) {
        this.cashCountLast = cashCountLast;
    }

    public BigDecimal getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(BigDecimal roomArea) {
        this.roomArea = roomArea;
    }

    public BigDecimal getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(BigDecimal totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }

    public Date getYearUse() {
        return yearUse;
    }

    public void setYearUse(Date yearUse) {
        this.yearUse = yearUse;
    }

    public Long getNoPay() {
        return noPay;
    }

    public void setNoPay(Long noPay) {
        this.noPay = noPay;
    }

    public Long getOwnerCount() {
        return ownerCount;
    }

    public void setOwnerCount(Long ownerCount) {
        this.ownerCount = ownerCount;
    }

    @Override
    public String toString() {
        return "EstateCash{" +
                "communityId=" + communityId +
                ", communityName=" + communityName +
                ", buildingId=" + buildingId +
                ", unitId=" + unitId +
                ", roomId=" + roomId +
                ", buildingName='" + buildingName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", feeAmount=" + feeAmount +
                ", feeType='" + feeType + '\'' +
                ", delFlag=" + delFlag +
                ", payYear=" + payYear +
                ", payStatus='" + payStatus + '\'' +
                ", payAmount=" + payAmount +
                ", shouldPay=" + shouldPay +
                ", roomArea=" + roomArea +
                ", payTime=" + payTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
