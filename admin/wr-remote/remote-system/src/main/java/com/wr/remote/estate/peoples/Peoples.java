package com.wr.remote.estate.peoples;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2022/10/11 14:48
 * @Version 1.5
 */
public class Peoples extends BaseEntity {
    /**
     * 主键id
     */
    private Long logId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 用户类型 1 业主 2 租客
     */
    private String userType;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 识别小区
     */
    private String communityName;

    /**
     * 楼栋、单元、房间
     */
    private String roomName;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备唯一标识
     */
    private String indexCode;

    /**
     * 识别方式 1 人脸识别 2 蓝牙开门 3 安康码 4 二维码 5 社保卡
     */
    private String ioType;

    /**
     * 安康码颜色 1绿色，2黄色，3红色
     */
    private String healthLevel;

    /**
     * 识别时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date discernTime;

    /**
     * pic_url
     */
    private String picUrl;

    private String tableName;

    public String time;


    public Peoples() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
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

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
