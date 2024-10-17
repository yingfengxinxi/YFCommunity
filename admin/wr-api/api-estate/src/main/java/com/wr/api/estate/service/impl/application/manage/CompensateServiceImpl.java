package com.wr.api.estate.service.impl.application.manage;

import com.wr.api.estate.entity.vo.application.manage.CompensateVo;
import com.wr.api.estate.entity.vo.application.manage.GoodsVo;
import com.wr.api.estate.mapper.application.manage.CompensateMapper;
import com.wr.api.estate.mapper.application.manage.GoodsMapper;
import com.wr.api.estate.service.application.manage.CompensateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: lvzy
 * @Date: 2022-11-17 16:33:34
 * @Desc:
 */
@Service
public class CompensateServiceImpl implements CompensateService {
    @Autowired
    private CompensateMapper compensateMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 添加赔偿信息
     *
     * @param compensateVo
     * @return
     */
    @Override
    @Transactional
    public int insertCompensate(CompensateVo compensateVo) {
        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setGoodsId(compensateVo.getGoodsId());
        goodsMapper.updateBorrowStatus(compensateVo.getBorrowId());
        goodsMapper.removeGoods(compensateVo.getGoodsId());
        return compensateMapper.insertCompensate(compensateVo);
    }
}
