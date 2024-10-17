package com.wr.estate.web.info;

import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.info.InvestService;
import com.wr.remote.estate.info.Invest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 乐进
 * @Date: 2022-10-13 10:05:07
 * @Desc:
 */
@RestController
@RequestMapping("/invest")
@Api(tags = "物管平台-信息公示")
public class InvestController extends BaseController {

    @Autowired
    private InvestService investService;

    /**
     * 信息公示列表
     */
    @RequiresPermissions("estate:invest:list")
    @PostMapping("/list")
    @ApiOperation("信息公示列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "investTitle", value = "标题", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "investType", value = "类型0=物业招商公示1=物业通报公示", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(@RequestBody Invest invest) {

        invest.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        PageUtils.startPage(invest.getPageNum(), invest.getPageSize());
        List<Invest> list = investService.selectList(invest);
        return getDataTable(list);
    }

    /**
     * 根据编号获取详细信息
     */
    @ApiOperation("信息公示详细")
    @RequiresPermissions("estate:invest:query")
    @GetMapping(value = "/{investId}")
    public AjaxResult getInfo(@PathVariable Long investId) {
//      headlineService.updateReadNum(headlineId);
        Invest invest = investService.selectInvestById(investId);
        return AjaxResult.success(invest);
    }

    /**
     * 添加信息公示
     */
    @ApiOperation("添加信息公示")
    @RequiresPermissions("estate:invest:add")
    @Log(title = "添加信息公示", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Invest invest) {
        invest.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        List<Invest> invests = investService.checkTitleUnique(invest);
        if (invests.size() > 0) {
            return AjaxResult.error("添加信息公示失败,标题重复");
        }
        invest.setCreateBy(SecurityUtils.getUsername());
        int row = investService.insertInvest(invest);
        return toAjax(row);
    }

    /**
     * 发布信息公示
     */
    @ApiOperation("发布信息公示")
    @RequiresPermissions("estate:invest:add")
    @GetMapping(value = "/up/{investId}")
    public AjaxResult up(@PathVariable Long investId) {
        int row = investService.updateStatus(investId);
        return toAjax(row);
    }

    /**
     * 撤回信息公示
     */
    @ApiOperation("撤回信息公示")
    @RequiresPermissions("estate:invest:add")
    @GetMapping(value = "/back/{investId}")
    public AjaxResult back(@PathVariable Long investId) {
        int row = investService.updateStatus2(investId);
        return toAjax(row);
    }

    /**
     * 修改信息公示
     */
    @ApiOperation("修改信息公示")
    @RequiresPermissions("estate:invest:edit")
    @Log(title = "修改信息公示", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Invest invest) {
        List<Invest> invests = investService.checkTitleUnique2(invest);
        if (invests.size() > 0) {
            return AjaxResult.error("修改招商公示失败,标题重复");
        }
        invest.setUpdateBy(SecurityUtils.getUsername());
        int row = investService.updateInvest(invest);
        return toAjax(row);
    }

    /**
     * 删除信息公示
     *
     * @param investIds 主键
     * @return
     */
    @ApiOperation("删除信息公示")
    @RequiresPermissions("estate:invest:remove")
    @Log(title = "删除信息公示", businessType = BusinessType.DELETE)
    @DeleteMapping("/{investIds}")
    public AjaxResult remove(@PathVariable Long[] investIds) {
        int row = investService.deleteInvestByIds(investIds);
        return toAjax(row);
    }
}
