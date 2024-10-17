package com.wr.api.owner.entity.home.rescue;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author: lusus
 * @Date: 2022-11-15 10:06:55
 * @Desc:  社区救助表
 */
public class ApiRescue extends BaseEntity {

    private static final long serialVersionUID = -1007237545332685052L;

    /**
     * 主键
     */
    private Long rescueId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 标题
     */
    private String rescueTitle;


    /**
     * 所在位置
     */
    private String address;

    /**
     * 发布人类型：0=业主，1=租客
     */
    private String releaseType;

    /**
     * 发布人id，表xccj_community.bus_owner/bus_tenant
     */
    private Long releaseId;

    /**
     * 需要人数
     */
    private Long needNum;


    /**
     * 具体内容
     */
    private String content;


    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;


    /**
     * 紧急联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;


    public Long getRescueId() {
        return rescueId;
    }

    public void setRescueId(Long rescueId) {
        this.rescueId = rescueId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getRescueTitle() {
        return rescueTitle;
    }

    public void setRescueTitle(String rescueTitle) {
        this.rescueTitle = rescueTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    public Long getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Long releaseId) {
        this.releaseId = releaseId;
    }

    public Long getNeedNum() {
        return needNum;
    }

    public void setNeedNum(Long needNum) {
        this.needNum = needNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Rescue{" +
                "rescueId=" + rescueId +
                ", communityId=" + communityId +
                ", rescueTitle='" + rescueTitle + '\'' +
                ", address='" + address + '\'' +
                ", releaseType='" + releaseType + '\'' +
                ", releaseId=" + releaseId +
                ", needNum=" + needNum +
                ", content='" + content + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", releaseTime=" + releaseTime +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
