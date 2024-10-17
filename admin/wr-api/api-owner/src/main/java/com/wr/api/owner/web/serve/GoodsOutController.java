package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.location.ExtractCode;
import com.wr.api.owner.entity.vo.serve.ApiGoodsOutVo;
import com.wr.api.owner.service.serve.GoodsOutService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 物品出门
 * @Author: lvzy
 * @Date: 2022-11-15 10:07:31
 * @Desc:物品出门
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v2/goodsOut")
public class GoodsOutController extends BaseController {

    private final GoodsOutService goodsOutService;

    @GetMapping("/passageList")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult passageList(ApiGoodsOutVo goodsOutVo) {
        return AjaxResult.success(goodsOutService.passageList(goodsOutVo.getCommunityId()));
    }

    @PostMapping("/add")
    @ApiLog(title = "新增物品出门", businessType = BusinessType.INSERT)
    @AuthRequired
//    @EncryptResponse
    public AjaxResult passageAdd(@RequestBody ApiGoodsOutVo goodsOutVo) {
        if (!StringUtils.isNull(goodsOutVo)) {
            goodsOutService.passageAdd(goodsOutVo);
        }
        return AjaxResult.success();
    }

    @GetMapping("/listPage")
    @AuthRequired
//    @EncryptResponse
    public TableDataInfo listPage(ApiGoodsOutVo goodsOutVo) {
        new ExtractCode().pageListAntiSql(goodsOutVo.getPageNum(), goodsOutVo.getPageSize());
        return getDataTable(goodsOutService.listPage(goodsOutVo.getAccountId()));
    }

    @GetMapping("/detail")
    @AuthRequired
//    @EncryptResponse
    public AjaxResult detail(ApiGoodsOutVo goodsOutVo) {
        return AjaxResult.success(goodsOutService.detail(goodsOutVo.getOutId()));
    }
}
