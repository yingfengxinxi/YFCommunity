package com.wr.api.owner.entity.home.visitor.vo;

import com.wr.api.owner.entity.home.visitor.ApiLiaison;

/**
 * @Author: lusu
 * @Date: 2022-11-17 08:39:49
 * @Desc: 通行证vo类
 */


public class ApiLiaisonVo extends ApiLiaison {


    private static final long serialVersionUID = 541214240135001071L;


    /**
     *业主姓名
     */
    private String ownerName;


    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 楼栋名称
     */
    private String buildingName;


    /**
     * 单位名称
     */
    private String unitName;


    /**
     * 房号名称
     */
    private String roomName;


    /**
     * 业主手机号码
     */
    private String ownerPhone;


    /**
     * 用户的类型
     */
    private String accountType;



    /**
     * 证件类型
     */
    private String cardType;

    /**
     * 小区id(业主可能在不同的小区都有房产， 则需要确定是那个小区的)
     *
     */
    private Long communityId;


    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
