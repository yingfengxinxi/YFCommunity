package com.wr.system.business.service.impl;

import com.wr.system.business.domain.*;
import com.wr.system.business.mapper.HomeMapper;
import com.wr.system.business.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页 服务层实现
 *
 * @author wr
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeMapper homeMapper;

    /**
     * 物业总数
     *
     * @return java.lang.Integer
     * @author yx
     * @date 2022/11/30 14:29
     */
    @Override
    public HomeCountVO count() {
        Integer estateCount = homeMapper.countEstate();
        Integer staffCount = homeMapper.countStaff();
        Integer foreignStaffCount = homeMapper.countForeignStaff();
        HomeCountVO homeCount = new HomeCountVO();
        homeCount.setEstateCount(estateCount);
        homeCount.setStaffCount(staffCount);
        homeCount.setForeignStaffCount(foreignStaffCount);
        return homeCount;
    }

    /**
     * 工单评分统计
     *
     * @param communityId 小区id
     * @return com.wr.system.business.domain.OrderScoreStatisticsVO
     * @author yx
     * @date 2022/11/30 15:30
     */
    @Override
    public HomeOrderScoreStatisticsVO orderScoreStatistics(Long communityId) {
        HomeScoreVO homeScore = homeMapper.homeOrderScoreStatistics(communityId);
        HomeScoreVO parkScore = homeMapper.parkOrderScoreStatistics(communityId);
        HomeScoreVO disasterScore = homeMapper.disasterOrderScoreStatistics(communityId);
        HomeOrderScoreStatisticsVO orderScoreStatistics = new HomeOrderScoreStatisticsVO();
        orderScoreStatistics.setHomeOrder(homeScore);
        orderScoreStatistics.setParkOrder(parkScore);
        orderScoreStatistics.setDisasterOrder(disasterScore);
        return orderScoreStatistics;
    }

    /**
     * 查询小区列表
     *
     * @return java.util.List<com.wr.system.business.domain.HomeCommunityVO>
     * @author yx
     * @date 2022/11/30 16:04
     */
    @Override
    public List<HomeCommunityVO> listCommunities() {
        return homeMapper.listCommunities();
    }

    /**
     * 物业统计
     *
     * @return com.wr.system.business.domain.HomeEstateStatisticsVO
     * @author yx
     * @date 2022/11/30 16:34
     */
    @Override
    public HomeEstateStatisticsVO estateStatistics() {
        return homeMapper.estateStatistics();
    }

    /**
     * 合同统计
     *
     * @return com.wr.system.business.domain.HomeContractStatistics
     * @author yx
     * @date 2022/11/30 17:15
     */
    @Override
    public HomeContractStatisticsVO contractStatistics() {
        HomeMonthStatisticsVO tenancyStatistics = homeMapper.tenancyStatistics();
        HomeMonthStatisticsVO purchaseStatistics = homeMapper.purchaseStatistics();
        HomeMonthStatisticsVO serveStatistics = homeMapper.serveStatistics();
        HomeContractStatisticsVO homeContractStatistics = new HomeContractStatisticsVO();
        homeContractStatistics.setTenancy(tenancyStatistics);
        homeContractStatistics.setPurchase(purchaseStatistics);
        homeContractStatistics.setServer(serveStatistics);
        return homeContractStatistics;
    }

    /**
     * 工单排名
     *
     * @return com.wr.system.business.domain.HomeOrderRankVO
     * @author yx
     * @date 2022/11/30 18:12
     */
    @Override
    public List<HomeOrderRankVO> orderRank() {
        return homeMapper.orderRank();
    }
}
