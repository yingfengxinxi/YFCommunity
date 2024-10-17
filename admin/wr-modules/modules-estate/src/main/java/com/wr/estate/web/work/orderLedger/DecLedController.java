package com.wr.estate.web.work.orderLedger;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.orderLedger.DecLedService;
import com.wr.remote.work.fitment.DecorateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-07 11:48:36
 * @Desc:
 */
@RestController
@RequestMapping("/decLed")
public class DecLedController extends BaseController {

    @Autowired
    private DecLedService decLedService;

    /**
     * 装修评定工单列表
     *
     * @param decorateOrder
     * @return
     */
    @RequiresPermissions("work:decLed:list")
    @GetMapping("/list")
    public TableDataInfo list(DecorateOrder decorateOrder) {
        startPage();
        List<DecorateOrder> list = decLedService.getDecorateOrderList(decorateOrder);
        return getDataTable(list);
    }

    /**
     * 查询装修评定工单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping(value = "/{orderId}")
    @RequiresPermissions("work:decLed:query")
    public AjaxResult getDecorateOrderDetail(@PathVariable Long orderId) {
        return decLedService.getDecorateOrderDetail(orderId);
    }
}
