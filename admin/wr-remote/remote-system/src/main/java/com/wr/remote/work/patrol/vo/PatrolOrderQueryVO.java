package com.wr.remote.work.patrol.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-31 13:27:40
 * @Desc:
 */
@Data
@ToString
public class PatrolOrderQueryVO {
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
     * 巡点
     */
    private Long pointId;

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
     * 执行状态
     */
    private String executeStatus;
}
