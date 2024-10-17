package com.wr.api.estate.web.application.manage;

import com.wr.api.estate.entity.application.manage.GoodsOut;
import com.wr.api.estate.service.application.manage.GoodsOutService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.constant.UserConstants;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.estate.manage.Passage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 14:17:15
 * @Desc:
 */
@RestController
@RequestMapping("/v2/goodsOut")
public class GoodsOutController extends BaseController {
    @Autowired
    private GoodsOutService goodsOutService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public TableDataInfo findAllByPage(GoodsOut goodsOut) {
        startPage();
        List<GoodsOut> lists = goodsOutService.getList(goodsOut);
        return getDataTable(lists);
    }

    /**
     * 通过主键查询单条数据
     */
    @GetMapping(value = "/getInfoById/{outId}")
    public AjaxResult getInfo(@PathVariable("outId") Long outId) {
        return AjaxResult.success(goodsOutService.getById(outId));
    }
    /**
     * 修改数据
     *
     * @param
     * @return 修改结果
     */
    @ApiLog(title="物品出门-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult update(@RequestBody GoodsOut goodsOut) {
        return AjaxResult.success(goodsOutService.updateGoodsOut(goodsOut));
    }

    /**
     * 获取今日总出门数
     * @param communityId
     * @return
     */
    @GetMapping(value = "/getCount/{communityId}")
    public AjaxResult allCount(@PathVariable("communityId") Long communityId) {
        return AjaxResult.success(goodsOutService.goodsOutCount(communityId));
    }
}
