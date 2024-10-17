package com.wr.remote.estate.order.vo;

/**
 * 工单协助审核扩展类
 *
 * @Author: lvzy
 * @Date: 2022-10-31 20:01:57
 * @Desc: 工单协助审核扩展类
 */
public class OrderAuditListVO {

    /**
     * 审核id
     */
    private Long auditId;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 工单名称
     */
    private String orderName;

    /**
     * 申请人
     */
    private String applyName;

    /**
     * 审核类型
     */
    private String auditType;

    /**
     * 审核状态
     */
    private String auditStatus;

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Override
    public String toString() {
        return "OrderAuditVo{" +
                "auditId=" + auditId +
                ", orderNo='" + orderNo + '\'' +
                ", orderName='" + orderName + '\'' +
                ", applyName='" + applyName + '\'' +
                ", auditType='" + auditType + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                '}';
    }
}
