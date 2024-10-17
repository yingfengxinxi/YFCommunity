package com.wr.estate.service.customer.goods.impl;

import com.wr.estate.mapper.customer.goods.CompensateMapper;
import com.wr.estate.mapper.customer.goods.GoodsMapper;
import com.wr.estate.service.customer.goods.CompensateService;
import com.wr.remote.estate.customer.goods.vo.CompensateVo;
import com.wr.remote.estate.customer.goods.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-13 11:46:03
 * @Desc:
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class CompensateServiceImpl implements CompensateService {

    @Autowired
    private CompensateMapper compensateMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 赔偿列表
     *
     * @param compensateVo
     * @return
     */
    @Override
    public List<CompensateVo> selectCompensateList(CompensateVo compensateVo) {
        return compensateMapper.selectCompensateList(compensateVo);
    }

    /**
     * 物品赔偿
     *
     * @param goodsVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public int insertCompensate(GoodsVo goodsVo) {
        GoodsVo vo = new GoodsVo();
        vo.setGoodsId(goodsVo.getGoodsId());
        vo.setBorrowStatus("0");
        goodsMapper.updateGoods(vo);
        return compensateMapper.insertCompensate(goodsVo);
    }

}
