package com.wr.api.estate.web.application.security;

import com.wr.api.estate.service.application.security.PreventService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.vo.PreventVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:15:39
 * @Desc: 防控信息控制层
 */
@RestController
@RequestMapping("v2/prevent")
@Api(tags = "物业移动端-防控信息")
public class PreventController extends BaseController {

    @Autowired
    private PreventService preventService;

    /**
     * 获取防控信息集合
     *
     * @param prevent
     * @return com.wr.common.core.web.page.TableDataInfo
     */
    @GetMapping("/getList")
    @ApiOperation("防控信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "estateId", value = "物业id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo getList(Prevent prevent) {
        startPage();
        List<Prevent> preventList = preventService.getList(prevent);
        return getDataTable(preventList);
    }


    /**
     * 防空详情
     *
     * @param preventId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("防控信息详情")
    @GetMapping("/getPreventDetail/{preventId}")
    public AjaxResult getPreventDetail(@PathVariable Long preventId) {
        return AjaxResult.success(preventService.getPreventDetail(preventId));
    }
}
