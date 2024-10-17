package com.wr.estate.service.work.cleaningMangement;

import com.wr.remote.work.cleaningManagement.CleanPlan;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-27 16:03:18
 * @Desc: 保洁计划接口
 */
public interface CleanPlanService {

    /**
     *  查询保洁计划集合
     * @param cleanPlan
     * @return
     */
    List<CleanPlan> selectCleanPlanList(CleanPlan cleanPlan);


    /**
     * 根据ID查询单个保洁计划
     * @param planId
     * @return
     */
    CleanPlan selectCleanPlanOne(Long planId);

    /**
     * 修改或新增保洁计划
     * @param cleanPlan
     * @return
     */
    int insertAndupdataCleanPlanOneand(CleanPlan cleanPlan);


    /**
     * 删除
     * @param planIds
     * @return
     */
    void delectCleanPlan(Long[] planIds);


    /**
     * 修改状态
     * @param planId
     * @return
     */
    int updataPlanOrderStatue(Long planId);

}
