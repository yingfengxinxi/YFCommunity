package com.wr.system.business.service;

import com.wr.system.business.domain.*;

import java.util.List;

/**
 * 首页 服务层
 *
 * @author wr
 */
public interface HomeService {
    /**
     * 总数
     *
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/30 14:29
     */
    HomeCountVO count();

    /**
     * 工单评分统计
     *
     * @param communityId 小区id
     * @return com.wr.system.business.domain.OrderScoreStatisticsVO
     * @author yx
     * @date 2022/11/30 15:30
     */
    HomeOrderScoreStatisticsVO orderScoreStatistics(Long communityId);

    /**
     * 查询小区列表
     *
     * @return java.util.List<com.wr.system.business.domain.HomeCommunityVO>
     * @author yx
     * @date 2022/11/30 16:04
     */
    List<HomeCommunityVO> listCommunities();

    /**
     * 物业统计
     *
     * @return com.wr.system.business.domain.HomeEstateStatisticsVO
     * @author yx
     * @date 2022/11/30 16:34
     */
    HomeEstateStatisticsVO estateStatistics();

    /**
     * 合同统计
     *
     * @return com.wr.system.business.domain.HomeContractStatistics
     * @author yx
     * @date 2022/11/30 17:15
     */
    HomeContractStatisticsVO contractStatistics();

    /**
     * 工单排名
     *
     * @return com.wr.system.business.domain.HomeOrderRankVO
     * @author yx
     * @date 2022/11/30 18:12
     */
    List<HomeOrderRankVO> orderRank();

}
