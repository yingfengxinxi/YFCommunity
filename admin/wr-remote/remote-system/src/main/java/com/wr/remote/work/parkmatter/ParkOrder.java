package com.wr.remote.work.parkmatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 园区报事工单表(BusParkOrder)实体类
 *
 * @author lvzy
 * @since 2022-11-17 13:36:23
 */
@Data
public class ParkOrder implements Serializable {
    private static final long serialVersionUID = 256826748453257311L;
    /**
     * 主键
     */
    private Long orderId;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 园区报事申请id，表xccj_work.bus_park_audit
     */
    private Long auditId;
    /**
     * 工单编号
     */
    private String orderNo;
    /**
     * 工单名称
     */
    private String orderName;
    /**
     * 计划开始时间
     */
    private String planStart;
    /**
     * 计划结束时间
     */
    private String planEnd;
    /**
     * 计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date planEnds;
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
    private Date executeTime;
    /**
     * 完成时间
     */
    private Date finishTime;
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
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 删除标记：0=未删除，1=已删除
     */
    private String delFlag;


}

