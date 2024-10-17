package com.wr.estate.web.work.orderLedger;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.work.orderLedger.DisasterLedgerBillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-11-02 11:09:29
 * @Desc:
 */
@RestController
@RequestMapping("disasterledgerbill")
public class DisasterLedgerBillController extends BaseController {

    @Resource
    private DisasterLedgerBillService disasterLedgerBillService;

    @GetMapping("query")
    public TableDataInfo query(@RequestParam Map<String,Object> params) {
        startPage();
        return getDataTable(disasterLedgerBillService.query(params));
    }

    @GetMapping("queryDisaster")
    public AjaxResult queryDisaster() {

        return AjaxResult.success(disasterLedgerBillService.queryDisaster());
    }

    @GetMapping("queryAssist")
    public AjaxResult queryAssist(Long id) {

        return AjaxResult.success(disasterLedgerBillService.queryAssist(id));
    }

}
