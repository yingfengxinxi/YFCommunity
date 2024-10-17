package com.wr.estate.web.work.parkmatter;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.parkmatter.ParkOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-04 13:41:33
 * @Desc:
 */
@RequestMapping("parkorder")
@RestController
public class ParkOrderController extends BaseController {

    @Resource
    private ParkOrderService parkOrderService;

    @GetMapping("query")
    @RequiresPermissions("work:parkorder:query")
    public TableDataInfo query(@RequestParam Map<String, Object> params) {
        startPage();
        return getDataTable(parkOrderService.query(params));
    }

    @RequiresPermissions("work:parkorder:updateorder")
    @Log(title = "修改园区工单", businessType = BusinessType.UPDATE)
    @PostMapping("updateOrder")
    public AjaxResult updateOrder(@RequestBody Map<String, Object> params) {

        return AjaxResult.success(parkOrderService.updateOrder(params));
    }

    @RequiresPermissions("work:parkorder:remove")
    @Log(title = "删除园区工单", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public AjaxResult remove(@RequestBody Map<String, Object> params) {

        return AjaxResult.success(parkOrderService.remove(params));
    }

    @RequiresPermissions("work:parkorder:update")
    @Log(title = "分配园区工单负责人", businessType = BusinessType.DELETE)
    @PostMapping("update")
    public AjaxResult update(@RequestBody Map<String, Object> params) {

        return AjaxResult.success(parkOrderService.update(params));
    }
}
