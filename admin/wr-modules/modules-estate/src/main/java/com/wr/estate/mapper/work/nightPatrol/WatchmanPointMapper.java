package com.wr.estate.mapper.work.nightPatrol;


import com.wr.remote.work.nightPatrol.Watchman;
import com.wr.remote.work.nightPatrol.WatchmanPoint;

import java.util.List;

/**
 * 巡更工单-巡更点Mapper接口
 *
 * @author 奔波儿灞
 * @date 2022-10-27
 */
public interface WatchmanPointMapper {
    /**
     * 查询巡更工单-巡更点
     *
     * @param watchmanId 巡更工单-巡更点主键
     * @return 巡更工单-巡更点
     */
    WatchmanPoint selectWatchmanPointByWatchmanId(Long watchmanId);

    /**
     * 查询巡更工单-巡更点列表
     *
     * @param watchmanPoint 巡更工单-巡更点
     * @return 巡更工单-巡更点集合
     */
    List<WatchmanPoint> selectWatchmanPointList(WatchmanPoint watchmanPoint);

    /**
     * 新增巡更工单-巡更点
     *
     * @param watchmanPoint 巡更工单-巡更点
     * @return 结果
     */
    int insertWatchmanPoint(WatchmanPoint watchmanPoint);

    /**
     * 修改巡更工单-巡更点
     *
     * @param watchmanPoint 巡更工单-巡更点
     * @return 结果
     */
    int updateWatchmanPoint(WatchmanPoint watchmanPoint);

    /**
     * 批量新增巡更点
     *
     * @param watchman 工单信息
     * @return count
     */
    int insertWatchmanPointBatch(Watchman watchman);

    /**
     * 批量新增
     *
     * @param watchmanIds 批量新增ids
     * @return int
     * @author yx
     * @date 2022/12/8 13:39
     */
    int insertBatchByIds(List<Long> watchmanIds);

    /**
     * 删除巡更工单-巡更点
     *
     * @param watchmanId 巡更工单-巡更点主键
     * @return 结果
     */
    int deleteWatchmanPointByWatchmanId(Long watchmanId);

    /**
     * 批量删除巡更工单-巡更点
     *
     * @param watchmanIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWatchmanPointByWatchmanIds(Long[] watchmanIds);
}
