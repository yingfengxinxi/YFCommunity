package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.location.ExtractCode;
import com.wr.api.owner.entity.vo.serve.ApiGoodsVo;
import com.wr.api.owner.service.serve.GoodsBorrowService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 物品借用
 * @Author: lvzy
 * @Date: 2022-11-14 13:15:17
 * @Desc: 物品借用
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2/borrow")
public class GoodsBorrowController extends BaseController {

    private final GoodsBorrowService borrowService;

    @GetMapping("/listPage")
    @AuthRequired
//    @EncryptResponse
    public TableDataInfo listPage(ApiGoodsVo apiGoodsVo) {
        new ExtractCode().pageListAntiSql(apiGoodsVo.getPageNum(), apiGoodsVo.getPageSize());
        return getDataTable(borrowService.listPage(apiGoodsVo.getCommunityId()));
    }

    @GetMapping("/detail")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult detail(ApiGoodsVo apiGoodsVo) {
        return AjaxResult.success(borrowService.detail(apiGoodsVo.getGoodsId()));
    }


    @AuthRequired
//    @EncryptResponse
    @PostMapping("/borrowByMe")
    @ApiLog(title = "物品借用", businessType = BusinessType.INSERT)
    public AjaxResult borrowByMe(@RequestBody ApiGoodsVo apiGoodsVo) {
        return borrowService.borrowByMe(apiGoodsVo);
    }
}
