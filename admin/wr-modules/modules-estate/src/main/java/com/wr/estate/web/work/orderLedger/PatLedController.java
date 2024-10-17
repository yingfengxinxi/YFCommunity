package com.wr.estate.web.work.orderLedger;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.orderLedger.PatLedService;
import com.wr.remote.estate.order.vo.ledgerVo.PatrolLedVo;
import com.wr.remote.work.inspect.OrderRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-27 17:35:55
 * @Desc: 巡查台账
 */
@RestController
@RequestMapping("/patLed")
public class PatLedController extends BaseController {

    @Autowired
    private PatLedService patLedService;

    /**
     * 分页列表
     *
     * @param patrolLedVo
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("work:patLed:list")
    public TableDataInfo list(PatrolLedVo patrolLedVo) {
        startPage();
        List<PatrolLedVo> list = patLedService.listPatLed(patrolLedVo);
        return getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param patrolId
     * @return
     */
    @GetMapping(value = "/getPatLed/{patrolId}")
    @RequiresPermissions("work:patLed:query")
    public AjaxResult getInfo(@PathVariable("patrolId") Long patrolId) {
        return AjaxResult.success(patLedService.getPatLed(patrolId));
    }

    /**
     * 协助人员列表
     *
     * @param patrolId
     * @return
     */
    @GetMapping("/staList/{patrolId}")
    @RequiresPermissions("work:patLed:query")
    public TableDataInfo staList(@PathVariable("patrolId") Long patrolId) {
        startPage();
        List<PatrolLedVo> list = patLedService.getPatStaffList(patrolId);
        return getDataTable(list);
    }

    @GetMapping("/nameList/{communityId}")
    @RequiresPermissions("work:patLed:list")
    public AjaxResult nameList(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(patLedService.getTourName(communityId));
    }

    @GetMapping("/deatilListThree/{patrolId}")
    @RequiresPermissions("work:patLed:query")
    public AjaxResult deatilListThree(@PathVariable("patrolId") Long patrolId) {
        List<OrderRecord> list = patLedService.deatilListThree(patrolId);
        return AjaxResult.success(list);
    }
}
