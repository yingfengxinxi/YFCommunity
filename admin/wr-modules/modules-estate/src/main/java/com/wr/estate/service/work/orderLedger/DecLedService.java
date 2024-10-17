package com.wr.estate.service.work.orderLedger;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.work.fitment.DecorateOrder;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-07 11:42:22
 * @Desc: 装修评定台账
 */
public interface DecLedService {

    /**
     * 装修评定工单列表
     *
     * @param decorateOrder
     * @return
     */
    List<DecorateOrder> getDecorateOrderList(DecorateOrder decorateOrder);

    /**
     * 查询装修评定工单详情
     *
     * @param orderId
     * @return
     */
    AjaxResult getDecorateOrderDetail(Long orderId);
}
