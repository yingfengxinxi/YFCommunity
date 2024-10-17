package com.wr.api.owner.entity.home.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author: WuKong
 * @Date: 2022-11-15 11:49:04
 * @Desc: 投诉表扬表
 */
@Data
public class Complaint extends BaseEntity {
    private static final long serialVersionUID = 5626021023589066656L;
    /**
     * 主键
     */
    private Long complaintId;

    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;

    /**
     * 发布人账号id，表xccj_community.bus_account
     */
    private Long accountId;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型：0=业主表扬，1=业主投诉
     */
    private String complaintType;

    /**
     * 工单类型：0=无，1=居家维修，2=园区报事，3=灾害报事
     */
    private String orderType;

    /**
     * 工单id，对应order_type的表
     */
    private Long orderId;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 图片路径集合
     */
    private String[] complaintUrls;

    /**
     * 图片路径
     */
    private String complaintUrl;

    /**
     * 是否公式：0=未公示，1=已公示
     */
    private String publicStatus;

    /**
     * 公示时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publicTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;
    /**
     * 小区名称
     */
    private String communityName;
    /**
     * 小区别名
     */
    private String communityAlias;
    /**
     * 账号（手机号）
     */
    private String loginName;
    /**
     * 用户名
     */
    private String accountName;
    /**
     * 用户名称
     */
    private String accountNick;
    /**
     * 关联工单名称
     */
    private String orderName;
}
