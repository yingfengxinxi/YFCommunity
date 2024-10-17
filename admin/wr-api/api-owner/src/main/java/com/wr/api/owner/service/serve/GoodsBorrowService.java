package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.ApiGoodsVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * 物品借用
 * @Author: lvzy
 * @Date: 2022-11-14 13:17:44
 * @Desc: 物品借用
 */
public interface GoodsBorrowService {

    /**
     * 根据借用人ID查物品借用列表
     * @param communityId 小区ID
     * @return List
     */
    List<ApiGoodsVo> listPage(Long communityId);

    /**
     * 详情查看
     * @param goodsId
     * @return
     */
    ApiGoodsVo detail(Long goodsId);

    /**
     * 借用按钮
     * @param apiGoodsVo
     * @return
     */
    AjaxResult borrowByMe(ApiGoodsVo apiGoodsVo);
}
