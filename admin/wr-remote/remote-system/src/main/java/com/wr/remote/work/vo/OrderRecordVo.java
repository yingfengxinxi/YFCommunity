package com.wr.remote.work.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-10-31 16:05:12
 * @Desc: 灾害执行情况表
 */
public class OrderRecordVo {
    /**
     * 工单类型：见类 OrderConstants
     */
    private String orderType;

    /**
     * 工单id，order_type对应的表
     */
    private Long orderId;

    /**
     * 数据类型：0=其他，1=执行
     */
    private String dataType;

    /**
     * 结果
     */
    private String orderResult;

    /**
     * 提交人姓名
     */
    private String userName;

    /**
     * 相关人员姓名（多人）
     */
    private String relevant;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 评价星级
     */
    private String appraiseStar;

    /**
     * 评价备注
     */
    private String appraiseRemark;

    /**
     * 执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
     */
    private String executeStatus;

    /**
     * 评价状态：0=未评价，1=已评价
     */
    private String appraiseStatus;




    public String getAppraiseStar() {
        return appraiseStar;
    }

    public void setAppraiseStar(String appraiseStar) {
        this.appraiseStar = appraiseStar;
    }

    public String getAppraiseRemark() {
        return appraiseRemark;
    }

    public void setAppraiseRemark(String appraiseRemark) {
        this.appraiseRemark = appraiseRemark;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getAppraiseStatus() {
        return appraiseStatus;
    }

    public void setAppraiseStatus(String appraiseStatus) {
        this.appraiseStatus = appraiseStatus;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getOrderResult() {
        return orderResult;
    }

    public void setOrderResult(String orderResult) {
        this.orderResult = orderResult;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRelevant() {
        return relevant;
    }

    public void setRelevant(String relevant) {
        this.relevant = relevant;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
