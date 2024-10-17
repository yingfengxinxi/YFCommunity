package com.wr.estate.entity.vo;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @author: bajie
 * @create: 2022/10/12
 * @Description:
 * @FileName: OlderVo
 * @History:
 */
public class OlderVo extends BaseEntity {
    private long olderId;
    private long communityId;
    private long buildingId;
    private long unitId;
    private long roomId;
    private long ownerId;
    private String contactName;
    private String contactPhone;
    private long contactGender;
    private String contactId;
    private long relation;
    private String auditStatus;
    private String rejectReason;

    private String ownerName;

    private long oldGender;
    private long olderAge;
    private Integer age;
    private String cardNo;
    private String homeName;
    private String roomName;
    private String status;

    public long getOldGender() {
        return oldGender;
    }

    public void setOldGender(long oldGender) {
        this.oldGender = oldGender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OlderVo{" +
                "olderId=" + olderId +
                ", communityId=" + communityId +
                ", buildingId=" + buildingId +
                ", unitId=" + unitId +
                ", roomId=" + roomId +
                ", ownerId=" + ownerId +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactGender=" + contactGender +
                ", contactId='" + contactId + '\'' +
                ", relation=" + relation +
                ", auditStatus='" + auditStatus + '\'' +
                ", rejectReason='" + rejectReason + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", oldGender=" + oldGender +
                ", olderAge=" + olderAge +
                ", cardNo='" + cardNo + '\'' +
                ", homeName='" + homeName + '\'' +
                '}';
    }

    public long getOlderId() {
        return olderId;
    }

    public void setOlderId(long olderId) {
        this.olderId = olderId;
    }

    public long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(long communityId) {
        this.communityId = communityId;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public long getContactGender() {
        return contactGender;
    }

    public void setContactGender(long contactGender) {
        this.contactGender = contactGender;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public long getRelation() {
        return relation;
    }

    public void setRelation(long relation) {
        this.relation = relation;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getOlderAge() {
        return olderAge;
    }

    public void setOlderAge(long olderAge) {
        this.olderAge = olderAge;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
