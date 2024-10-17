package com.wr.remote.work.nightPatrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-31 13:27:40
 * @Desc:
 */
public class NightPatrolPlanListVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long planId;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 巡查类型名称
     */
    private String tourName;

    /**
     * 巡查路线名称
     */
    private String routeName;

    /**
     * 巡更点数量
     */
    private Integer pointNumber;

    /**
     * 是否生成工单：1=否，0=是
     */
    private String planOrder;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

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

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Integer getPointNumber() {
        return pointNumber;
    }

    public void setPointNumber(Integer pointNumber) {
        this.pointNumber = pointNumber;
    }

    public String getPlanOrder() {
        return planOrder;
    }

    public void setPlanOrder(String planOrder) {
        this.planOrder = planOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "NightPatrolPlanListVO{" +
                "planId=" + planId +
                ", planName='" + planName + '\'' +
                ", tourName='" + tourName + '\'' +
                ", routeName='" + routeName + '\'' +
                ", pointNumber=" + pointNumber +
                ", planOrder='" + planOrder + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
