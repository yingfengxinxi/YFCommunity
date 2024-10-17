package com.wr.system.business.domain.screen;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: LiuBei
 * @Date: 2022-01-08 16:04:34
 * @Desc:
 */
public class UserPeopleVo implements Serializable {
    private static final long serialVersionUID = 7222600609048384240L;
    /**用户姓名*/
    private String userName;
    /**用户类型*/
    private String userType;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    @Override
    public String toString() {
        return "UserPeopleVo{" +
                "userName='" + userName + '\'' +
                ", userType='" + userType + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", ioType='" + ioType + '\'' +
                ", healthLevel='" + healthLevel + '\'' +
                ", discernTime=" + discernTime +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
