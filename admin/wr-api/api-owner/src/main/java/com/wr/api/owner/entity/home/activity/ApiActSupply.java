package com.wr.api.owner.entity.home.activity;

import java.io.Serializable;

/**
 * @Author: lusu
 * @Date: 2022-11-16 13:31:51
 * @Desc: 活动-物资关联表
 */
public class ApiActSupply implements Serializable {
    private static final long serialVersionUID = 5882269632135391672L;

    /**
     * 活动id，表bus_activity
     */
    private Long activityId;

    /**
     * 物资名称
     */
    private String supplyName;


    /**
     * 物资数量
     */
    private Integer supplyNum;




    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public Integer getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(Integer supplyNum) {
        this.supplyNum = supplyNum;
    }

    @Override
    public String toString() {
        return "ApiActSupply{" +
                "activityId=" + activityId +
                ", supplyName='" + supplyName + '\'' +
                ", supplyNum=" + supplyNum +
                '}';
    }
}
