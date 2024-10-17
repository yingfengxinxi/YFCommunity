package com.wr.estate.mapper.work.nightPatrol;


import com.wr.remote.work.nightPatrol.Watchman;
import com.wr.remote.work.nightPatrol.vo.*;

import java.util.List;

/**
 * 巡更工单Mapper接口
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
public interface WatchmanMapper {
    /**
     * 查询巡更工单
     *
     * @param watchmanId 巡更工单主键
     * @return 巡更工单
     */
    Watchman selectWatchmanByWatchmanId(Long watchmanId);

    /**
     * 查询巡更工单列表
     *
     * @param watchman 巡更工单
     * @return 巡更工单集合
     */
    List<Watchman> selectWatchmanList(Watchman watchman);

    /**
     * 新增巡更工单
     *
     * @param watchman 巡更工单
     * @return 结果
     */
    int insertWatchman(Watchman watchman);

    /**
     * 修改巡更工单
     *
     * @param watchman 巡更工单
     * @return 结果
     */
    int updateWatchman(Watchman watchman);

    /**
     * 删除巡更工单
     *
     * @param watchmanId 巡更工单主键
     * @return 结果
     */
    int deleteWatchmanByWatchmanId(Long watchmanId);

    /**
     * 批量删除巡更工单
     *
     * @param watchmanIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWatchmanByWatchmanIds(Long[] watchmanIds);

    /**
     * 根据计划id查询历史生成工单
     *
     * @param planId 计划id
     * @return java.util.List<com.wr.remote.work.nightPatrol.vo.NightPatrolPlanOrderHistoriesVO>
     * @author yx
     * @date 2022/11/2 13:50
     */
    List<NightPatrolPlanOrderHistoriesVO> listNightPatrolPlanOrderHistories(Long planId);

    /**
     * 巡更工单分页列表
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.remote.work.nightPatrol.vo.NightPatrolOrderListVO>
     * @author yx
     * @date 2022/11/3 17:39
     */
    List<NightPatrolOrderListVO> listPage(NightPatrolOrderQueryVO query);

    /**
     * 根据id查询详情
     *
     * @param id 工单id
     * @return com.wr.remote.work.nightPatrol.vo.NightPatrolOrderDetailVO
     * @author yx
     * @date 2022/11/4 11:33
     */
    NightPatrolOrderDetailVO getDetail(Long id);

    /**
     * 分配负责人
     *
     * @param watchman 负责人信息
     * @return int
     * @author yx
     * @date 2022/11/5 13:37
     */
    int updateStaff(Watchman watchman);

    /**
     * 查询定时生成的工单
     *
     * @return java.util.List<com.wr.remote.work.nightPatrol.Watchman>
     * @author yx
     * @date 2022/12/8 13:30
     */
    List<Watchman> selectTimingOrderList();

    /**
     * 批量新增
     *
     * @param watchmanList 批量数据
     * @author yx
     * @date 2022/12/8 13:34
     */
    void insertBatch(List<Watchman> watchmanList);
}
