package com.wr.estate.mapper.work.nightPatrol;


import com.wr.remote.work.nightPatrol.WatchmanPlan;
import com.wr.remote.work.nightPatrol.vo.NightPatrolPlanDetailVO;
import com.wr.remote.work.nightPatrol.vo.NightPatrolPlanListVO;
import com.wr.remote.work.nightPatrol.vo.NightPatrolPlanQueryVO;

import java.util.List;

/**
 * 巡更计划Mapper接口
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
public interface WatchmanPlanMapper {
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
     * 删除巡更计划
     *
     * @param planId 巡更计划主键
     * @return 结果
     */
    int deleteWatchmanPlanByPlanId(Long planId);

    /**
     * 批量删除巡更计划
     *
     * @param planIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWatchmanPlanByPlanIds(Long[] planIds);

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
     * 根据名字校验计划名是否存在
     *
     * @param watchmanPlan 计划数据
     * @return int
     * @author yx
     * @date 2022/12/8 9:15
     */
    int countByName(WatchmanPlan watchmanPlan);

    /**
     * 查询所有定时开启的工单计划
     * @author yx
     * @date 2022/12/8 13:27
     * @param planOrder 开启状态
     * @return java.util.List<com.wr.remote.work.nightPatrol.WatchmanPlan>
     */
    List<WatchmanPlan> selectPlanByPlanOrder(Integer planOrder);
}
