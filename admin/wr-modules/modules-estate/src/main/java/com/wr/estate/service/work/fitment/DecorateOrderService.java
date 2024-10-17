package com.wr.estate.service.work.fitment;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.work.fitment.DecorateOrder;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-11-02 15:54:54
 * @Desc: 装修评定工单
 */
public interface DecorateOrderService {

    /**
     * 装修评定工单列表
     *
     * @param decorateOrder
     * @return
     */
    List<DecorateOrder> getDecorateOrderList(DecorateOrder decorateOrder);

    /**
     * 查询装修列表
     *
     * @param communityId
     * @return
     */
    AjaxResult getDecorateList(Long communityId);

    /**
     * 根据装修id查询装修详细信息
     *
     * @param decorateId
     * @return
     */
    AjaxResult getDecorateDetail(Long decorateId);

    /**
     * 装修评定工单新增
     *
     * @param decorateOrder
     * @return
     */
    int addDecorateOrder(DecorateOrder decorateOrder);

    /**
     * 装修评定工单删除
     *
     * @param orderIds
     * @return
     */
    int deleteDecorateOrder(Long[] orderIds);

    /**
     * 装修评定工单整改
     *
     * @param orderId
     * @return
     */
    int rectificationDecorateOrder(Long orderId);

    /**
     * 查询装修评定工单详情
     *
     * @param orderId
     * @return
     */
    AjaxResult getDecorateOrderDetail(Long orderId);

    /**
     * 装修评定工单修改页面查询数据
     *
     * @param orderId
     * @param communityId
     * @return
     */
    AjaxResult getUpdateDetail(Long orderId, Long communityId);

    /**
     * 装修评定工单修改
     *
     * @param decorateOrder
     * @return
     */
    int updateDecorateOrder(DecorateOrder decorateOrder);

    /**
     * 查询分配人员信息
     *
     * @param communityId
     * @return
     */
    AjaxResult getShareNameList(Long communityId);

    /**
     * 工单分配
     *
     * @param decorateOrder
     * @return
     */
    int decorateOrderDistribution(DecorateOrder decorateOrder);
}
