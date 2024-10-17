package com.wr.estate.mapper.work.patrol;


import com.wr.remote.work.patrol.PatrolPlan;
import com.wr.remote.work.patrol.vo.PatrolPlanDetailVO;
import com.wr.remote.work.patrol.vo.PatrolPlanListVO;
import com.wr.remote.work.patrol.vo.PatrolPlanQueryVO;

import java.util.List;

/**
 * 巡查计划Mapper接口
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
public interface PatrolPlanMapper {
    /**
     * 查询巡查计划
     *
     * @param planId 巡查计划主键
     * @return 巡查计划
     */
    PatrolPlan selectPatrolPlanByPlanId(Long planId);

    /**
     * 查询巡查计划列表
     *
     * @param patrolPlan 巡查计划
     * @return 巡查计划集合
     */
    List<PatrolPlan> selectPatrolPlanList(PatrolPlan patrolPlan);

    /**
     * 新增巡查计划
     *
     * @param patrolPlan 巡查计划
     * @return 结果
     */
    int insertPatrolPlan(PatrolPlan patrolPlan);

    /**
     * 修改巡查计划
     *
     * @param patrolPlan 巡查计划
     * @return 结果
     */
    int updatePatrolPlan(PatrolPlan patrolPlan);

    /**
     * 删除巡查计划
     *
     * @param planId 巡查计划主键
     * @return 结果
     */
    int deletePatrolPlanByPlanId(Long planId);

    /**
     * 批量删除巡查计划
     *
     * @param planIds 需要删除的数据主键集合
     * @return 结果
     */
    int deletePatrolPlanByPlanIds(Long[] planIds);

    /**
     * 修改巡查计划生成工单状态
     *
     * @param patrolPlan 工单修改信息
     * @return int
     * @author yx
     * @date 2022/10/31 16:30
     */
    void updatePatrolPlanOrder(PatrolPlan patrolPlan);

    /**
     * 巡查计划分页列表
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.remote.work.patrol.vo.PatrolPlanListVO>
     * @author yx
     * @date 2022/10/31 13:42
     */
    List<PatrolPlanListVO> listPage(PatrolPlanQueryVO query);

    /**
     * 查询计划详情
     *
     * @param planId 计划id
     * @return com.wr.remote.work.patrol.vo.PatrolPlanDetailVO
     * @author yx
     * @date 2022/11/1 10:30
     */
    PatrolPlanDetailVO getDetailById(Long planId);

    /**
     * 根据名字校验计划名是否存在
     *
     * @param plan
     * @author yx
     * @date 2022/12/8 9:13
     */
    int countByName(PatrolPlan plan);

    /**
     * 根据工单定时开启查询所有工单
     *
     * @param planOrder 开启状态
     * @return java.util.List<com.wr.remote.work.patrol.PatrolPlan>
     * @author yx
     * @date 2022/12/8 11:14
     */
    List<PatrolPlan> selectPlanByPlanOrder(Integer planOrder);
}
