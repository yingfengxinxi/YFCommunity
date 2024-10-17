package com.wr.remote.estate.manage.vo.contract;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @Author: lusu
 * @Date: 2022-10-14 16:46:32
 * @Desc: 业主Vo
 */
public class OwnerVo  extends BaseEntity {
    private static final long serialVersionUID = 3105003847010002784L;
    /**
     *小区id，表bus_community
     */
    private Long communityId;
    /**
     * 社区名称
     */
    private String communityName;
    /**
     * 楼宇主键
     */
    private Long buildingId;
    /**
     * 楼栋名
     */
    private String buildingName;
    /**
     * 单元主键
     */
    private Long unitId;
    /**
     * 单元名
     */
    private String unitName;

    /**
     * 主键
     */
    private Long roomId;
    /**
     * 房号
     */
    private String roomName;
    /**
     *业主姓名
     */
    private String ownerName;
    /**
     *手机号
     */
    private String ownerPhone;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    @Override
    public String toString() {
        return "OwnerVo{" +
                "communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                ", buildingId=" + buildingId +
                ", buildingName='" + buildingName + '\'' +
                ", unitId=" + unitId +
                ", unitName='" + unitName + '\'' +
                ", roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                '}';
    }
}
