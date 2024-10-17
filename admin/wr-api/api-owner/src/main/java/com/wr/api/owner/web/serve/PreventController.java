package com.wr.api.owner.web.serve;

import com.wr.api.owner.service.serve.PreventService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.PreventSafety;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 11:05:01
 * @Desc: 服务-治安防控控制层
 */
@RestController
@RequestMapping("/v2/prevent")
public class PreventController extends BaseController {
    @Autowired
    private PreventService preventService;

    /**
     * 查看本小区内的治安防控信息
     *
     * @param communityId
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo list(Long communityId) {
        if (ObjectUtils.isEmpty(communityId)) {
            return new TableDataInfo();
        }
        startPage();
        List<PreventSafety> preventAll = preventService.getPreventAll(communityId);
        return getDataTable(preventAll);
    }

    /**
     * 查看治安防控详情
     * @param preventId
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/info")
    public AjaxResult info(Long preventId) {
        if (ObjectUtils.isEmpty(preventId)) {
            return AjaxResult.error("参数不能为空");
        }
        PreventSafety preventById = preventService.getPreventById(preventId);
        return AjaxResult.success(preventById);
    }

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
        prevent.setEstateId( prevent.getEstateId());
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
    public AjaxResult addPrevent(@Validated @RequestBody com.wr.remote.govern.security.vo.PreventVO prevent) {
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
    @PostMapping("updatePrevent")
    public AjaxResult updatePrevent(@Validated @RequestBody com.wr.remote.govern.security.vo.PreventVO prevent) {
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
    public AjaxResult getPreventDetail(@PathVariable("preventId") Long preventId) {
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
