package com.wr.estate.service.work.patrol;


import com.wr.remote.work.Tour;

import java.util.List;

/**
 * 巡X类型Service接口
 *
 * @author 奔波儿灞
 * @date 2022-10-28
 */
public interface TourService {
    /**
     * 查询巡X类型
     *
     * @param tourId 巡X类型主键
     * @return 巡X类型
     */
    Tour selectTourByTourId(Long tourId);

    /**
     * 查询巡X类型列表
     *
     * @param tour 巡X类型
     * @return 巡X类型集合
     */
    List<Tour> selectTourList(Tour tour);

    /**
     * 新增巡X类型
     *
     * @param tour 巡X类型
     * @return 结果
     */
    int insertTour(Tour tour);

    /**
     * 修改巡X类型
     *
     * @param tour 巡X类型
     * @return 结果
     */
    int updateTour(Tour tour);

    /**
     * 批量删除巡X类型
     *
     * @param tourIds 需要删除的巡X类型主键集合
     * @return 结果
     */
    int deleteTourByTourIds(Long[] tourIds);

    /**
     * 删除巡X类型信息
     *
     * @param tourId 巡X类型主键
     * @return 结果
     */
    int deleteTourByTourId(Long tourId);

    /**
     * 检查类型名称是否唯一
     * @param tour
     * @return
     */
    String checkName(Tour tour);
}
