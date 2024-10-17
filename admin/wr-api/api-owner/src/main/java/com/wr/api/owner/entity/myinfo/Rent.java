package com.wr.api.owner.entity.myinfo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Wen Chou
 * @Date: 2022-11-14 14:16:02
 * @Desc:
 */
public class Rent {

    private static final long serialVersionUID = 377174444350968394L;

    /** 主键*/
    private Long rentId;

    private Long min;
    private Long max;

    /** 小区id，表xccj_community.bus_community*/
    private Long communityId;

    /** 联系电话*/
    private String communityName;

    /** 发布人账号id，表xccj_community.bus_account*/
    private Long accountId;

    /** 联系电话*/
    private String contactPhone;

    /** 户型*/
    private String doorModel;

    /** 所在楼层*/
    private Integer rentFloor;

    /** 房间类型：0=次卧，1=主卧*/
    private String roomType;

    /** 朝向*/
    private String roomToward;

    /** 房间面积*/
    private BigDecimal roomArea;

    /** 租赁方式：0=合租，1=整租*/
    private String rentType;

    /** 租金*/
    private BigDecimal rentPrice;

    /** 装修情况*/
    private String furnish;

    /** 地址*/
    private String address;

    /** 图片*/
    private String roomImg;

    /** 房屋配置（多选，英文逗号拼接）：0=冰箱，1=电视，2=洗衣机，3=空调，4=宽带，5=沙发，6=床，7=暖气，8=衣柜，9=独立卫生间，10=独立阳台，11=智能门锁，12=桌椅，13=微波炉，14=抽油烟机，15=燃气灶*/
    private String roomConfig;

    /** 房屋介绍*/
    private String roomInfo;

    /** 发布时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /** 浏览次数*/
    private Integer readNum;

    /** 状态：0=出租中，1=已出租，2=已下架*/
    private String rentStatus;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Long getRentId() {
        return rentId;
    }

    public void setRentId(Long rentId) {
        this.rentId = rentId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getDoorModel() {
        return doorModel;
    }

    public void setDoorModel(String doorModel) {
        this.doorModel = doorModel;
    }

    public Integer getRentFloor() {
        return rentFloor;
    }

    public void setRentFloor(Integer rentFloor) {
        this.rentFloor = rentFloor;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomToward() {
        return roomToward;
    }

    public void setRoomToward(String roomToward) {
        this.roomToward = roomToward;
    }

    public BigDecimal getRoomArea() {
        return roomArea;
    }

    public void setRoomArea(BigDecimal roomArea) {
        this.roomArea = roomArea;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getFurnish() {
        return furnish;
    }

    public void setFurnish(String furnish) {
        this.furnish = furnish;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomImg() {
        return roomImg;
    }

    public void setRoomImg(String roomImg) {
        this.roomImg = roomImg;
    }

    public String getRoomConfig() {
        return roomConfig;
    }

    public void setRoomConfig(String roomConfig) {
        this.roomConfig = roomConfig;
    }

    public String getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(String roomInfo) {
        this.roomInfo = roomInfo;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public String getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }
}
