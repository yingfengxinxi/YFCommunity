package com.wr.system.business.domain.screen;

import lombok.Data;

/**
 * @Author: GuanYu
 * @Date: 2021-09-26 17:53:52
 * @Desc: 小区工单
 */
@Data
public class OperPatrolOrder {

    private static final long serialVersionUID = -8949534726788014138L;

    /**工单名称*/
    private String orderName;
    /**工单分类*/
    private String patrolType;
    /** 巡检状态*/
    private String executeStatus;
    /** 执行人*/
    private String staffName;
}
