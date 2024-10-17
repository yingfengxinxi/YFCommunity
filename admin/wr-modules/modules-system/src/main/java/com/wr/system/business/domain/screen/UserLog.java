package com.wr.system.business.domain.screen;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class UserLog implements Serializable {
    private static final long serialVersionUID = -3765596798937932390L;
    /*主键*/
    private Long id;
    /**用户姓名*/
    private String userName;
    /**手机号*/
    private String userPhone;
    /**身份证号码*/
    private String idCard;
    /**用户类型*/
    private String userType;
    /**小区id*/
    private Long communityId;
    /**楼栋单元房号*/
    private String roomName;
    /**识别小区*/
    private String communityName;
    /**设备名称*/
    private String deviceName;
    /**识别方式*/
    private String ioType;
    /**安康吗颜色*/
    private String healthLevel;
    /**识别时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date discernTime;
    /**识别土图片*/
    private String picUrl;
    /**删除标记*/
    private String delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIoType() {
        return ioType;
    }

    public void setIoType(String ioType) {
        this.ioType = ioType;
    }

    public String getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(String healthLevel) {
        this.healthLevel = healthLevel;
    }

    public Date getDiscernTime() {
        return discernTime;
    }

    public void setDiscernTime(Date discernTime) {
        this.discernTime = discernTime;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", idCard='" + idCard + '\'' +
                ", userType='" + userType + '\'' +
                ", communityId=" + communityId +
                ", roomName='" + roomName + '\'' +
                ", communityName='" + communityName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", ioType='" + ioType + '\'' +
                ", healthLevel='" + healthLevel + '\'' +
                ", discernTime=" + discernTime +
                ", picUrl='" + picUrl + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
