package com.wr.api.owner.entity.home.activity;

import java.io.Serializable;

/**
 * @Author: lusu
 * @Date: 2022-11-16 13:36:52
 * @Desc: 活动-楼栋关联表
 */
public class ApiActBuilding implements Serializable {
    private static final long serialVersionUID = -114608193725519102L;

    /**
     * 活动id，表bus_activity
     */
    private Long activityId;

    /**
     * 楼栋id，表bus_building
     */
    private Long buildingId;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String toString() {
        return "ApiActBuilding{" +
                "activityId=" + activityId +
                ", buildingId=" + buildingId +
                '}';
    }
}
