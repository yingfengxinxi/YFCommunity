package com.wr.remote.govern.manage.vo;

import com.wr.remote.govern.manage.VehicleLog;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-02 09:22:15
 * @Desc: 车辆进出记录详情视图类
 */
public class VehicleDetailLogVO extends VehicleLogVO {

    /**
     * 姓名
     */
    private String avatar;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 证件类型
     */
    private String cardType;


    /**
     * 车辆记录列表
     */
    private List<VehicleLogVO> vehicleLogVOs;


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getCommunityName() {
        return communityName;
    }

    @Override
    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public List<VehicleLogVO> getVehicleLogVOs() {
        return vehicleLogVOs;
    }

    public void setVehicleLogVOs(List<VehicleLogVO> vehicleLogVOs) {
        this.vehicleLogVOs = vehicleLogVOs;
    }
}
