package com.wr.remote.estate.order.vo.ledgerVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-10-28 16:50:33
 * @Desc: 巡更工单表
 */
public class WatchmanLedVo extends BaseEntity {

    private static final long serialVersionUID = 9010631245175306500L;
    /**
     * 主键
     */
    private Long watchmanId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 工单编号
     */
    private String watchmanNo;

    /**
     * 工单名称
     */
    private String watchmanName;

    /**
     * 巡查点id
     */
    private Long pointId;

    /**
     * 巡更类型id
     */
    private Long tourId;

    /**
     * 巡更路线id
     */
    private Long routeId;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 执行人id，表xccj_community.bus_staff
     */
    private Long executeId;

    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date executeTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

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
    /*巡更结果：0=待巡更，1=正常，2=异常*/
    private String watchmanResult;

    /* 巡更类型名称*/
    private String tourName;

    /*巡更路线名称*/
    private String routeName;

    /*巡更点数量*/
    private Integer pointNo;
    /*巡更顺序：0=顺序，1=乱序*/
    private String watchmanType;

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

    public Long getWatchmanId() {
        return watchmanId;
    }

    public void setWatchmanId(Long watchmanId) {
        this.watchmanId = watchmanId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
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

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
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

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointSite() {
        return pointSite;
    }

    public void setPointSite(String pointSite) {
        this.pointSite = pointSite;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getWatchmanResult() {
        return watchmanResult;
    }

    public void setWatchmanResult(String watchmanResult) {
        this.watchmanResult = watchmanResult;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Integer getPointNo() {
        return pointNo;
    }

    public void setPointNo(Integer pointNo) {
        this.pointNo = pointNo;
    }

    public String getWatchmanType() {
        return watchmanType;
    }

    public void setWatchmanType(String watchmanType) {
        this.watchmanType = watchmanType;
    }
}
