package com.wr.estate.web.work.orderLedger;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.orderLedger.WatLedService;
import com.wr.remote.estate.order.vo.ledgerVo.WatchmanLedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-28 17:57:33
 * @Desc: 巡更台账
 */
@RestController
@RequestMapping("/watLed")
public class WatLedController extends BaseController {

    @Autowired
    private WatLedService watLedService;

    /**
     * 分页列表
     *
     * @param watchmanLedVo
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("work:watLed:list")
    public TableDataInfo list(WatchmanLedVo watchmanLedVo) {
        startPage();
        List<WatchmanLedVo> list = watLedService.listWatLed(watchmanLedVo);
        return getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param watchmanId
     * @return
     */
    @GetMapping(value = "/getWatLed/{watchmanId}")
    @RequiresPermissions("work:watLed:query")
    public AjaxResult getInfo(@PathVariable("watchmanId") Long watchmanId) {
        return AjaxResult.success(watLedService.getWatLed(watchmanId));
    }

    /**
     * 协助人员列表
     *
     * @param watchmanId
     * @return
     */
    @GetMapping("/staList/{watchmanId}")
    @RequiresPermissions("work:watLed:query")
    public TableDataInfo staList(@PathVariable("watchmanId") Long watchmanId) {
        startPage();
        List<WatchmanLedVo> list = watLedService.getWatStaffList(watchmanId);
        return getDataTable(list);
    }

    @GetMapping("/nameList/{communityId}")
    @RequiresPermissions("work:watLed:list")
    public AjaxResult nameList(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(watLedService.getTourName(communityId));
    }

    @GetMapping("/routeList/{communityId}")
    @RequiresPermissions("work:watLed:list")
    public AjaxResult routeList(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(watLedService.getRouteName(communityId));
    }
}
