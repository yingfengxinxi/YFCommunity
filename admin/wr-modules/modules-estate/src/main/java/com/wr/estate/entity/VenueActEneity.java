package com.wr.estate.entity;

import com.wr.common.core.web.domain.BaseEntity;

/**
 * @author: bajie
 * @create: 2022/10/24
 * @Description:
 * @FileName: VenueActEneity
 * @History:
 */
public class VenueActEneity extends BaseEntity {

    private Long activityId;

    private String communityName;
    private Long communityId;

    private String activityName;

    private String activityStatus;
    private String activityStatusName;


    public String getActivityStatusName() {
        return activityStatusName;
    }

    public void setActivityStatusName(String activityStatusName) {
        this.activityStatusName = activityStatusName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    @Override
    public String toString() {
        return "VenueActEneity{" +
                "activityId=" + activityId +
                ", communityName='" + communityName + '\'' +
                ", communityId=" + communityId +
                ", activityName='" + activityName + '\'' +
                ", activityStatus=" + activityStatus +
                '}';
    }
}
