package com.wr.remote.work.inspect;

import java.io.Serializable;

/**
 * 定时任务实体类
 * @Author: lvzy
 * @Date: 2022-11-04 17:42:19
 * @Desc: 定时任务实体类
 */
public class Timing implements Serializable {

    private static final long serialVersionUID = -5468326904896039876L;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 计划id
     */
    private Long planId;

    /**
     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
     */
    private String allotWay;

    /**
     * 巡更次数/天次
     */
    private Integer patrolNum;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 负责人id
     */
    private Long staffId;

    /**
     * 巡查模式 0=一日多查，1=多日一查',
     */
    private String planModel;

    /**
     * 巡更次数/天次
     */
    private Integer planNum;

    /**
     * 巡检类型id，表xccj_work.bus_tour（tour_type=2）
     */
    private Long tourId;

    /**
     * 计划名称
     */
    private String planName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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

    public String getPlanModel() {
        return planModel;
    }

    public void setPlanModel(String planModel) {
        this.planModel = planModel;
    }

    public Integer getPlanNum() {
        return planNum;
    }

    public void setPlanNum(Integer planNum) {
        this.planNum = planNum;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}