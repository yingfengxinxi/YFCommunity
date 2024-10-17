package com.wr.estate.mapper.customer.lost;

import com.wr.remote.estate.customer.lost.PeopleLostVo;
import com.wr.remote.estate.customer.lost.ThingLostVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-27 10:58:11
 * @Desc: 寻物启事表数据库访问层
 */
public interface ThingLostMapper {

    /**
     * 查询寻人启事列表
     *
     * @param thingLostVo 查询条件
     * @return 对象列表
     */
    List<ThingLostVo> selectThingLostList(ThingLostVo thingLostVo);

    /**
     * @param lostId
     * @return
     */
    ThingLostVo get(@Param("lostId") Long lostId);

    /**
     * @param thingLostVo
     */
    void addThingLost(ThingLostVo thingLostVo);

    /**
     * 删除
     *
     * @param lostId
     * @return
     */
    int deleteById(Long lostId);

    void updateThingLost(ThingLostVo thingLostVo);

}

