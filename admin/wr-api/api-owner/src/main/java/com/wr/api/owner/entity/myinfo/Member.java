package com.wr.api.owner.entity.myinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-11-11 17:21:14
 * @Desc:
 */
@Data
public class Member {
    private static final long serialVersionUID = 472238616430820958L;

    /** 党员id*/
    private Long memberId;

    /** 小区id，表xccj_community.bus_community*/
    private Long communityId;

    /** 楼栋id，表xccj_community.bus_building*/
    private Long buildingId;

    /** 单元id，表xccj_community.bus_unit*/
    private Long unitId;

    /** 当前所住房间id，表xccj_community.bus_room*/
    private Long roomId;

    /** 账号id，表xccj_community.bus_account*/
    private Long accountId;

    /** 入党时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date joinTime;

    /** 所属党组织id，表xccj_govern.bus_party*/
    private Long partyId;

    /** 证明图片*/
    private String proveUrl;

    /** 审核状态：0=审核中，1=通过，2=驳回*/
    private String auditStatus;

    /** 审核时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 驳回理由*/
    private String rejectReason;

    /** 党内职务*/
    private String memberDuty;
    /** 党员地址信息*/
    private String memberAdress;

    /** 个人党费*/
    private BigDecimal partyFee;

    /** 创建人*/
    private String createBy;

    /** 创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 修改人*/
    private String updateBy;

    /** 修改时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 删除状态 0 未删除 1 已删除*/
    private String delFlag;

    private String partyName;


    private String proveName;

    public String getProveName() {
        return proveName;
    }

    public void setProveName(String proveName) {
        this.proveName = proveName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public String getProveUrl() {
        return proveUrl;
    }

    public void setProveUrl(String proveUrl) {
        this.proveUrl = proveUrl;
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

    public String getMemberDuty() {
        return memberDuty;
    }

    public void setMemberDuty(String memberDuty) {
        this.memberDuty = memberDuty;
    }

    public BigDecimal getPartyFee() {
        return partyFee;
    }

    public void setPartyFee(BigDecimal partyFee) {
        this.partyFee = partyFee;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
