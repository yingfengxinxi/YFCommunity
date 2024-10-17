package com.wr.system.business.domain.screen;

import lombok.Data;

/**
 * @Author: GuanYu
 * @Date: 2021-09-08 13:46:34
 * @Desc: 工单统计
 */
@Data
public class WorkOrder {

    private static final long serialVersionUID = -5782957337168721196L;

    /** 工单总数*/
    private Integer workOrderSum;

    /** 报事报修*/
    private Integer reportCount;

    /** 装修评定*/
    private Integer decorateCount;

    /** 清洁工单*/
    private Integer cleanCount;

    /** 抄表*/
    private Integer meterCount;

    /** 巡查*/
    private Integer inspectionCount;

    /** 巡更*/
    private Integer watchmanCount;

    /** 巡检*/
    private Integer patrolCount;

}
