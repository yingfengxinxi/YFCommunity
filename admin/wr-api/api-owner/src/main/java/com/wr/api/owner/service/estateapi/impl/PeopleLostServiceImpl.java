package com.wr.api.owner.service.estateapi.impl;

import com.wr.api.owner.mapper.estateapi.PeopleLostMapper;
import com.wr.api.owner.service.estateapi.PeopleLostService;
import com.wr.remote.estate.customer.lost.PeopleLostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-27 10:56:18
 * @Desc: 寻人启事服务实现类
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PeopleLostServiceImpl implements PeopleLostService {

    @Autowired
    private PeopleLostMapper peopleLostMapper;


    /**
     * 查询PeopleLost列表
     *
     * @param peopleLost 筛选条件
     * @return 查询结果
     */
    @Override
    public List<PeopleLostVo> selectPeopleLostList(PeopleLostVo peopleLost) {
        return peopleLostMapper.selectPeopleLostList(peopleLost);
    }

    @Override
    public PeopleLostVo get(Long lostId) {
        return peopleLostMapper.get(lostId);
    }

    /**
     * 删除
     *
     * @param lostId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public int deleteById(Long lostId) {
        return peopleLostMapper.deleteById(lostId);
    }

    @Override
    @Transactional(readOnly = false)
    public void addPeoLost(PeopleLostVo peopleLostVo) {
        peopleLostMapper.addPeoLost(peopleLostVo);
    }

    @Override
    @Transactional(readOnly = false)
    public void updatePeoLost(PeopleLostVo peopleLostVo) {
        peopleLostMapper.updatePeoLost(peopleLostVo);
    }
}
