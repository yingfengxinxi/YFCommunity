package com.wr.system.business.mapper;

import com.wr.system.business.domain.*;

import java.util.List;

/**
 * 参数配置 数据层
 *
 * @author wr
 */
public interface HomeMapper {

    /**
     * 物业总数
     *
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/30 14:29
     */
    Integer countEstate();

    /**
     * 员工总数
     *
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/30 14:30
     */
    Integer countStaff();

    /**
     * 外来人员总数
     *
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/30 14:30
     */
    Integer countForeignStaff();

    /**
     * 居家维修工单评分统计
     *
     * @param communityId 小区id
     * @return com.wr.system.business.domain.HomeScoreVO
     * @author yx
     * @date 2022/11/30 15:28
     */
    HomeScoreVO homeOrderScoreStatistics(Long communityId);

    /**
     * 园区报事工单评分统计
     *
     * @param communityId 小区id
     * @return com.wr.system.business.domain.HomeScoreVO
     * @author yx
     * @date 2022/11/30 15:29
     */
    HomeScoreVO parkOrderScoreStatistics(Long communityId);

    /**
     * 灾害报事工单评分统计
     *
     * @param communityId 小区id
     * @return com.wr.system.business.domain.HomeScoreVO
     * @author yx
     * @date 2022/11/30 15:29
     */
    HomeScoreVO disasterOrderScoreStatistics(Long communityId);

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
     * 租房合同统计
     *
     * @return com.wr.system.business.domain.HomeContractStatistics
     * @author yx
     * @date 2022/11/30 17:11
     */
    HomeMonthStatisticsVO tenancyStatistics();

    /**
     * 采购合同统计
     *
     * @return com.wr.system.business.domain.HomeContractStatistics
     * @author yx
     * @date 2022/11/30 17:11
     */
    HomeMonthStatisticsVO purchaseStatistics();

    /**
     * 服务合同统计
     *
     * @return com.wr.system.business.domain.HomeContractStatistics
     * @author yx
     * @date 2022/11/30 17:11
     */
    HomeMonthStatisticsVO serveStatistics();

    /**
     * 工单排名
     *
     * @return com.wr.system.business.domain.HomeOrderRankVO
     * @author yx
     * @date 2022/11/30 18:12
     */
    List<HomeOrderRankVO> orderRank();
}
