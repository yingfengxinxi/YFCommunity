package com.wr.estate.service.customer.goods;

import com.wr.remote.estate.customer.goods.vo.GoodsOutVo;

import java.util.List;

/**
* @Author: Guan Yu
* @Date: 2022-10-12 16:27:24
* @Desc: 物品出门
*/
public interface GoodsOutService {

    /**
     * 物品出门列表
     * @param goodsOutVo
     * @return
     */
    List<GoodsOutVo> selectGoodsOutList(GoodsOutVo goodsOutVo);


    /**
     * 详情
     *
     * @param outId
     * @return
     */
    GoodsOutVo getInfo(Long outId);

    /**
     * 出门
     * @param goodsOutVo
     * @return
     */
    int updateOutStatus(GoodsOutVo goodsOutVo);
}

