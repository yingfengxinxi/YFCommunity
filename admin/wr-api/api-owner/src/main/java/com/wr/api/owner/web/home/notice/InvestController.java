package com.wr.api.owner.web.home.notice;


import com.wr.api.owner.entity.home.notice.Invest;
import com.wr.api.owner.entity.home.notice.Notice;
import com.wr.api.owner.service.home.headline.HeadlineService;
import com.wr.api.owner.service.home.notice.InvestService;
import com.wr.api.owner.service.home.notice.NoticeService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/14 14:46
 * @Desc: 公示控制层
 */
@RestController
@RequestMapping("/v2/invest")
public class InvestController extends BaseController {

    @Autowired
    private InvestService investService;
    @Autowired
    private NoticeService noticeService;

    /**
     * 获取公示列表
     *
     * @param invest
     * @return
     * @throws ParseException
     */
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo list(Invest invest) throws ParseException {
        startPage();
        //调用接口获取内容
        List<Invest> list = investService.getList( invest );
        return getDataTable( list );
    }


    /**
     * 公示详情
     *
     * @param investId
     * @return
     */
    @AuthRequired
    @GetMapping(value = "/{investId}")
    public AjaxResult getInvestById(@PathVariable Long investId) {
        Invest notice = investService.getInvestById( investId );
        return AjaxResult.success( notice );
    }


    /**
     * 查询发布时间最新的公示或者， 公示， 并发送状态码！
     *
     * @return
     */
    @GetMapping(value = "/news")
    @AuthRequired
    public AjaxResult getNews(Invest invest) {

        AjaxResult ajaxResult = new AjaxResult();

        Notice notice = new Notice();
        notice.setCommunityId( invest.getCommunityId() );
        notice.setEstateId( invest.getEstateId() );

        //接口
        Notice not = noticeService.selectNews( notice );//最新公告
        Invest n = investService.selectNews( invest );//最新公示
        //公示、公告都有最新值
        if (not != null && n != null) {
            int i = not.getCreateTime().compareTo( n.getCreateTime() );
            if (i > 0) {
                ajaxResult.put( "data", not );
                ajaxResult.put( "status", 0 ); //公告
            } else {
                ajaxResult.put( "data", n );
                ajaxResult.put( "status", 1 ); //公示
            }
            return AjaxResult.success( ajaxResult );
        }

        //公示、公告 可能没有任何记录
        if (not != null) {
            ajaxResult.put( "data", not );
            ajaxResult.put( "status", 0 ); //公告
            return AjaxResult.success( ajaxResult );
        }
        if (n != null) {
            ajaxResult.put( "data", n );
            ajaxResult.put( "status", 1 ); //公示'
            return AjaxResult.success( ajaxResult );
        }
        return AjaxResult.success( ajaxResult );
    }

    /**
     * 信息公示列表
     */
    @PostMapping("/getList")
    @ApiOperation("信息公示列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "investTitle", value = "标题", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "investType", value = "类型0=物业招商公示1=物业通报公示", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo getList(@RequestBody com.wr.remote.estate.info.Invest invest) {

        invest.setEstateId( invest.getEstateId() );
        PageUtils.startPage(invest.getPageNum(), invest.getPageSize());
        List<com.wr.remote.estate.info.Invest> list = investService.selectList(invest);
        return getDataTable(list);
    }

    /**
     * 根据编号获取详细信息
     */
    @ApiOperation("信息公示详细")
    @GetMapping(value = "getInfo/{investId}")
    public AjaxResult getInfo(@PathVariable Long investId) {
        //实现阅读量+1
        investService.updateRedNum(investId);
        com.wr.remote.estate.info.Invest invest = investService.selectInvestById(investId);
        return AjaxResult.success(invest);
    }

    /**
     * 添加信息公示
     */
    @ApiOperation("添加信息公示")
    @RequiresPermissions("estate:invest:add")
    @Log(title = "添加信息公示", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public AjaxResult add(@Validated @RequestBody com.wr.remote.estate.info.Invest invest) {
        invest.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        List<com.wr.remote.estate.info.Invest> invests = investService.checkTitleUnique(invest);
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
    @PostMapping("edit")
    public AjaxResult edit(@Validated @RequestBody com.wr.remote.estate.info.Invest invest) {
        List<com.wr.remote.estate.info.Invest> invests = investService.checkTitleUnique2(invest);
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
