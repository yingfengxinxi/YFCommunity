package com.wr.api.owner.entity.home.location;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 投诉表扬表
 * @Author: lvzy
 * @Date: 2022-12-02 10:46:16
 * @Desc:投诉表扬表
 */
@Data
public class ApiComplaint implements Serializable {

    /**
     * 主键
     */
    private Long complaintId;

    /**
     * 小区id，表bus_community
     */
    private Long communityId;

    /**
     * 发布人账号id，表bus_account
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
     * 图片路径
     */
    private String complaintUrl;

    /**
     * 内容
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否公式：0=未公示，1=已公示
     */
    private String publicStatus;

    /**
     * 公示时间
     */
    private Date publicTime;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;

    /**
     * 多张图片
     */
    private String[] imageUrl;

    /**
     * 账户名称
     */
    private String accountName;


    private static final long serialVersionUID = 1L;
}