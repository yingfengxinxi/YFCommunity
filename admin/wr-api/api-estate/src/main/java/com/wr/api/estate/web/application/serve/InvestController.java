package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.service.application.serve.InvestService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.DecryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 11:33:03
 * @Desc:
 */
@RestController
@RequestMapping("v2/invest")
@Api(tags = "物业移动端-信息公示")
public class InvestController extends BaseController {

    @Resource
    private InvestService investService;

    @GetMapping("search")
    @EncryptResponse
    @AuthRequired
    @ApiOperation("信息公示列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "investType", value = "0=物业招商公示1=物业通报公示", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo search(@RequestParam("communityId") Long communityId, @RequestParam("investType") String investType) {
        startPage();
        return getDataTable(investService.searchByCommunityId(communityId, investType));
    }

    @ApiOperation("信息公示详情")
    @GetMapping("detail")
    @EncryptResponse
    @AuthRequired
    public AjaxResult detail(@RequestParam("investId") Long investId) {
        return AjaxResult.success(investService.detailByInvestId(investId));
    }
}
