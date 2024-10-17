package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.location.ExtractCode;
import com.wr.api.owner.entity.vo.serve.ApiGoodsVo;
import com.wr.api.owner.service.serve.BorrowByMeService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我的借用
 * @Author: lvzy
 * @Date: 2022-11-14 20:53:43
 * @Desc:我的借用
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2/borrowByMe")
public class BorrowByMeController extends BaseController {

    private final BorrowByMeService borrowByMeService;

    @GetMapping("/listPage")
    @AuthRequired
    public TableDataInfo listPage(ApiGoodsVo apiGoodsVo) {
        new ExtractCode().pageListAntiSql(apiGoodsVo.getPageNum(), apiGoodsVo.getPageSize());
        return getDataTable(borrowByMeService.listPage(apiGoodsVo.getAccountId()));
    }

    @GetMapping("/detail")
    @AuthRequired
    public AjaxResult detail(ApiGoodsVo apiGoodsVo) {
        return borrowByMeService.detail(apiGoodsVo.getBorrowId());
    }

    @GetMapping("/qrCode")
    @AuthRequired
    public AjaxResult qrCode(ApiGoodsVo apiGoodsVo) {
        return borrowByMeService.qrCode(apiGoodsVo.getBorrowId());
    }
}
