package com.wr.api.owner.entity.home.memberAct;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lusu
 * @Date: 2022-11-24 14:09:38
 * @Desc: 党员管理
 */
public class ApMember extends BaseEntity {

    private static final long serialVersionUID = -6698093014662396006L;
    /**
     * 党员id
     */
    private Long memberId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 当前所住房间id
     */
    private Long roomId;

    /**
     * 当前账号的id
     */
    private Long accountId;


    /**
     * 入党时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date joinTime;

    /**
     * 所属党组织id，表xccj_govern.bus_party
     */
    private Integer partyId;


    /**
     * 证明图片
     */
    private String proveUrl;

    /**
     * 审核状态：0=审核中，1=通过，2=驳回
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
     * 党内职务
     */
    private String memberDuty;

    /**
     * 个人党费
     */
    private BigDecimal partyFee;


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

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
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


    @Override
    public String toString() {
        return "ApMember{" +
                "memberId=" + memberId +
                ", communityId=" + communityId +
                ", roomId=" + roomId +
                ", accountId=" + accountId +
                ", joinTime=" + joinTime +
                ", partyId=" + partyId +
                ", proveUrl='" + proveUrl + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", auditTime=" + auditTime +
                ", rejectReason='" + rejectReason + '\'' +
                ", memberDuty='" + memberDuty + '\'' +
                ", partyFee=" + partyFee +
                '}';
    }
}
