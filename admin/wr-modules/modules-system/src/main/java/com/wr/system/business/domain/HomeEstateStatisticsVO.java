package com.wr.system.business.domain;

import lombok.Data;

/**
 * 首页物业统计
 *
 * @Author: 奔波儿灞
 * @Date: 2022-11-30 14:38:08
 * @Desc:
 */
@Data
public class HomeEstateStatisticsVO {

    /**
     * 存续统计
     */
    private Integer standNum;

    /**
     * 在业统计
     */
    private Integer employedNum;

    /**
     * 吊销统计
     */
    private Integer revokeNum;

    /**
     * 注销统计
     */
    private Integer removeNum;

    /**
     * 迁入统计
     */
    private Integer moveInNum;

    /**
     * 迁出统计
     */
    private Integer moveOutNum;

    /**
     * 停业统计
     */
    private Integer closeNum;

    /**
     * 清算统计
     */
    private Integer clearNum;

}
