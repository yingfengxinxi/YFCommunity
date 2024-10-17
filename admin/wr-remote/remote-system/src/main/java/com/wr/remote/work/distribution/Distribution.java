package com.wr.remote.work.distribution;

import com.wr.common.core.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/25 9:17
 * @Version 1.5
 */
public class Distribution extends BaseEntity {
    /**
     * 主键
     */
    private Long allotId;

    /**
     * 物业id，表xccj_estate_sys.bus_estate
     */
    private Long communityId;

    /**
     * 类型：0=巡查，1=巡更，2=巡检，3=保洁，4=装修评定，5=居家维修，6=园区报事，7=灾害报事，8=设备维修
     */
    private String allotType;

    /**
     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
     */
    private String allotWay;

    /**
     * 人员类型：对应表xccj_community.bus_staff.work_post，英文逗号拼接
     */
    private String userType;

    /**
     * 协助人数
     */
    private Integer assistNum;

    /**
     * 间隔时长
     */
    private Integer gapHour;

    /**
     * 人员类型
     */
    private String workPost;


    private String communityName;


    private String[] userTypes;
//    private List<String> userTypes=new ArrayList<>();

    public Long getAllotId() {
        return allotId;
    }

    public void setAllotId(Long allotId) {
        this.allotId = allotId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getAllotType() {
        return allotType;
    }

    public void setAllotType(String allotType) {
        this.allotType = allotType;
    }

    public String getAllotWay() {
        return allotWay;
    }

    public void setAllotWay(String allotWay) {
        this.allotWay = allotWay;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getAssistNum() {
        return assistNum;
    }

    public void setAssistNum(Integer assistNum) {
        this.assistNum = assistNum;
    }

    public Integer getGapHour() {
        return gapHour;
    }

    public void setGapHour(Integer gapHour) {
        this.gapHour = gapHour;
    }

    public String getWorkPost() {
        return workPost;
    }

    public void setWorkPost(String workPost) {
        this.workPost = workPost;
    }

    public String[] getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(String[] userTypes) {
        this.userTypes = userTypes;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
