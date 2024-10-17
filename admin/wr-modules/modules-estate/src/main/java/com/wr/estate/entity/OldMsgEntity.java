package com.wr.estate.entity;

import com.wr.estate.entity.vo.OlderVo;

import java.security.PrivateKey;

/**
 * @author: bajie
 * @create: 2022/10/13
 * @Description:
 * @FileName: OldMsgEntity
 * @History:
 */
public class OldMsgEntity extends OlderVo {
    private String faceUrl;
    private String communityName;
    private long roomStatus;
    private String buildingName;
    private String unitName;
    private long ownerRelation;
    private String roomName;

    private  String ownerPhone;
    private String cardType;

    private  String rejectReason;

    private String cardFont;

    private String cardBack;

    public String getCardFont() {
        return cardFont;
    }

    public void setCardFont(String cardFont) {
        this.cardFont = cardFont;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    @Override
    public String toString() {
        return "OldMsgEntity{" +
                "faceUrl='" + faceUrl + '\'' +
                ", communityName='" + communityName + '\'' +
                ", roomStatus=" + roomStatus +
                ", buildingName='" + buildingName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", ownerRelation=" + ownerRelation +
                ", roomName='" + roomName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", cardType='" + cardType + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                '}';
    }

    @Override
    public String getRejectReason() {
        return rejectReason;
    }

    @Override
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }


    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public long getOwnerRelation() {
        return ownerRelation;
    }

    public void setOwnerRelation(long ownerRelation) {
        this.ownerRelation = ownerRelation;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public long getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(long roomStatus) {
        this.roomStatus = roomStatus;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

}
