package com.wr.system.business.domain;

import lombok.Data;

/**
 * 首页评分统计
 *
 * @Author: 奔波儿灞
 * @Date: 2022-11-30 14:38:08
 * @Desc:
 */
@Data
public class HomeOrderScoreStatisticsVO {

    /**
     * 居家维修评分统计
     */
    private HomeScoreVO homeOrder;

    /**
     * 园区报事评分统计
     */
    private HomeScoreVO parkOrder;

    /**
     * 灾害报事评分统计
     */
    private HomeScoreVO disasterOrder;

}
