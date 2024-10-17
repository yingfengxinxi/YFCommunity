package com.wr.remote.work.nightPatrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.work.patrol.vo.AssistStaffListVO;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-03 14:53:57
 * @Desc:
 */
@Data
@ToString
public class NightPatrolOrderDetailVO {

    /**
     * 工单编号
     */
    private String watchmanNo;

    /**
     * 其他工单编号
     */
    private String orderNo;

    /**
     * 工单名称
     */
    private String watchmanName;

    /**
     * 主要负责人
     */
    private String staffName;

    /**
     * 巡更顺序
     */
    private String watchmanType;

    /**
     * 巡查类型
     */
    private String tourName;

    /**
     * 工单来源
     */
    private String dataSource;

    /**
     * 来源工单
     */
    private String otherOrderName;

    /**
     * 巡查点
     */
    private String routeName;

    /**
     * 巡查计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    /**
     * 巡查计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date finishTime;

    /**
     * 异常状态，1异常，0非异常
     */
    private Integer errorStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 完成图片
     */
    private String imageUrl;

    /**
     * 执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
     */
    private Integer executeStatus;

    /**
     * 协助人列表
     */
    private List<AssistStaffListVO> staffList;
}
