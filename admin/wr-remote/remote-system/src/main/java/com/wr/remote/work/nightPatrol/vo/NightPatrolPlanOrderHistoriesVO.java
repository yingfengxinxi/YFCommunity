package com.wr.remote.work.nightPatrol.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-10-31 13:27:40
 * @Desc:
 */
@Data
public class NightPatrolPlanOrderHistoriesVO {
    private static final long serialVersionUID = 1L;

    /**
     * 工单编号
     */
    private String watchmanNo;

    /**
     * 工单名称
     */
    private String watchmanName;

    /**
     * 巡更类型名称
     */
    private String tourName;

    /**
     * 巡更路线
     */
    private String routeName;

    /**
     * 巡更点数量
     */
    private String pointNum;

    /**
     * 分配方式
     */
    private String allotWay;

    /**
     * 主要负责人
     */
    private String staffName;

    /**
     * 生成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
