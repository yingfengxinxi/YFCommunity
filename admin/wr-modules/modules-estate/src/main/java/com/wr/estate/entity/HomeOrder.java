package com.wr.estate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 居家维修工单表(BusHomeOrder)实体类
 *
 * @author SJiang
 * @since 2022-11-05
 */
@Data
public class HomeOrder extends BaseEntity {

    private static final long serialVersionUID = 526519572383631456L;
    /**
     * 主键
     */
    private Long orderId;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 工单编号
     */
    private String orderNo;
    /**
     * 工单名称
     */
    private String orderName;
    /**
     * 业主id，表xccj_community.bus_owner
     */
    private Long ownerId;
    /**
     * 维修类型id，表xccj_work.bus_home_repair
     */
    private Long repairId;
    /**
     * 定金
     */
    private BigDecimal deposit;
    /**
     * 计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planStart;
    /**
     * 计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planEnd;
    /**
     * 地址
     */
    private String address;
    /**
     * 工单分配方式：0=自动分配，1=管理员分配，2=抢单
     */
    private String allotWay;
    /**
     * 负责人id，表xccj_community.bus_staff
     */
    private Long staffId;
    /**
     * 协助人数
     */
    private Integer assistNum;
    /**
     * 转交状态：0=无需转交，1=转交待确认
     */
    private String transferStatus;
    /**
     * 分配状态：0=待分配，1=已分配
     */
    private String allotStatus;
    /**
     * 接单状态：0=待接单，1=已接单
     */
    private String acceptStatus;
    /**
     * 执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
     */
    private String executeStatus;
    /**
     * 评定结果：0=合格，1=违规，2=整改完成
     */
    private String assessResult;
    /**
     * 执行人id，表xccj_community.bus_staff
     */
    private Long executeId;
    /**
     * 执行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date executeTime;
    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
    /**
     * 状态：0=未支付，1=已支付
     */
    private String appointStatus;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 完成图片
     */
    private String imageUrl;
    /**
     * 备注
     */
    private String remark;
    /**
     * 评价状态：0=未评价，1=已评价
     */
    private String appraiseStatus;
    /**
     * 评价星级：0~5
     */
    private Integer appraiseStar;
    /**
     * 评价备注
     */
    private String appraiseRemark;

}

