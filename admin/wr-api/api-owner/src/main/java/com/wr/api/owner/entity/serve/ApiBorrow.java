package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 借用申请表
 * @Author: lvzy
 * @Date: 2022-11-14 20:17:04
 * @Desc:借用申请表
 */
public class ApiBorrow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long borrowId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 物品id，表bus_goods
     */
    private Long goodsId;

    /**
     * 借用人账号id，表bus_account
     */
    private Long accountId;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 预计归还时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;


    /**
     * 实际归还时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date actuallyTime;

    /**
     * 审核状态：0=审核中，1=驳回，2=通过
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
     * 审批人
     */
    private String auditBy;

    /**
     * 借用说明
     */
    private String remark;

    /**
     * 二维码片路径
     */
    private String codeUrl;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Date getActuallyTime() {
        return actuallyTime;
    }

    public void setActuallyTime(Date actuallyTime) {
        this.actuallyTime = actuallyTime;
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

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "ApiBorrow{" +
                "borrowId=" + borrowId +
                ", communityId=" + communityId +
                ", goodsId=" + goodsId +
                ", accountId=" + accountId +
                ", applyTime=" + applyTime +
                ", returnTime=" + returnTime +
                ", actuallyTime=" + actuallyTime +
                ", auditStatus='" + auditStatus + '\'' +
                ", auditTime=" + auditTime +
                ", rejectReason='" + rejectReason + '\'' +
                ", auditBy='" + auditBy + '\'' +
                ", remark='" + remark + '\'' +
                ", codeUrl='" + codeUrl + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}