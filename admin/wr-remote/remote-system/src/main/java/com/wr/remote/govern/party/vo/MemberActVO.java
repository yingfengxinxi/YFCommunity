package com.wr.remote.govern.party.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author: SJiang
 * @Date: 2022-10-31 11:16:41
 * @Desc: 党员活动 VO
 */
@Data
public class MemberActVO implements Serializable {
    private static final long serialVersionUID = -5355939019855258324L;

    /**
     * 活动id
     */
    private Long activityId;
    /**
     * 活动主题
     */
    private String actTheme;
    /**
     * 创建者
     */
    private String createBy;
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

    //活动状态0=未开始1=进行中2=结束
    private String state;
    /**
     * 已报名人数
     */
    private Integer toSignUpNumber;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 小区
     */
    private String communityName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 活动图片
     */
    private String pictureUrl;
    /**
     * 地点
     */
    private String address;
    /**
     * 请求参数
     */
    private Map<String, Object> params;
    /**
     * 物业id
     */
    private Long estateId;
    /**
     * 活动详情
     */
    private String content;

    @Override
    public String toString() {
        return "MemberActVO{" +
                "activityId=" + activityId +
                ", actTheme='" + actTheme + '\'' +
                ", createBy='" + createBy + '\'' +
                ", startTime=" + startTime +
                ", status='" + status + '\'' +
                ", planStatus='" + planStatus + '\'' +
                ", toSignUpNumber=" + toSignUpNumber +
                ", communityId=" + communityId +
                ", communityName=" + communityName +
                ", createTime=" + createTime +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
