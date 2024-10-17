package com.wr.api.estate.mapper.application.manage;

import com.wr.api.estate.entity.application.manage.GoodsOut;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 13:41:01
 * @Desc: 数据层
 */
public interface GoodsOutMapper {

    /**
     * 列表
     * @param goodsOut
     * @return
     */
    List<GoodsOut> getList(GoodsOut goodsOut);

    /**
     * 图片列表
     * @param tableId
     * @return
     */
    List<GoodsOut> getImage(Long tableId);

    /**
     * 修改出门状态
     * @param goodsOut
     * @return
     */
    int updateGoodsOut(GoodsOut goodsOut);

    /**
     * 获取详情
     * @param outId
     * @return
     */
    GoodsOut getById(Long outId);

    /**
     * 今日出门总数
     * @return
     */
    int goodsOutCount(Long communityId);
}
