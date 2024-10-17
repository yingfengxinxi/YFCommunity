package com.wr.remote.work.nightPatrol.vo;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-02 13:37:53
 * @Desc:
 */
public class NightPatrolPlanPointListVO {

    /**
     * 巡点名称
     */
    private String pointName;

    /**
     * 巡点地址
     */
    private String pointSite;

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

    @Override
    public String toString() {
        return "PointListVO{" +
                "pointName='" + pointName + '\'' +
                ", pointSite='" + pointSite + '\'' +
                '}';
    }
}
