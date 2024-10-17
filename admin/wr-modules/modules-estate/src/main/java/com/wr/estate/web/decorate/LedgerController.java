package com.wr.estate.web.decorate;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.customer.decorate.LedgerService;
import com.wr.remote.estate.customer.vo.decorate.DecorateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-13 13:29:07
 * @Desc: 装修台账
 */
@RestController
@RequestMapping("/ledger")
public class LedgerController extends BaseController {

    @Autowired
    private LedgerService ledgerService;

    /**
     * 查询装修台账列表
     *
     * @param decorateVo
     * @return
     */
    @RequiresPermissions("customer:ledger:list")
    @GetMapping("/list")
    public TableDataInfo list(DecorateVo decorateVo) {
        startPage();
        List<DecorateVo> list = ledgerService.getDecorateLedgerList(decorateVo);
        return getDataTable(list);
    }

    /**
     * 查询装修台账详情
     *
     * @param decorateId
     * @return
     */
    @RequiresPermissions("customer:ledger:detail")
    @GetMapping(value = "/{decorateId}")
    public AjaxResult getDecorateLedgerDetail(@PathVariable Long decorateId) {
        return ledgerService.getDecorateLedgerDetail(decorateId);
    }
}
