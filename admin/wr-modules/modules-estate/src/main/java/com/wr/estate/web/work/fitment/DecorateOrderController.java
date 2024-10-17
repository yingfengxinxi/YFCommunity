package com.wr.estate.web.work.fitment;

import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.fitment.DecorateOrderService;
import com.wr.remote.work.fitment.DecorateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-11-02 15:04:04
 * @Desc: 装修评定工单
 */
@RestController
@RequestMapping("/decorateOrder")
public class DecorateOrderController extends BaseController {

    @Autowired
    private DecorateOrderService decorateOrderService;

    /**
     * 装修评定工单列表
     *
     * @param decorateOrder
     * @return
     */
    @RequiresPermissions("work:decorateOrder:list")
    @GetMapping("/list")
    public TableDataInfo list(DecorateOrder decorateOrder) {
        startPage();
        List<DecorateOrder> list = decorateOrderService.getDecorateOrderList(decorateOrder);
        return getDataTable(list);
    }

    /**
     * 查询装修列表
     *
     * @param communityId
     * @return
     */
    @GetMapping("/getDecorateList/{communityId}")
    @RequiresPermissions("work:decorateOrder:params")
    public AjaxResult getDecorateList(@PathVariable Long communityId) {
        return decorateOrderService.getDecorateList(communityId);
    }

    /**
     * 根据装修id查询装修详细信息
     *
     * @param decorateId
     * @return
     */
    @GetMapping(value = "/{decorateId}")
    @RequiresPermissions("work:decorateOrder:params")
    public AjaxResult getDecorateDetail(@PathVariable Long decorateId) {
        return decorateOrderService.getDecorateDetail(decorateId);
    }

    /**
     * 装修评定工单新增
     *
     * @param decorateOrder
     * @return
     */
    @Log(title = "装修评定工单", businessType = BusinessType.INSERT)
    @RequiresPermissions("work:decorateOrder:add")
    @PostMapping
    public AjaxResult addDecorateOrder(@RequestBody DecorateOrder decorateOrder) {
        return toAjax(decorateOrderService.addDecorateOrder(decorateOrder));
    }

    /**
     * 装修评定工单删除
     *
     * @param orderIds
     * @return
     */
    @Log(title = "装修评定工单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    @RequiresPermissions("work:decorateOrder:delete")
    public AjaxResult deleteDecorateOrder(@PathVariable Long[] orderIds) {
        return toAjax(decorateOrderService.deleteDecorateOrder(orderIds));
    }

    /**
     * 装修评定工单整改
     *
     * @param orderId
     * @return
     */
    @Log(title = "装修评定工单", businessType = BusinessType.UPDATE)
    @PutMapping("/{orderId}")
    @RequiresPermissions("work:decorateOrder:rectification")
    public AjaxResult rectificationDecorateOrder(@PathVariable Long orderId) {
        return toAjax(decorateOrderService.rectificationDecorateOrder(orderId));
    }

    /**
     * 查询装修评定工单详情
     *
     * @param orderId
     * @return
     */
    @GetMapping(value = "/order/{orderId}")
    @RequiresPermissions("work:decorateOrder:detail")
    public AjaxResult getDecorateOrderDetail(@PathVariable Long orderId) {
        return decorateOrderService.getDecorateOrderDetail(orderId);
    }

    /**
     * 装修评定工单修改页面查询数据
     *
     * @param orderId
     * @param communityId
     * @return
     */
    @GetMapping(value = "/update/{orderId}/{communityId}")
    @RequiresPermissions("work:decorateOrder:detail")
    public AjaxResult getUpdateDetail(@PathVariable Long orderId, @PathVariable Long communityId) {
        return decorateOrderService.getUpdateDetail(orderId, communityId);
    }

    /**
     * 装修评定工单修改
     *
     * @param decorateOrder
     * @return
     */
    @Log(title = "装修评定工单", businessType = BusinessType.UPDATE)
    @RequiresPermissions("work:decorateOrder:update")
    @PutMapping
    public AjaxResult updateDecorateOrder(@RequestBody DecorateOrder decorateOrder) {
        return toAjax(decorateOrderService.updateDecorateOrder(decorateOrder));
    }

    /**
     * 查询分配人员信息
     *
     * @param communityId
     * @return
     */
    @GetMapping(value = "/share/{communityId}")
    @RequiresPermissions("work:decorateOrder:params")
    public AjaxResult getShareNameList(@PathVariable Long communityId) {
        return decorateOrderService.getShareNameList(communityId);
    }

    /**
     * 工单分配
     *
     * @param decorateOrder
     * @return
     */
    @Log(title = "装修评定工单", businessType = BusinessType.UPDATE)
    @PutMapping("/distribution")
    @RequiresPermissions("work:decorateOrder:distribution")
    public AjaxResult decorateOrderDistribution(@RequestBody DecorateOrder decorateOrder) {
        return toAjax(decorateOrderService.decorateOrderDistribution(decorateOrder));
    }

    /**
     * 装修评定工单导出
     *
     * @param response
     * @param decorateOrder
     */
    @Log(title = "装修评定工单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @RequiresPermissions("work:decorateOrder:export")
    public void export(HttpServletResponse response, DecorateOrder decorateOrder) {
        List<DecorateOrder> list = decorateOrderService.getDecorateOrderList(decorateOrder);
        ExcelUtil<DecorateOrder> util = new ExcelUtil<DecorateOrder>(DecorateOrder.class);
        util.exportExcel(response, list, "装修评定工单");
    }
}
