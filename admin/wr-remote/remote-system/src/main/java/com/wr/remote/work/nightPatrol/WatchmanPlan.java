package com.wr.remote.work.nightPatrol;

import java.math.BigDecimal;

import com.wr.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 巡更计划对象 bus_watchman_plan
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
public class WatchmanPlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long planId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 巡更类型id，表xccj_work.bus_tour（tour_type=1）
     */
    private Long tourId;

    /**
     * 巡更路线id，表xccj_work.bus_route
     */
    private Long routeId;

    /**
     * 是否生成工单：0=否，1=是
     */
    private String planOrder;

    /**
     * 巡查模式 ：0=一日多查，1=多日一查
     */
    private String planModel;

    /**
     * 巡更顺序：0=顺序，1=乱序
     */
    private String watchmanType;

    /**
     * 巡更次数/天次
     */
    private Long planNum;

    /**
     * 计划开始（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）
     */
    private String startTime;

    /**
     * 计划结束（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）
     */
    private String endTime;

    /**
     * 每次巡更间隔时间（plan_model=0）
     */
    private BigDecimal gapTime;

    /**
     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
     */
    private String allotWay;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanName() {
        return planName;
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

    public void setPlanOrder(String planOrder) {
        this.planOrder = planOrder;
    }

    public String getPlanOrder() {
        return planOrder;
    }

    public void setPlanModel(String planModel) {
        this.planModel = planModel;
    }

    public String getPlanModel() {
        return planModel;
    }

    public void setWatchmanType(String watchmanType) {
        this.watchmanType = watchmanType;
    }

    public String getWatchmanType() {
        return watchmanType;
    }

    public void setPlanNum(Long planNum) {
        this.planNum = planNum;
    }

    public Long getPlanNum() {
        return planNum;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setGapTime(BigDecimal gapTime) {
        this.gapTime = gapTime;
    }

    public BigDecimal getGapTime() {
        return gapTime;
    }

    public void setAllotWay(String allotWay) {
        this.allotWay = allotWay;
    }

    public String getAllotWay() {
        return allotWay;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("planId", getPlanId())
                .append("communityId", getCommunityId())
                .append("planName", getPlanName())
                .append("tourId", getTourId())
                .append("routeId", getRouteId())
                .append("planOrder", getPlanOrder())
                .append("planModel", getPlanModel())
                .append("watchmanType", getWatchmanType())
                .append("planNum", getPlanNum())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("gapTime", getGapTime())
                .append("allotWay", getAllotWay())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
