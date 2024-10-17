package com.wr.estate.web.work.orderLedger;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.work.orderLedger.HomLedService;
import com.wr.remote.estate.order.vo.ledgerVo.HomeLedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 11:51:33
 * @Desc: 保洁台账
 */
@RestController
@RequestMapping("/homLed")
public class HomLedController extends BaseController {

    @Autowired
    private HomLedService homLedService;

    /**
     * 分页列表
     *
     * @param homeLedVo
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("work:homLed:list")
    public TableDataInfo list(HomeLedVo homeLedVo) {
        startPage();
        List<HomeLedVo> list = homLedService.listHomLed(homeLedVo);
        return getDataTable(list);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param orderId
     * @return
     */
    @GetMapping(value = "/getHomLed/{orderId}")
    @RequiresPermissions("work:homLed:query")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        return AjaxResult.success(homLedService.getHomLed(orderId));
    }

    /**
     * 协助人员列表
     *
     * @param orderId
     * @return
     */
    @GetMapping("/staList/{orderId}")
    @RequiresPermissions("work:homLed:query")
    public TableDataInfo staList(@PathVariable("orderId") Long orderId) {
        startPage();
        List<HomeLedVo> list = homLedService.getHomStaffList(orderId);
        return getDataTable(list);
    }

    @GetMapping(value = "/imgList/{orderId}")
    @RequiresPermissions("work:homLed:query")
    public AjaxResult getOrderImgs(@PathVariable("orderId") Long orderId) {
        return AjaxResult.success(homLedService.orderImg(orderId));
    }
}
