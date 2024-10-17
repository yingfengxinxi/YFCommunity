package com.wr.estate.service.work.distribution;

import com.wr.remote.work.distribution.Distribution;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/25 10:13
 * @Version 1.5
 */
public interface DistributionService {


    /**
     * 根据小区查询所有
     * @param distribution
     * @return
     */
    List<Distribution> selectList(Distribution distribution);


    /**
     * 新增
     * @param distribution
     * @return
     */
    int insertAllot(Distribution distribution);


    /**
     * 修改
     * @param distribution
     * @return
     */
    int updateAllot(Distribution distribution);


    /**
     * 查询是否存在
     * @param distribution
     * @return
     */
    int selectAllot(Distribution distribution);



    /**
     * 根据id查询
     * @param distribution
     * @return
     */
    Distribution selectAllotById(Long allotId);
}
