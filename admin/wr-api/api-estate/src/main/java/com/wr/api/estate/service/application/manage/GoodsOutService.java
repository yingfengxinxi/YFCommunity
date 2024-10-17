package com.wr.api.estate.service.application.manage;

import com.wr.api.estate.entity.application.manage.GoodsOut;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 13:51:01
 * @Desc: 业务层
 */
public interface GoodsOutService {

    /**
     * 列表
     * @param goodsOut
     * @return
     */
    List<GoodsOut> getList(GoodsOut goodsOut);

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
    AjaxResult getById(Long outId);

    /**
     * 今日出门总数
     * @return
     */
    int goodsOutCount(Long communityId);


}
