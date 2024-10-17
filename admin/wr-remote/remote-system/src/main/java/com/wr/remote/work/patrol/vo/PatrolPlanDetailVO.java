package com.wr.remote.work.patrol.vo;

import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-31 13:27:40
 * @Desc:
 */
public class PatrolPlanDetailVO {
    private static final long serialVersionUID = 1L;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 巡查类型名称
     */
    private String tourName;

    /**
     * 巡查点名称
     */
    private String pointName;

    /**
     * 是否生成工单：1=否，0=是
     */
    private String planOrder;

    /**
     * 巡查模式 ：0=一日多查，1=多日一查
     */
    private String planModel;

    /**
     * 巡查次数/频率
     */
    private String planNum;

    /**
     * 间隔时间
     */
    private Double gapTime;

    /**
     * 计划开始（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）
     */
    private String startTime;

    /**
     * 计划结束（格式：plan_model=0时HH:mm，plan_model=1时yyyy-MM-dd）
     */
    private String endTime;

    /**
     * 人员类型
     */
    private String userPosts;

    /**
     * 工单生成历史
     */
    private List<PatrolOrderListVO> patrolOrderList;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
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

    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum;
    }

    public Double getGapTime() {
        return gapTime;
    }

    public void setGapTime(Double gapTime) {
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

    public String getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(String userPosts) {
        this.userPosts = userPosts;
    }

    public List<PatrolOrderListVO> getPatrolOrderList() {
        return patrolOrderList;
    }

    public void setPatrolOrderList(List<PatrolOrderListVO> patrolOrderList) {
        this.patrolOrderList = patrolOrderList;
    }

    @Override
    public String toString() {
        return "PatrolPlanDetailVO{" +
                "planName='" + planName + '\'' +
                ", tourName='" + tourName + '\'' +
                ", pointName='" + pointName + '\'' +
                ", planOrder='" + planOrder + '\'' +
                ", planModel='" + planModel + '\'' +
                ", planNum='" + planNum + '\'' +
                ", gapTime=" + gapTime +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", userPosts='" + userPosts + '\'' +
                ", patrolPlanOrderList=" + patrolOrderList +
                '}';
    }
}
