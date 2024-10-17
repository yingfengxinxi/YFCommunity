package com.wr.remote.work.patrol.vo;

import lombok.Data;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-31 13:27:40
 * @Desc: 巡查计划页面查询参数
 */
@Data
public class PatrolPlanQueryVO {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long communityId;

    /**
     * 巡查类型id
     */
    private Long patrolTypeId;

    /**
     * 巡查类型模糊搜索
     */
    private String patrolType;

    /**
     * 是否生成工单：1=否，0=是
     */
    private String planOrder;

    /**
     * 计划名称
     */
    private String planName;
}
