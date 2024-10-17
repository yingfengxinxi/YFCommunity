package com.wr.govern.web.security;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.govern.service.security.PreventService;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.vo.PreventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:15:39
 * @Desc: 治安防控信息
 */
@RestController
@RequestMapping("/prevent")
public class PreventController extends BaseController {

    @Autowired
    private PreventService preventService;

    /**
     * 获取防控信息集合
     * @param prevent
     * @return com.wr.common.core.web.page.TableDataInfo
     */
    @RequiresPermissions("govern:prevent:list")
    @GetMapping("/getList")
    public TableDataInfo getList(Prevent prevent){
        startPage();
        List<Prevent> preventList=preventService.getList(prevent);
        return getDataTable(preventList);
    }

    /**
     * 添加防控信息
     * @param prevent
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:prevent:add")
    @Log(title = "添加防控信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addPrevent(@Validated @RequestBody PreventVO prevent){
        return toAjax(preventService.addPrevent(prevent));
    }

    /**
     * 修改防控信息
     * @param prevent
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:prevent:edit")
    @Log(title = "修改防控信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updatePrevent(@Validated @RequestBody PreventVO prevent){
        return toAjax(preventService.updatePrevent(prevent));
    }

    /**
     * 防空详情
     * @param preventId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:prevent:list")
    @GetMapping("/getPreventDetail/{preventId}")
    public AjaxResult getPreventDetail(@PathVariable Long preventId){
        return AjaxResult.success(preventService.getPreventDetail(preventId));
    }

    /**
     * 删除信息
     * @param preventId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @RequiresPermissions("govern:prevent:remove")
    @Log(title = "删除防控信息", businessType = BusinessType.DELETE)
    @RequestMapping("/delPrevent/{preventId}")
    public AjaxResult delPrevent(@PathVariable Long preventId){
        return toAjax(preventService.delPrevent(preventId));
    }

}
