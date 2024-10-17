package com.wr.estate.mapper.customer.lost;

import com.wr.remote.estate.customer.lost.PeopleLostVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-27 10:56:17
 * @Desc: 寻人启事数据库访问层
 */
public interface PeopleLostMapper {

    /**
     * 查询寻人启事列表
     *
     * @param peopleLost 查询条件
     * @return 对象列表
     */
    List<PeopleLostVo> selectPeopleLostList(PeopleLostVo peopleLost);

    /**
     *
     * @param lostId
     * @return
     */
    PeopleLostVo get(@Param("lostId") Long lostId);

    /**
     * @param peopleLost
     */
    void addPeoLost(PeopleLostVo peopleLost);

    /**
     * 删除
     *
     * @param lostId
     * @return
     */
    int deleteById(Long lostId);

    void updatePeoLost(PeopleLostVo peopleLostVo);
}

