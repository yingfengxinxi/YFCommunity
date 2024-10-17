package com.wr.remote.estate.visitor;

import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2022/10/12 10:22
 * @Version 1.5
 */
public class Visitor extends BaseEntity {

    /**
     * 主键id
     */
    private Long logId;

    /**
     * 来访姓名
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
     * 拜访目标人
     */
    private String ownerName;

    /**
     * 拜访目标人手机号
     */
    private String ownerPhone;

    /**
     * 安康码颜色 1绿色，2黄色，3红色
     */
    private String healthLevel;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备唯一标识
     */
    private String indexCode;

    /**
     * 进门时间
     */
    private Date intoTime;

    /**
     * 出门时间
     */
    private Date outTime;


    /**
     * 删除状态 0 未删除 1 已删除
     */
    private String delFlag;

    private Date month;

    private String tableName;

    public Visitor() {
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

    public String getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(String healthLevel) {
        this.healthLevel = healthLevel;
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

    public Date getIntoTime() {
        return intoTime;
    }

    public void setIntoTime(Date intoTime) {
        this.intoTime = intoTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
