package com.wr.api.owner.entity.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 党员活动
 * @Author: lvzy
 * @Date: 2022-11-15 15:08:55
 * @Desc:党员活动
 */
@Data
public class MemberAct extends BaseEntity implements Serializable {

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
    private Long accountId;

    /**
     * 活动图片
     */
    private String pictureUrl;

    /**
     * 活动主题
     */
    private String actTheme;

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
     * 报名截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

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
    private String content;

    /**
     * 删除状态 0 未删除 1 已删除
     */
    private String delFlag;

    private static final long serialVersionUID = 1L;
}