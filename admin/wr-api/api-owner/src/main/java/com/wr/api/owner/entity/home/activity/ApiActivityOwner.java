package com.wr.api.owner.entity.home.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: lusu
 * @Date: 2022-11-16 13:24:04
 * @Desc: 活动-人员关联表
 */
@Data
public class ApiActivityOwner implements Serializable {
    private static final long serialVersionUID = -889473004916183653L;

    /**
     * 活动id，表bus_activity
     */
    private Long activityId;

    /**
     * 参与人员id，表bus_owner
     */
    private Long ownerId;

    /**
     * 类型id，表bus_act_type
     */
    private Long typeId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 报名时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date signInTime;
    /**
     * 签到时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date signUpTime;


    /**
     * 报名截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;


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
     * 当前表的状态， 1：bus_activity  2：bus_voluntary
     * 1：社区活动   2：志愿者活动
     */
    private String tableStatus;

    /**
     * 当前小区id
     */
    private Long communityId;

    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 参与人员姓名，表bus_owner
     */
    private String ownerName;

    /**
     * 参与人员联系方式，表bus_owner
     */
    private String ownerPhone;

    /**
     * 参与人员备注，表bus_owner
     */
    private String ownerRemark;

}
