package com.wr.api.estate.entity.vo.application;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动管理表(BusActivity)VO类
 *
 * @author lvzy
 * @since 2022-11-14 14:42:18
 */
@Data
public class ActivityVO {
    /**
     * 主键
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String titleName;
    /**
     * 活动类型
     */
    private String typeName;
    //浏览次数
    private int viewNumber;
    /**
     * 报名截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Date closeTime;
    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 活动结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    /**
     * 活动地点
     */
    private String address;
    /**
     * 活动费用
     */
    private Double actAmount;
    /**
     * 图片
     */
    private String imgUrl;
    /**
     * 活动说明
     */
    private String actIntro;
    /**
     * 报名人数
     */
    private Integer joinNum;
    /**
     * 负责人
     */
    private String leader;
    /**
     * 负责人联系方式
     */
    private String leaderPhone;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 状态
     */
    private String state;

    /**
     * 活动物质
     */
    private String supply;
}

