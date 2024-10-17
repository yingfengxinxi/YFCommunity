package com.wr.remote.work.fitment;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author: 晁盖
 * @Date: 2022-11-02 14:04:05
 * @Desc: 装修评定工单表
 */
public class DecorateOrder extends BaseEntity {

    private static final long serialVersionUID = 5829021297543698768L;

    /**
     * 主键
     */
    private Long orderId;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 装修id，表xccj_community.bus_decorate
     */
    private Long decorateId;
    /**
     * 工单编号
     */
    @Excel(name = "工单编号", width = 30)
    private String orderNo;
    /**
     * 工单名称
     */
    private String orderName;
    /**
     * 工单类型：0=装修中评定，1=装修后评定
     */
    private String orderType;
    /**
     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
     */
    @Excel(name = "工单分配方式", readConverterExp = "0=自动分配,1=管理员分配,2=抢单")
    private String allotWay;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 负责人id，表xccj_community.bus_staff
     */
    private Long staffId;
    /**
     * 协助人数
     */
    private Integer assistNum;
    /**
     * 转交状态：0=无需转交，1=转交待确认
     */
    private String transferStatus;
    /**
     * 分配状态：0=待分配，1=已分配
     */
    private String allotStatus;
    /**
     * 接单状态：0=待接单，1=已接单
     */
    private String acceptStatus;
    /**
     * 执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
     */
    private String executeStatus;
    /**
     * 评定结果：0=待评定，1=合格，2=违规，3=整改完成
     */
    @Excel(name = "评定结果", readConverterExp = "0=待评定,1=合格,2=违规,3=整改完成")
    private String assessResult;
    /**
     * 执行人id，表xccj_community.bus_staff
     */
    private Long executeId;
    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date executeTime;
    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
    /**
     * 完成图片
     */
    private String imageUrl;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    /**
     * 业主姓名
     */
    @Excel(name = "业主姓名")
    private String ownerName;
    /**
     * 房间号
     */
    @Excel(name = "房间号")
    private String roomName;
    /**
     * 地址
     */
    @Excel(name = "地址", width = 30)
    private String address;
    /**
     * 负责人姓名
     */
    private String staffName;

    /**
     * 更新者
     */
    @Excel(name = "操作人")
    private String updateBy;

    /**
     * 更新时间
     */
    @Excel(name = "最后操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 是否异常
     */
    private String abnormalType;
    /**
     * 住户是否损坏公共区域：0=否，1=是
     */
    private String damageStatus;
    /**
     * 住户装修楼道整洁度：0=否，1=是
     */
    private String neatStatus;


    /**
     * 备注
     */
    private String remark;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getDecorateId() {
        return decorateId;
    }

    public void setDecorateId(Long decorateId) {
        this.decorateId = decorateId;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAllotWay() {
        return allotWay;
    }

    public void setAllotWay(String allotWay) {
        this.allotWay = allotWay;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Integer getAssistNum() {
        return assistNum;
    }

    public void setAssistNum(Integer assistNum) {
        this.assistNum = assistNum;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getAllotStatus() {
        return allotStatus;
    }

    public void setAllotStatus(String allotStatus) {
        this.allotStatus = allotStatus;
    }

    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getAssessResult() {
        return assessResult;
    }

    public void setAssessResult(String assessResult) {
        this.assessResult = assessResult;
    }

    public Long getExecuteId() {
        return executeId;
    }

    public void setExecuteId(Long executeId) {
        this.executeId = executeId;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAbnormalType() {
        return abnormalType;
    }

    public void setAbnormalType(String abnormalType) {
        this.abnormalType = abnormalType;
    }

    public String getDamageStatus() {
        return damageStatus;
    }

    public void setDamageStatus(String damageStatus) {
        this.damageStatus = damageStatus;
    }

    public String getNeatStatus() {
        return neatStatus;
    }

    public void setNeatStatus(String neatStatus) {
        this.neatStatus = neatStatus;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
