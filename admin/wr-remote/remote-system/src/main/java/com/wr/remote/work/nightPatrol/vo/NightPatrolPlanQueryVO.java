package com.wr.remote.work.nightPatrol.vo;

import lombok.Data;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-31 13:27:40
 * @Desc:
 */
@Data
public class NightPatrolPlanQueryVO {
    private static final long serialVersionUID = 1L;

    /**
     * 小区id
     */
    private Long communityId;

    /**
     * 主键
     */
    private Long planId;

    /**
     * 巡查类型id
     */
    private String patrolTypeId;

    /**
     * 巡查类型模糊搜索
     */
    private String patrolType;

    /**
     * 巡查路线id
     */
    private Long routeId;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 是否生成工单：1=否，0=是
     */
    private String planOrder;

}
