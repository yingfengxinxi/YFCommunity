package com.wr.api.owner.entity.home.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022-11-16 11:41:13
 * @Desc: 活动管理表
 */
@Data
public class ApiActivity extends BaseEntity {
    private static final long serialVersionUID = -3077852847552258891L;

    /**
     * 主键
     */
    private Long activityId;
    /**
     * 楼栋id
     */
    private Long buildingId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 类型id，表bus_act_type
     */
    private Long typeId;

    /**
     * 活动名称
     */
    private String activityName;

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
     * 活动地点
     */
    private String address;

    /**
     * 活动范围：0=全体业主，1=选择业主
     */
    private String act_rang;

    /**
     * 活动费用
     */
    private BigDecimal actAmount;

    /**
     * 图片
     */
    private String actUrl;

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
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;


    /**
     * 志愿者活动id
     */
    private Long voluntaryId;

    /**
     * 志愿者活动名称
     */
    private String actTitle;

    /**
     * 志愿者活动封面
     */
    private String coverUrl;
    /**
     * 志愿者活动地址
     */
    private String communityName;

    /**
     * 志愿者活动内容
     */
    private String content;

    /**
     * 志愿者活动需要人数
     */
    private Integer needNum;


    /**
     * 当前表的状态， 1：bus_activity  2：bus_voluntary
     * 1：社区活动   2：志愿者活动
     */
    private String tableStatus;

    /**
     * 志愿者活动， 已经参加的人数统计
     */
    private Integer isJoinNum;


    /**
     * 账户id
     */
    private Long accountId;

    /**
     * '物资数量'
     */
    private Long supplyNum;
    /**
     * '物资名称'
     */
    private String supplyName;

    /**
     * 状态
     */
    private String status;

    /**
     * 楼栋id
     */
    private List<String> buildingIds;
}
