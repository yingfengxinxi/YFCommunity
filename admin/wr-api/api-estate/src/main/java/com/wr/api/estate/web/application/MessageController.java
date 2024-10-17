package com.wr.api.estate.web.application;

import com.wr.api.estate.service.application.MessageService;
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
 * @Date: 2022-11-14 19:23:35
 * @Desc:【物业移动端党员资讯】
 */
@RestController
@RequestMapping("v2/message")
@Api(tags = "物业移动端-党建资讯")
public class MessageController extends BaseController {

    @Resource
    private MessageService messageService;

    @GetMapping("search")
    @EncryptResponse
    @AuthRequired
    @ApiOperation("党建资讯列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo search(@RequestParam("communityId") Long communityId){
        startPage();
        return getDataTable(messageService.searchByCommunityId(communityId));
    }

    @ApiOperation("党建资讯详情")
    @GetMapping("detail")
    @EncryptResponse
    @AuthRequired
    public AjaxResult detail(Long messageId){

        return AjaxResult.success(messageService.detailByMessageId(messageId));
    }
}
