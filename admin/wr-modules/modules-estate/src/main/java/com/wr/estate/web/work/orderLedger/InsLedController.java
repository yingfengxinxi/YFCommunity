package com.wr.estate.web.work.orderLedger;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.orderLedger.InsLedService;
import com.wr.remote.estate.order.vo.ledgerVo.InspectLedVo;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-25 20:14:28
 * @Desc: 巡检台账
 */
@RestController
@RequestMapping("/inspectLedger")
public class InsLedController extends BaseController {

    @Autowired
    private InsLedService insLedService;

    /**
     * 分页列表
     *
     * @param inspectLedVo
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("work:inspectLedger:list")
    public TableDataInfo list(InspectLedVo inspectLedVo) {
        startPage();
        List<InspectLedVo> list = insLedService.listInspect(inspectLedVo);
        return getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param inspectId
     * @return
     */
    @GetMapping(value = "/getInspect/{inspectId}")
    @RequiresPermissions("work:inspectLedger:query")
    public AjaxResult getInfo(@PathVariable("inspectId") Long inspectId) {
        return AjaxResult.success(insLedService.getInspect(inspectId));
    }

    /**
     * 协助人员列表
     *
     * @param inspectId
     * @return
     */
    @GetMapping("/staList/{inspectId}")
    @RequiresPermissions("work:inspectLedger:query")
    public TableDataInfo staList(@PathVariable("inspectId") Long inspectId) {
        startPage();
        List<InspectLedVo> list = insLedService.getInsStaffList(inspectId);
        return getDataTable(list);
    }

    @GetMapping("/detailList/{inspectId}")
    @RequiresPermissions("work:inspectLedger:query")
    public AjaxResult detailList(@PathVariable("inspectId") Long inspectId) {
        return AjaxResult.success(insLedService.detailList(inspectId));
    }

    @GetMapping("/nameList/{communityId}")
    @RequiresPermissions("work:inspectLedger:list")
    public AjaxResult nameList(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(insLedService.getTourName(communityId));
    }

    @GetMapping("/deatilListThree/{inspectId}")
    @RequiresPermissions("work:inspectLedger:query")
    public AjaxResult deatilListThree(@PathVariable("inspectId") Long inspectId) {
        List<OrderRecord> list = insLedService.deatilListThree(inspectId);
        return AjaxResult.success(list);
    }
}
