package com.wr.api.owner.entity.home.memberAct;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author: lusu
 * @Date: 2022-11-16 17:43:52
 * @Desc: 党员活动
 */
public class ApiMemberAct extends BaseEntity {

    private static final long serialVersionUID = -6140805938282059436L;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 发起人账号id，表xccj_community.bus_account
     */
    private Long  accountId;


    /**
     * 活动图片
     */
    private String pictureUrl;

    /**
     * 活动主题
     */
    private String  actTheme;

    /**
     * 活动地点
     */
    private String address;

    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 活动状态 0 开启 1 结束
     */
    private String status;

    /**
     * 活动进度 0 活动待启动 1 活动报名中 2 活动结束
     */
    private String planStatus;

    /**
     * 活动详情
     */
    private  String content;


    /**
     * 删除状态 0 未删除 1 已删除
     */
    private String delFlag;


    /**
     * 党员id
     */
    private Long  memberId;


    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getActTheme() {
        return actTheme;
    }

    public void setActTheme(String actTheme) {
        this.actTheme = actTheme;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "ApiMemberAct{" +
                "activityId=" + activityId +
                ", communityId=" + communityId +
                ", accountId=" + accountId +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", actTheme='" + actTheme + '\'' +
                ", address='" + address + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                ", planStatus='" + planStatus + '\'' +
                ", content='" + content + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
