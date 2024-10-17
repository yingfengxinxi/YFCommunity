package com.wr.estate.web.work.orderLedger;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.work.orderLedger.ParkLedgerBillService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-01 09:51:34
 * @Desc:
 */
@RestController
@RequestMapping("parkledgerbill")
public class ParkLedgerBillController extends BaseController {

    @Resource
    private ParkLedgerBillService parkLedgerBillService;

    @GetMapping("query")
    public TableDataInfo query(@RequestParam Map<String,Object> params) {
        startPage();
        return getDataTable(parkLedgerBillService.query(params));
    }

    @GetMapping("queryRepair")
    public AjaxResult queryRepair() {

        return AjaxResult.success(parkLedgerBillService.queryRepair());
    }

    @GetMapping("queryAssist")
    public AjaxResult queryAssist(Long id) {

        return AjaxResult.success(parkLedgerBillService.queryAssist(id));
    }
}
