package com.wr.remote.work.patrol;

import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2022/10/26 10:06
 * @Version 1.5
 */
public class Point extends BaseEntity {

    /**
     * 主键
     */
    private Long pointId;

    private Long routeId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    private String communityName;

    /**
     * 巡点名称
     */
    private String pointName;

    /**
     * 类型：0=巡查点、巡更点，1=巡查点，2=巡更点
     */
    private String pointType;

    /**
     * 巡点所在位置
     */
    private String pointSite;

    /**
     * 状态：0=启用，1=停用
     */
    private String pointStatus;

    private String pointTypeStr;

    /**
     * 二维码路径
     */
    private String pointUrl;

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getPointSite() {
        return pointSite;
    }

    public void setPointSite(String pointSite) {
        this.pointSite = pointSite;
    }

    public String getPointStatus() {
        return pointStatus;
    }

    public void setPointStatus(String pointStatus) {
        this.pointStatus = pointStatus;
    }

    public String getPointUrl() {
        return pointUrl;
    }

    public void setPointUrl(String pointUrl) {
        this.pointUrl = pointUrl;
    }

    public String getPointTypeStr() {
        return pointTypeStr;
    }

    public void setPointTypeStr(String pointTypeStr) {
        this.pointTypeStr = pointTypeStr;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
