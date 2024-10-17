package com.wr.remote.work.patrol;

import com.wr.common.core.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/27 9:59
 * @Version 1.5
 */
public class Route extends BaseEntity {
    /**
     * 主键
     */
    private Long routeId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 巡点表 id
     */
    private Long pointId;

    private List<Long> pointIdList =new ArrayList<>();

    /**
     * 巡点名称
     */
    private String pointName;

    /**
     * 巡点数量
     */
    private Integer pointNumber;

    /**
     * 路线名称
     */
    private String routeName;

    /**
     * 状态：0=启用，1=停用
     */
    private String routeStatus;


    public List<Long> getPointIdList() {
        return pointIdList;
    }

    public void setPointIdList(List<Long> pointIdList) {
        this.pointIdList = pointIdList;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getPointNumber() {
        return pointNumber;
    }

    public void setPointNumber(Integer pointNumber) {
        this.pointNumber = pointNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteStatus() {
        return routeStatus;
    }

    public void setRouteStatus(String routeStatus) {
        this.routeStatus = routeStatus;
    }
}
