package com.wr.estate.service.customer.goods;

import com.wr.remote.estate.customer.goods.vo.GoodsVo;

import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-11 19:40:56
 * @Desc: 物品管理服务接口
 */
public interface GoodsService {

    /**
     * 物品列表
     * @param goodsVo
     * @return
     */
    List<GoodsVo> selectGoodsList(GoodsVo goodsVo);

    /**
     * 物品列表
     * @param goodsVo
     * @return
     */
    List<GoodsVo> export(GoodsVo goodsVo);

    /**
     * 物品详情
     * @param goodsId
     * @return
     */
    GoodsVo getGoodsInfo(Long goodsId);

    /**
     * 修改
     * @param goodsVo
     * @return
     */
    int updateGoods(GoodsVo goodsVo);

    /**
     * 新增
     *
     * @param goodsVo
     * @return
     */
    int insertClassify(GoodsVo goodsVo);

    /**
     * 删除
     * @param goodsId
     * @return
     */
    int deleteById(Long goodsId);

    /**
     * 新增提醒
     * @param goodsVo
     * @return
     */
    int insertRemind(GoodsVo goodsVo);
}
