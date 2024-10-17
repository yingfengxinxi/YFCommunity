package com.wr.system.business.controller;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.system.business.domain.*;
import com.wr.system.business.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页 操作处理
 *
 * @author wr
 */
@RestController
@RequestMapping("/home")
public class HomeController extends BaseController {

    @Autowired
    private HomeService homeService;

    /**
     * 总数
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/30 14:43
     */
    @GetMapping("/count")
    public AjaxResult count() {
        HomeCountVO count = homeService.count();
        return AjaxResult.success(count);
    }

    /**
     * 工单评分统计
     *
     * @param communityId 小区id
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/30 15:33
     */
    @GetMapping("/scoreStatistics/{communityId}")
    public AjaxResult scoreStatistics(@PathVariable("communityId") Long communityId) {
        HomeOrderScoreStatisticsVO orderScoreStatistics = homeService.orderScoreStatistics(communityId);
        return AjaxResult.success(orderScoreStatistics);
    }

    /**
     * 小区列表
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/30 16:06
     */
    @GetMapping("/listCommunities")
    public AjaxResult listCommunities() {
        List<HomeCommunityVO> communityList = homeService.listCommunities();
        return AjaxResult.success(communityList);
    }

    /**
     * 物业统计
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/30 16:35
     */
    @GetMapping("/estateStatistics")
    public AjaxResult estateStatistics() {
        HomeEstateStatisticsVO estateStatistics = homeService.estateStatistics();
        return AjaxResult.success(estateStatistics);
    }

    /**
     * 合同统计
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/30 17:17
     */
    @GetMapping("/contractStatistics")
    public AjaxResult contractStatistics() {
        HomeContractStatisticsVO homeContractStatistics = homeService.contractStatistics();
        return AjaxResult.success(homeContractStatistics);
    }

    /**
     * 物业工单排名
     *
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2022/11/30 18:14
     */
    @GetMapping("/orderRank")
    public AjaxResult orderRank() {
        List<HomeOrderRankVO> homeOrderRank = homeService.orderRank();
        return AjaxResult.success(homeOrderRank);
    }

}
