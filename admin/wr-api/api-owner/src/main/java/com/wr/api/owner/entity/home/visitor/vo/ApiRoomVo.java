package com.wr.api.owner.entity.home.visitor.vo;

import com.wr.api.owner.entity.home.visitor.ApiRoom;
import lombok.Data;

/**
 * @Author: lusu
 * @Date: 2022-11-17 16:49:32
 * @Desc:  查业主详情，以及名下房产房产
 */

public class ApiRoomVo extends ApiRoom {


    private static final long serialVersionUID = 7404924690176483214L;


    /**
     * 小区名称
     */
    private String communityName;
    /**
     * 楼宇
     */
    private String buildingName;
    /**
     * 单元
     */
    private String unitName;


    /**
     *业主姓名
     */
    private String ownerName;

    /**
     * 业主手机号
     */
    private String  ownerPhone;


    /**
     * owner身份证
     */
    private String cardNo;


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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
