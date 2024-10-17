package com.wr.remote.work.nightPatrol.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-31 13:27:40
 * @Desc:
 */
@Data
@ToString
public class NightPatrolOrderQueryVO {
    private static final long serialVersionUID = 1L;

    /**
     * 社区id
     */
    private Long communityId;

    /**
     * 巡查类型
     */
    private Long patrolTypeId;

    /**
     * 巡查类型模糊搜索
     */
    private String patrolType;

    /**
     * 巡更路线
     */
    private Long routeId;

    /**
     * 分配方式
     */
    private String allotWay;

    /**
     * 工单来源
     */
    private String dataSource;

    /**
     * 负责人名称
     */
    private String staffName;

    /**
     * 工单状态
     */
    private Integer executeStatus;
}
