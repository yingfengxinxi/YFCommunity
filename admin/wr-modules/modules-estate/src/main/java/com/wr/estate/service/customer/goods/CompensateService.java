package com.wr.estate.service.customer.goods;

import com.wr.remote.estate.customer.goods.vo.CompensateVo;
import com.wr.remote.estate.customer.goods.vo.GoodsVo;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-13 11:45:19
 * @Desc:
 */
public interface CompensateService {

    /**
     * 赔偿列表
     * @param compensateVo
     * @return
     */
    List<CompensateVo> selectCompensateList(CompensateVo compensateVo);

    /**
     * 物品赔偿
     *
     * @param goodsVo
     * @return
     */
    int insertCompensate(GoodsVo goodsVo);
}
