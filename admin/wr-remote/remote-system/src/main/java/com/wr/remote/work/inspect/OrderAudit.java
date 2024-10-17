package com.wr.remote.work.inspect;

import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * 工单协助审核表
 * @Author: lvzy
 * @Date: 2022-10-31 19:55:11
 * @Desc: 工单协助审核表
 */
public class OrderAudit extends BaseEntity {

    private static final long serialVersionUID = 5027739665465133832L;

    /**
     * 主键
     */
    private Long auditId;

    /**
     * 审核类型:0=工单转交,1=工单协助
     */
    private String auditType;

    /**
     * 工单类型：见类 OrderConstants
     */
    private String orderType;

    /**
     * 工单id，order_type对应的表
     */
    private Long orderId;

    /**
     * 审核状态：0=审核中，1=通过，2=驳回
     */
    private String auditStatus;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 驳回理由
     */
    private String rejectReason;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 审批人 表（xccj_estate_sys.sys_user）
     */
    private Long userId;

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}