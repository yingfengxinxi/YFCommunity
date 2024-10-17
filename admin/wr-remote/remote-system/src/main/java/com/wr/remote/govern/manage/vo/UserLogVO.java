package com.wr.remote.govern.manage.vo;

import com.wr.remote.estate.log.UserLog;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 16:51:15
 * @Desc: 人员出入记录视图类
 */
public class UserLogVO extends UserLog {

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
     * 查询时间
     */
    private String queryTime;


    public UserLogVO() {

    }


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

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
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
}
