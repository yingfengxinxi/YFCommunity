package com.wr.api.owner.service.estateapi;

import com.wr.remote.estate.customer.lost.ThingLostVo;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-27 10:58:11
 * @Desc: 寻物启事表服务接口
 */
public interface ThingLostService {

    /**
     * 查询ThingLost列表
     *
     * @param thingLostVo 筛选条件
     * @return 查询结果
     */
    List<ThingLostVo> selectThingLostList(ThingLostVo thingLostVo);

    /**
     * @param lostId
     * @return
     */
    ThingLostVo get(Long lostId);

    /**
     * 删除
     *
     * @param lostId
     * @return
     */
    int deleteById(Long lostId);

    /**
     * @param thingLostVo
     * @return
     */
    void addThingLost(ThingLostVo thingLostVo);

    /**
     * @param thingLostVo
     */
    void updateThingLost(ThingLostVo thingLostVo);
}
