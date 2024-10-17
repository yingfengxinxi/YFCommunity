package com.wr.api.owner.entity.home.visitor;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: lusu
 * @Date: 2022-11-17 17:55:19
 * @Desc: 用于封装业主名下的房产
 */
@Data
public class OwnerHouse implements Serializable {
    private static final long serialVersionUID = 2059740988917245862L;

    /**
     * 房屋id
     */
    private Long roomId;
    /**
     * 房间名
     */
    private String roomName;
    /**
     * 小区名
     */
    private String communityName;

    /**
     * 社区id
     */
    private Long communityId;
    /**
     * 楼宇名
     */
    private String buildingName;
    /**
     * 单元号
     */
    private String unitName;
    private Long buildingId;
    private Long unitId;
    private String roomAttribute;
    private String houseAddress;
    private String totalArea;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }
}
