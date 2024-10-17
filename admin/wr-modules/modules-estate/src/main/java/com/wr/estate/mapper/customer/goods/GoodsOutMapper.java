package com.wr.estate.mapper.customer.goods;

import com.wr.remote.estate.customer.goods.vo.GoodsOutVo;

import java.util.List;

/**
* @Author: Guan Yu
* @Date: 2022-10-12 16:27:24
* @Desc: 物品出门数据库访问层
*/
public interface GoodsOutMapper {

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

    /**
     * 获取员工id
     * @param userId
     * @return
     */
    String getStaffId(Long userId);

}

