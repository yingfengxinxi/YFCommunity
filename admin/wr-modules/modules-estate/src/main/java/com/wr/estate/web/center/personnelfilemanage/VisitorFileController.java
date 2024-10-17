package com.wr.estate.web.center.personnelfilemanage;

import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.center.personnelfilemanage.VisitorFileService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.center.vo.VisitorFileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author: lvzy
 * @Date: 2022-10-18 15:23:24
 * @Desc:
 */
@RestController
@RequestMapping("visitorfile")
@Api(tags = "物管平台-访客管理【弃用】")
public class VisitorFileController extends BaseController {

    @Resource
    private VisitorFileService visitorFileService;

    //{"visitorPhone":"123","visitorName":"123","pageNum":1,"pageSize":10,"communityId":1}
    @ApiOperation("分页列表")
    @PostMapping("query")
    public TableDataInfo query(@RequestBody VisitorFileVo visitorFileVo) {
        PageUtils.startPage(visitorFileVo.getPageNum(), visitorFileVo.getPageSize());
        return getDataTable(visitorFileService.query(visitorFileVo));
    }

    /**
     * 详情
     *
     * @param
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("{visitorId}")
    public AjaxResult getVisitorById(@PathVariable("visitorId") Long visitorId) {

        return AjaxResult.success(visitorFileService.getVisitorById(visitorId));
    }

    /**
     * 删除
     *
     * @param
     * @return
     */
    @RequiresPermissions("center:visitorfile:deleted")
    @Log(title = "删除访客", businessType = BusinessType.DELETE)
    @PostMapping("deleted")
    public AjaxResult update(@RequestBody Long[] ids) {

        return AjaxResult.success(visitorFileService.deleted(ids));
    }

    /**
     * 导出
     */
    @RequiresPermissions("center:visitorfile:export")
    @Log(title = "访客档案信息导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisitorFileVo vop) {

        List<VisitorFileVo> list = visitorFileService.query(vop);
        ExcelUtil<VisitorFileVo> util = new ExcelUtil(VisitorFileVo.class);
        util.exportExcel(response, list, "访客档案数据");

    }
}
