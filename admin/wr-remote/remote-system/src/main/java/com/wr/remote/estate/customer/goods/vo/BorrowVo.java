package com.wr.remote.estate.customer.goods.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-12 11:31:54
 * @Desc: 借用申请VO
 */
public class BorrowVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -94838369025003807L;

    /** 主键*/
    private Long borrowId;

    /** 小区id，表bus_community*/
    private Long communityId;

    /** 物品id，表bus_goods*/
    private Long goodsId;

    /** 借用人账号id，表bus_account*/
    private Long accountId;

    /** 申请时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** 预计归还时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    /** 审核状态：0=审核中，1=驳回，2=通过*/
    private String auditStatus;

    /** 审核时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 驳回理由*/
    private String rejectReason;

    /** 二维码片路径*/
    private String codeUrl;

    /** 删除标记：0=未删除，1=已删除*/
    private String delFlag;

    /** 用户名*/
    private String accountName;

    /** 账号（手机号）*/
    private String loginName;

    /** 物品编号*/
    private String goodsNo;

    /** 物品名称*/
    private String goodsName;

    /** 分类信息*/
    private String classifyName;

    /** 存放地址*/
    private String address;

    /** 分类id，表bus_classify*/
    private Long classifyId;

    /** 审批人*/
    private  String auditBy;


    /** 借用天数*/
    private Integer borrowDays;

    public Integer getBorrowDays() {
        return borrowDays;
    }

    public void setBorrowDays(Integer borrowDays) {
        this.borrowDays = borrowDays;
    }

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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
        return "BorrowVo{" +
                "borrowId=" + borrowId +
                ", communityId=" + communityId +
                ", goodsId=" + goodsId +
                ", accountId=" + accountId +
                ", applyTime=" + applyTime +
                ", returnTime=" + returnTime +
                ", auditStatus='" + auditStatus + '\'' +
                ", auditTime=" + auditTime +
                ", rejectReason='" + rejectReason + '\'' +
                ", codeUrl='" + codeUrl + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", accountName='" + accountName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", goodsNo='" + goodsNo + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", classifyName='" + classifyName + '\'' +
                ", address='" + address + '\'' +
                ", classifyId=" + classifyId +
                ", auditBy='" + auditBy + '\'' +
                '}';
    }
}
