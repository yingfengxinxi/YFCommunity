package com.wr.remote.work.nightPatrol;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 巡更工单对象 bus_watchman
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
public class Watchman extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long watchmanId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 巡更顺序：0=顺序，1=乱序
     */
    private String watchmanType;

    /**
     * 工单编号
     */
    private String watchmanNo;

    /**
     * 工单名称
     */
    private String watchmanName;

    /**
     * 巡更类型id，表xccj_work.bus_tour（tour_type=1）
     */
    private Long tourId;

    /**
     * 巡更路线id，表xccj_work.bus_route
     */
    private Long routeId;

    /**
     * 工单来源：0=计划生成，1=后台添加，2=其他工单
     */
    private String dataSource;

    /**
     * 计划id，表xccj_work.bus_watchman_plan（data_source=0有值）
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
    private Long patrolNum;

    /**
     * 每次巡更间隔时间（一日多查时有值）
     */
    private BigDecimal gapTime;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    /**
     * 负责人id，表xccj_community.bus_staff
     */
    private Long staffId;

    /**
     * 协助人数
     */
    private Long assistNum;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date executeTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;

    /**
     * 完成图片
     */
    private String imageUrl;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public void setWatchmanId(Long watchmanId) {
        this.watchmanId = watchmanId;
    }

    public Long getWatchmanId() {
        return watchmanId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setWatchmanNo(String watchmanNo) {
        this.watchmanNo = watchmanNo;
    }

    public String getWatchmanNo() {
        return watchmanNo;
    }

    public void setWatchmanName(String watchmanName) {
        this.watchmanName = watchmanName;
    }

    public String getWatchmanName() {
        return watchmanName;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setAllotWay(String allotWay) {
        this.allotWay = allotWay;
    }

    public String getAllotWay() {
        return allotWay;
    }

    public void setPatrolNum(Long patrolNum) {
        this.patrolNum = patrolNum;
    }

    public Long getPatrolNum() {
        return patrolNum;
    }

    public void setGapTime(BigDecimal gapTime) {
        this.gapTime = gapTime;
    }

    public BigDecimal getGapTime() {
        return gapTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setAssistNum(Long assistNum) {
        this.assistNum = assistNum;
    }

    public Long getAssistNum() {
        return assistNum;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setAllotStatus(String allotStatus) {
        this.allotStatus = allotStatus;
    }

    public String getAllotStatus() {
        return allotStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteId(Long executeId) {
        this.executeId = executeId;
    }

    public Long getExecuteId() {
        return executeId;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public String getWatchmanType() {
        return watchmanType;
    }

    public void setWatchmanType(String watchmanType) {
        this.watchmanType = watchmanType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("watchmanId", getWatchmanId())
                .append("communityId", getCommunityId())
                .append("watchmanNo", getWatchmanNo())
                .append("watchmanName", getWatchmanName())
                .append("tourId", getTourId())
                .append("routeId", getRouteId())
                .append("dataSource", getDataSource())
                .append("planId", getPlanId())
                .append("orderNo", getOrderNo())
                .append("allotWay", getAllotWay())
                .append("patrolNum", getPatrolNum())
                .append("gapTime", getGapTime())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("staffId", getStaffId())
                .append("assistNum", getAssistNum())
                .append("transferStatus", getTransferStatus())
                .append("allotStatus", getAllotStatus())
                .append("acceptStatus", getAcceptStatus())
                .append("executeStatus", getExecuteStatus())
                .append("executeId", getExecuteId())
                .append("executeTime", getExecuteTime())
                .append("finishTime", getFinishTime())
                .append("imageUrl", getImageUrl())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
