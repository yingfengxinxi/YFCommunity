package com.wr.remote.work.inspect;

import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 巡检工单表
 * @Author: lvzy
 * @Date: 2022-10-25 11:45:31
 * @Desc: 巡检工单表
 */
public class Inspect extends BaseEntity {

    private static final long serialVersionUID = 5827045669902512415L;

    /**
     * 主键
     */
    private Long inspectId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 工单编号
     */
    private String inspectNo;

    /**
     * 工单名称
     */
    private String inspectName;

    /**
     * 工单来源：0=计划生成，1=后台添加，2=其他工单
     */
    private String dataSource;

    /**
     * 巡查类型id，表xccj_work.bus_tour（tour_type=0）
     */
    private Long tourId;

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

    /**
     * 巡更工单编号
     */
    private String watchmanNo;

    /**
     * 巡更工单名称
     */
    private String watchmanName;
    /**
     * 巡查工单编号
     */
    private String patrolNo;

    /**
     * 巡查工单名称
     */
    private String patrolName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getInspectId() {
        return inspectId;
    }

    public void setInspectId(Long inspectId) {
        this.inspectId = inspectId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getInspectNo() {
        return inspectNo;
    }

    public void setInspectNo(String inspectNo) {
        this.inspectNo = inspectNo;
    }

    public String getInspectName() {
        return inspectName;
    }

    public void setInspectName(String inspectName) {
        this.inspectName = inspectName;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
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

    public String getWatchmanNo() {
        return watchmanNo;
    }

    public void setWatchmanNo(String watchmanNo) {
        this.watchmanNo = watchmanNo;
    }

    public String getWatchmanName() {
        return watchmanName;
    }

    public void setWatchmanName(String watchmanName) {
        this.watchmanName = watchmanName;
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

    @Override
    public String toString() {
        return "Inspect{" +
                "inspectId=" + inspectId +
                ", communityId=" + communityId +
                ", inspectNo='" + inspectNo + '\'' +
                ", inspectName='" + inspectName + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", tourId=" + tourId +
                ", planId=" + planId +
                ", orderNo='" + orderNo + '\'' +
                ", allotWay='" + allotWay + '\'' +
                ", patrolNum=" + patrolNum +
                ", gapTime=" + gapTime +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", staffId=" + staffId +
                ", assistNum=" + assistNum +
                ", transferStatus='" + transferStatus + '\'' +
                ", allotStatus='" + allotStatus + '\'' +
                ", acceptStatus='" + acceptStatus + '\'' +
                ", executeStatus='" + executeStatus + '\'' +
                ", executeId=" + executeId +
                ", executeTime='" + executeTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", watchmanNo='" + watchmanNo + '\'' +
                ", watchmanName='" + watchmanName + '\'' +
                ", patrolNo='" + patrolNo + '\'' +
                ", patrolName='" + patrolName + '\'' +
                '}';
    }
}