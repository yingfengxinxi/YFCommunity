package com.wr.remote.estate.custom.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 荀彧
 * @Date: 2022-10-11 15:33:44
 * @Desc: 投诉表扬VO
 */
public class ComplanintPraiseVO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 11L;

    // 投诉表扬表主键
    private Long complaintId;

    // 小区Id
    private Long communityId;
    // 账号表主键
    private Long accountId;
    // 标题
    private String title;
    // 提出人
    private String accountName;
    // 类型
    private String complaintType;
    // 工单类型
    private Long orderType;
    // 数据源
    private String dataSource;
    // 工单id
    private Long orderId;
    // 工单编号
    private Long orderNo;
    // 图片路径
    private String complaintUrl;


    // 是否公示
    private String publicStatus;
    // 提出时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    public ComplanintPraiseVO() {
    }

    public String getPublicStatus() {
        return publicStatus;
    }

    public void setPublicStatus(String publicStatus) {
        this.publicStatus = publicStatus;
    }


    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public Long getOrderType() {
        return orderType;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getComplaintUrl() {
        return complaintUrl;
    }

    public void setComplaintUrl(String complaintUrl) {
        this.complaintUrl = complaintUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
