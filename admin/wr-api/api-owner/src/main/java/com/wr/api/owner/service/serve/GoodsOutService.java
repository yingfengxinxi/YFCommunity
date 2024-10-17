package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.ApiGoodsOutVo;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.manage.Passage;

import java.util.List;

/**
 * 物品出门
 * @Author: lvzy
 * @Date: 2022-11-15 10:08:50
 * @Desc:物品出门
 */
public interface GoodsOutService {

    /**
     * 查找出入口
     * @param communityId
     * @return
     */
    List<Passage> passageList(Long communityId);

    /**
     * 新增
     * @param goodsOutVo
     * @return
     */
    AjaxResult passageAdd(ApiGoodsOutVo goodsOutVo);

    /**
     * 分页查询
     * @param accountId
     * @return
     */
    List<ApiGoodsOutVo> listPage(Long accountId);

    /**
     * 详情查看
     * @param outId
     * @return
     */
    ApiGoodsOutVo detail(Long outId);
}
