package com.wr.remote.work.inspect;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单转交记录表
 * @Author: lvzy
 * @Date: 2022-11-01 15:51:21
 * @Desc: 工单转交记录表
 */
public class OrderTransfer implements Serializable {

    private static final long serialVersionUID = 5753291403456825809L;

    /**
     * 主键
     */
    private Long transferId;

    /**
     * 审核id
     */
    private Long auditId;

    /**
     * 工单类型：见类 OrderConstants
     */
    private String orderType;

    /**
     * 工单id，order_type对应的表
     */
    private Long orderId;

    /**
     * 转交人id，表xccj_community.bus_staff
     */
    private Long sourceId;

    /**
     * 接收人id，表xccj_community.bus_staff
     */
    private Long targetId;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 接收状态：0=待同意，1=同意，2=不同意
     */
    private String acceptType;

    /**
     * 不同意理由
     */
    private String rejectReason;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
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

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getAcceptType() {
        return acceptType;
    }

    public void setAcceptType(String acceptType) {
        this.acceptType = acceptType;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }
}
