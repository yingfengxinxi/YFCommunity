package com.wr.system.business.domain;

import lombok.Data;

/**
 * 首页评分
 *
 * @Author: 奔波儿灞
 * @Date: 2022-11-30 14:38:08
 * @Desc:
 */
@Data
public class HomeScoreVO {

    /**
     * 一星评分
     */
    private Integer oneStar;

    /**
     * 二星评分
     */
    private Integer twoStar;

    /**
     * 三星评分
     */
    private Integer threeStar;

    /**
     * 四星评分
     */
    private Integer fourStar;

    /**
     * 五星评分
     */
    private Integer fiveStar;

}
