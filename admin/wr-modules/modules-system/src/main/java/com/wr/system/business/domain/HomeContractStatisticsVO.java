package com.wr.system.business.domain;

import lombok.Data;

/**
 * @Author: 奔波儿灞
 * @Date: 2022-11-30 17:08:44
 * @Desc: 月份统计
 */
@Data
public class HomeContractStatisticsVO {

    /**
     * 租房统计
     */
    private HomeMonthStatisticsVO tenancy;

    /**
     * 采购统计
     */
    private HomeMonthStatisticsVO purchase;

    /**
     * 服务统计
     */
    private HomeMonthStatisticsVO server;
}
