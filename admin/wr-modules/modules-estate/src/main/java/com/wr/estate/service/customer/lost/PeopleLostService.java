package com.wr.estate.service.customer.lost;

import com.wr.remote.estate.customer.lost.PeopleLostVo;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-27 10:56:18
 * @Desc: 寻人启事服务接口
 */
public interface PeopleLostService {

    /**
     * 查询PeopleLost列表
     *
     * @param peopleLost 筛选条件
     * @return 查询结果
     */
    List<PeopleLostVo> selectPeopleLostList(PeopleLostVo peopleLost);

    /**
     *
     * @param lostId
     * @return
     */
    PeopleLostVo get(Long lostId);

    /**
     * 删除
     *
     * @param lostId
     * @return
     */
    int deleteById(Long lostId);

    /**
     * @param peopleLostVo
     * @return
     */
    void addPeoLost(PeopleLostVo peopleLostVo);

    void updatePeoLost(PeopleLostVo peopleLostVo);
}
