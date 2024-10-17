package com.wr.remote.work.inspect;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单执行情况表
 * @Author: lvzy
 * @Date: 2022-10-31 15:57:52
 * @Desc: 工单执行情况表
 */
public class OrderRecord implements Serializable {

    private static final long serialVersionUID = 5012735467535662044L;

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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

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
