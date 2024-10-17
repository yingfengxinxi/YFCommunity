package com.wr.estate.web.security;

import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.security.PreventService;
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
@RequestMapping("/prevent")
@Api(tags = "物管平台-防控信息")
public class PreventController extends BaseController {

    @Autowired
    private PreventService preventService;

    /**
     * 获取防控信息集合
     *
     * @param prevent
     * @return
     */
    @PostMapping("/getList")
    @ApiOperation("防控信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "preventTitle", value = "标题", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "preventTypeId", value = "类型【防控信息类型表id】", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo getList(@RequestBody Prevent prevent) {
        prevent.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        PageUtils.startPage(prevent.getPageNum(), prevent.getPageSize());
        List<Prevent> preventList = preventService.getList(prevent);
        return getDataTable(preventList);
    }

    /**
     * 添加防控信息
     *
     * @param prevent
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("添加防控信息")
    @Log(title = "添加防控信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addPrevent(@Validated @RequestBody PreventVO prevent) {
        return toAjax(preventService.addPrevent(prevent));
    }

    /**
     * 修改防控信息
     *
     * @param prevent
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("修改防控信息")
    @Log(title = "修改防控信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updatePrevent(@Validated @RequestBody PreventVO prevent) {
        return toAjax(preventService.updatePrevent(prevent));
    }

    /**
     * 防空详情
     *
     * @param preventId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("防空信息详情")
    @GetMapping("/getPreventDetail/{preventId}")
    public AjaxResult getPreventDetail(@PathVariable Long preventId) {
        return AjaxResult.success(preventService.getPreventDetail(preventId));
    }

    /**
     * 删除信息
     *
     * @param preventId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("删除防空信息")
    @Log(title = "删除防控信息", businessType = BusinessType.DELETE)
    @RequestMapping("/delPrevent/{preventId}")
    public AjaxResult delPrevent(@PathVariable Long preventId) {
        return toAjax(preventService.delPrevent(preventId));
    }

}
