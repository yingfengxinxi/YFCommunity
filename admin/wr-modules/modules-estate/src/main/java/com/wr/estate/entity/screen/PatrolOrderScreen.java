package com.wr.estate.entity.screen;

/**
 * @Author: GuanYu
 * @Date: 2021-09-26 17:53:52
 * @Desc: 小区工单
 */
public class PatrolOrderScreen {

    private static final long serialVersionUID = -8949534726788014138L;

    /**工单编号*/
    private String orderNo;
    /** 工单名称*/
    private String content;
    /** 巡检状态*/
    private String patrolStatus;
    /** 执行人*/
    private String staffName;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPatrolStatus() {
        return patrolStatus;
    }

    public void setPatrolStatus(String patrolStatus) {
        this.patrolStatus = patrolStatus;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    @Override
    public String toString() {
        return "PatrolOrder{" +
                "orderNo='" + orderNo + '\'' +
                ", content='" + content + '\'' +
                ", patrolStatus='" + patrolStatus + '\'' +
                ", staffName='" + staffName + '\'' +
                '}';
    }
}
