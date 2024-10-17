package com.wr.system.business.domain.screen;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * @Author: LiuBei
 * @Date: 2022-01-08 16:04:34
 * @Desc:
 */
public class UserDoorPeopleVo implements Serializable {
    private static final long serialVersionUID = 7222600609048384240L;
    /**
     * 姓名
     */
    private String personName;
    /**
     * 设备名称
     */
    private String doorName;
    /**
     * 进出类型
     */
    private String inAndOutType;
    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String receiveTime;
    /**
     * 社区名称
     */
    private String communityName;

    /**
     * 社区id
     */
    private Long communityId;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public String getInAndOutType() {
        return inAndOutType;
    }

    public void setInAndOutType(String inAndOutType) {
        this.inAndOutType = inAndOutType;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    @Override
    public String toString() {
        return "UserDoorPeopleVo{" +
                "personName='" + personName + '\'' +
                ", doorName='" + doorName + '\'' +
                ", inAndOutType='" + inAndOutType + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", communityName='" + communityName + '\'' +
                ", communityId=" + communityId +
                '}';
    }
}
