package com.wr.remote.work.inspect;

import com.wr.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 巡更计划表
 * @Author: lvzy
 * @Date: 2022-10-27 09:35:25
 * @Desc: 巡更计划表
 */
public class InspectPlan extends BaseEntity {

    private static final long serialVersionUID = 9034518993798284096L;

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
     * 巡检类型id，表xccj_work.bus_tour（tour_type=2）
     */
    private Long tourId;

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
    private Integer planNum;

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

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public String getPlanOrder() {
        return planOrder;
    }

    public void setPlanOrder(String planOrder) {
        this.planOrder = planOrder;
    }

    public String getPlanModel() {
        return planModel;
    }

    public void setPlanModel(String planModel) {
        this.planModel = planModel;
    }

    public String getWatchmanType() {
        return watchmanType;
    }

    public void setWatchmanType(String watchmanType) {
        this.watchmanType = watchmanType;
    }

    public Integer getPlanNum() {
        return planNum;
    }

    public void setPlanNum(Integer planNum) {
        this.planNum = planNum;
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

    public BigDecimal getGapTime() {
        return gapTime;
    }

    public void setGapTime(BigDecimal gapTime) {
        this.gapTime = gapTime;
    }

    public String getAllotWay() {
        return allotWay;
    }

    public void setAllotWay(String allotWay) {
        this.allotWay = allotWay;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}