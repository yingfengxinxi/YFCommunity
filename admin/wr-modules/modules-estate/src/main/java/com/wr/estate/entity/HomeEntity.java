package com.wr.estate.entity;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @author: bajie
 * @create: 2022/10/12
 * @Description:
 * @FileName: HomeEntity
 * @History:
 */
public class HomeEntity extends BaseEntity {


    /**
     * 小区id
     */
    private long communityId;

    /**
     * 小区名字
     */
    private String communityName;

    /**
     * 楼栋id
     */
    private long buildingId;

    /**
     * 楼栋名字
     */
    private String buildingName;

    /**
     * 单元id
     */
    private long unitId;

    /**
     * 单元名字
     */
    private String unitName;

    /**
     * 房间id
     */
    private long roomId;

    /**
     * 房间名字
     */
    private String roomName;

    /**
     * 房屋状态
     */
    private long roomStatus;


    /**
     * 产权人
     */
    private long ownerId;


    public long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(long roomStatus) {
        this.roomStatus = roomStatus;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "HomeEntity{" +
                "communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                ", buildingId=" + buildingId +
                ", buildingName='" + buildingName + '\'' +
                ", unitId=" + unitId +
                ", unitName='" + unitName + '\'' +
                ", roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomStatus=" + roomStatus +
                ", ownerId=" + ownerId +
                '}';
    }
}
