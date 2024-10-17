package com.wr.estate.mapper.customer.goods;


import com.wr.remote.estate.customer.goods.vo.CompensateVo;
import com.wr.remote.estate.customer.goods.vo.GoodsVo;

import java.util.List;

/**
* @Author: Guan Yu
* @Date: 2022-10-12 16:27:24
* @Desc: 损坏赔偿数据库访问层
*/
public interface CompensateMapper {

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

