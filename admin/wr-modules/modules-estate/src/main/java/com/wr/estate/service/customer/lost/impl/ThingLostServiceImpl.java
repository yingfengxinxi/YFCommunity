package com.wr.estate.service.customer.lost.impl;

import com.wr.estate.mapper.customer.lost.ThingLostMapper;
import com.wr.estate.service.customer.lost.ThingLostService;
import com.wr.remote.estate.customer.lost.PeopleLostVo;
import com.wr.remote.estate.customer.lost.ThingLostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-27 10:58:11
 * @Desc: 寻物启事表服务实现类
 */
@Service
public class ThingLostServiceImpl implements ThingLostService {

    @Autowired
    private ThingLostMapper thingLostMapper;


    /**
     * 查询ThingLost列表
     *
     * @param thingLost 筛选条件
     * @return 查询结果
     */
    @Override
    public List<ThingLostVo> selectThingLostList(ThingLostVo thingLost) {
        return thingLostMapper.selectThingLostList(thingLost);
    }

    @Override
    public ThingLostVo get(Long lostId) {
        return thingLostMapper.get(lostId);
    }




    @Override
    @Transactional(readOnly = false)
    public void addThingLost(ThingLostVo thingLostVo) {
        thingLostMapper.addThingLost(thingLostVo);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateThingLost(ThingLostVo thingLostVo) {
        thingLostMapper.updateThingLost(thingLostVo);
    }
    /**
     * 删除
     *
     * @param lostId
     * @return
     */
    @Override
    public int deleteById(Long lostId) {
        return thingLostMapper.deleteById(lostId);
    }
}
