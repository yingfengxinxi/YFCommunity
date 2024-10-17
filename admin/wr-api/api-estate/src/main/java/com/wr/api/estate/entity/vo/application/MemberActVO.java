package com.wr.api.estate.entity.vo.application;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 党员活动(BusMemberAct)VO类
 *
 * @author lvzy
 * @since 2022-11-14 17:01:05
 */
@Data
public class MemberActVO {

    /**
     * 主键
     */
    private Long activityId;
    /**
     * 活动图片
     */
    private String imgUrl;
    /**
     * 活动主题
     */
    private String titleName;
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
     * 活动详情
     */
    private String content;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 报名人数
     */
    private Integer joinNum;
    //活动状态0=未开始1=进行中2=已结束
    private String state;
    //浏览次数
    private Integer viewNum;
}

