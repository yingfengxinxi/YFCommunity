package com.wr.api.owner.entity.home.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lusu
 * @Date: 2022-12-17 14:23:21
 * @Desc: 志愿者活动-人员关联表
 */
public class VoluntaryUser  implements Serializable {
    private static final long serialVersionUID = -6723368248959669414L;

    /**
     * 志愿者活动id，表xccj_govern.bus_voluntary
     */
    private Long voluntaryId;

    /**
     *  小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 人员姓名
     */
    private String userName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别：0=男，1=女，2=未知
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer userAge;

    /**
     * 证件类型：见数据字典 sys_certificate
     */
    private String cardType;

    /**
     * 证件号码
     */
    private String cardNo;


    /**
     * 联系电话
     */
    private String userPhone;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    public Long getVoluntaryId() {
        return voluntaryId;
    }

    public void setVoluntaryId(Long voluntaryId) {
        this.voluntaryId = voluntaryId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
}
