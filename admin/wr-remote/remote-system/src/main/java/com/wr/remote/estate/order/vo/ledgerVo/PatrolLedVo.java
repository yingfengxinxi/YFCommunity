package com.wr.remote.estate.order.vo.ledgerVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-10-27 09:48:55
 * @Desc:
 */
public class PatrolLedVo extends BaseEntity {

    private static final long serialVersionUID = -3285258571714369907L;

    /**
     * 主键
     */
    private Long patrolId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 工单编号
     */
    private String patrolNo;

    /**
     * 工单名称
     */
    private String patrolName;

    /**
     * 巡查点id
     */
    private Long pointId;

    /**
     * 巡查类型id
     */
    private Long tourId;

    /**
     * 工单来源：0=计划生成，1=后台添加，2=其他工单
     */
    private String dataSource;

    /**
     * 计划id，表xccj_work.bus_inspect_plan（data_source=0有值）
     */
    private Long planId;

    /**
     * 其他工单编号（data_source=2有值）
     */
    private String orderNo;

    /**
     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
     */
    private String allotWay;

    /**
     * 巡更次数/天次
     */
    private Integer patrolNum;

    /**
     * 每次巡更间隔时间（一日多查时有值）
     */
    private BigDecimal gapTime;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

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
     * 执行人id，表xccj_community.bus_staff
     */
    private Long executeId;

    /**
     * 执行时间
     */
    private String executeTime;

    /**
     * 完成时间
     */
    private String finishTime;

    /**
     * 完成图片
     */
    private String imageUrl;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    /*巡点名称*/
    private String pointName;
    /*巡点位置*/
    private String pointSite;
    /** 员工姓名 */
    private String staffName;
    /** 账号/手机号 */
    private String staffPhone;
    /** 身份证号码 */
    private String cardNo;
    /** 岗位：0=安防人员,1=维修人员,2=装修人员,3=场馆人员,4=保洁人员,5=志愿者 */
    private String workPost;
    /** 是否主要负责人 */
    private String whether;
    /**
     * 其他工单id
     */
    private Long orderId;
    /**
     * 异常id，表xccj_work.bus_abnormal
     */
    private Long abnormalId;

    /**
     * 异常描述，表xccj_work.bus_abnormal
     */
    private String abnormalInfo;
    /**
     * 异常状态，表xccj_work.bus_abnormal
     */
    private String status;
    /*异常状态*/
    private String inspectResult;

    /* 巡查类型名称*/
    private String tourName;


    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getPointSite() {
        return pointSite;
    }

    public void setPointSite(String pointSite) {
        this.pointSite = pointSite;
    }

    public String getInspectResult() {
        return inspectResult;
    }

    public void setInspectResult(String inspectResult) {
        this.inspectResult = inspectResult;
    }

    public Long getPatrolId() {
        return patrolId;
    }

    public void setPatrolId(Long patrolId) {
        this.patrolId = patrolId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getPatrolNo() {
        return patrolNo;
    }

    public void setPatrolNo(String patrolNo) {
        this.patrolNo = patrolNo;
    }

    public String getPatrolName() {
        return patrolName;
    }

    public void setPatrolName(String patrolName) {
        this.patrolName = patrolName;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAllotWay() {
        return allotWay;
    }

    public void setAllotWay(String allotWay) {
        this.allotWay = allotWay;
    }

    public Integer getPatrolNum() {
        return patrolNum;
    }

    public void setPatrolNum(Integer patrolNum) {
        this.patrolNum = patrolNum;
    }

    public BigDecimal getGapTime() {
        return gapTime;
    }

    public void setGapTime(BigDecimal gapTime) {
        this.gapTime = gapTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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

    public Long getExecuteId() {
        return executeId;
    }

    public void setExecuteId(Long executeId) {
        this.executeId = executeId;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
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

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getWorkPost() {
        return workPost;
    }

    public void setWorkPost(String workPost) {
        this.workPost = workPost;
    }

    public String getWhether() {
        return whether;
    }

    public void setWhether(String whether) {
        this.whether = whether;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAbnormalId() {
        return abnormalId;
    }

    public void setAbnormalId(Long abnormalId) {
        this.abnormalId = abnormalId;
    }

    public String getAbnormalInfo() {
        return abnormalInfo;
    }

    public void setAbnormalInfo(String abnormalInfo) {
        this.abnormalInfo = abnormalInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
