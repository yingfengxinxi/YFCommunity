package com.wr.estate.service.work.nightPatrol;

import com.wr.remote.work.nightPatrol.WatchmanPlan;
import com.wr.remote.work.nightPatrol.vo.NightPatrolPlanDetailVO;
import com.wr.remote.work.nightPatrol.vo.NightPatrolPlanListVO;
import com.wr.remote.work.nightPatrol.vo.NightPatrolPlanQueryVO;

import java.util.List;

/**
 * 巡更计划
 *
 * @Author: 奔波儿灞
 * @Date: 2022-10-27 14:19:53
 * @Desc:
 */
public interface NightPatrolPlanService {

    /**
     * 查询巡更计划
     *
     * @param planId 巡更计划主键
     * @return 巡更计划
     */
    WatchmanPlan selectWatchmanPlanByPlanId(Long planId);

    /**
     * 查询巡更计划列表
     *
     * @param watchmanPlan 巡更计划
     * @return 巡更计划集合
     */
    List<WatchmanPlan> selectWatchmanPlanList(WatchmanPlan watchmanPlan);

    /**
     * 新增巡更计划
     *
     * @param watchmanPlan 巡更计划
     * @return 结果
     */
    int insertWatchmanPlan(WatchmanPlan watchmanPlan);

    /**
     * 修改巡更计划
     *
     * @param watchmanPlan 巡更计划
     * @return 结果
     */
    int updateWatchmanPlan(WatchmanPlan watchmanPlan);

    /**
     * 批量删除巡更计划
     *
     * @param planIds 需要删除的巡更计划主键集合
     * @return 结果
     */
    int deleteWatchmanPlanByPlanIds(Long[] planIds);

    /**
     * 删除巡更计划信息
     *
     * @param planId 巡更计划主键
     * @return 结果
     */
    int deleteWatchmanPlanByPlanId(Long planId);

    /**
     * 巡更计划页面分页查询列表
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.remote.work.nightPatrol.vo.NightPatrolPlanListVO>
     * @author yx
     * @date 2022/11/1 14:59
     */
    List<NightPatrolPlanListVO> listPage(NightPatrolPlanQueryVO query);

    /**
     * 修改巡更计划生成工单状态
     *
     * @param watchmanPlan 修改参数
     * @author yx
     * @date 2022/11/1 16:45
     */
    void updateNightPatrolPlanOrder(WatchmanPlan watchmanPlan);

    /**
     * 根据计划id查询计划详情
     *
     * @param planId 计划id
     * @return com.wr.remote.work.nightPatrol.vo.NightPatrolPlanDetailVO
     * @author yx
     * @date 2022/11/2 13:43
     */
    NightPatrolPlanDetailVO getDetailById(Long planId);

    /**
     * 定时生成工单
     *
     * @author yx
     * @date 2022/12/8 11:08
     */
    void scheduledCreateOrder();

}
