package com.wr.remote.govern.party;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-10-24 17:10:31
 * @Desc: 党员活动(BusMemberAct)实体类
 */
@Data
public class MemberAct extends BaseEntity {

    private static final long serialVersionUID = -4538151535869888946L;
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
    private Date startTime;
    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 活动报名结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 时间
     */
    private String[] startEndTime;

    @Override
    public String toString() {
        return "MemberAct{" +
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
                '}';
    }
}

