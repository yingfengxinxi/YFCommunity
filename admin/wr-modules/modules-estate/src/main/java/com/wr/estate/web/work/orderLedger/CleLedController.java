package com.wr.estate.web.work.orderLedger;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.orderLedger.CleLedService;
import com.wr.remote.estate.order.vo.ledgerVo.CleanLedVo;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-01 13:57:33
 * @Desc: 保洁台账
 */
@RestController
@RequestMapping("/cleLed")
public class CleLedController extends BaseController {

    @Autowired
    private CleLedService cleLedService;

    /**
     * 分页列表
     *
     * @param cleanLedVo
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("work:cleLed:list")
    public TableDataInfo list(CleanLedVo cleanLedVo) {
        startPage();
        List<CleanLedVo> list = cleLedService.listCleLed(cleanLedVo);
        return getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param cleanId
     * @return
     */
    @GetMapping(value = "/getCleLed/{cleanId}")
    @RequiresPermissions("work:cleLed:query")
    public AjaxResult getInfo(@PathVariable("cleanId") Long cleanId) {
        return AjaxResult.success(cleLedService.getCleLed(cleanId));
    }

    /**
     * 协助人员列表
     *
     * @param cleanId
     * @return
     */
    @GetMapping("/staList/{cleanId}")
    @RequiresPermissions("work:cleLed:query")
    public TableDataInfo staList(@PathVariable("cleanId") Long cleanId) {
        startPage();
        List<CleanLedVo> list = cleLedService.getCleStaffList(cleanId);
        return getDataTable(list);
    }

    @GetMapping("/deatilListThree/{cleanId}")
    @RequiresPermissions("work:cleLed:query")
    public AjaxResult deatilListThree(@PathVariable("cleanId") Long cleanId) {
        List<OrderRecord> list = cleLedService.deatilListThree(cleanId);
        return AjaxResult.success(list);
    }
}
