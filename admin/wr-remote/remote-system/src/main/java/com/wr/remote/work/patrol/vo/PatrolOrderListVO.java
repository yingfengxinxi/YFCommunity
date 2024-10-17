package com.wr.remote.work.patrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-31 13:27:40
 * @Desc:
 */
@Data
public class PatrolOrderListVO {
    private static final long serialVersionUID = 1L;

    /**
     * 工单id
     */
    private Long patrolId;

    /**
     * 工单编号
     */
    private String patrolNo;

    /**
     * 工单名称
     */
    private String patrolName;

    /**
     * 巡查类型名称
     */
    private String tourName;

    /**
     * 工单来源
     */
    private String dataSource;

    /**
     * 分配方式
     */
    private String allotWay;

    /**
     * 主要负责人
     */
    private String staffName;

    /**
     * 巡查点名称
     */
    private String pointName;

    /**
     * 分配状态
     */
    private String allotStatus;

    /**
     * 接单状态
     */
    private String acceptStatus;

    /**
     * 执行状态
     */
    private String executeStatus;

    /**
     * 生成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
