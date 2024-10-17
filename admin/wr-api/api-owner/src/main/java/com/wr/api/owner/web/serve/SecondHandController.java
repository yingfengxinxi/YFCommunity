package com.wr.api.owner.web.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.owner.entity.dto.SecondHandDTO;
import com.wr.api.owner.entity.vo.serve.SecondHandVo;
import com.wr.api.owner.service.serve.SecondHandService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import com.wr.remote.estate.customer.secondhand.SecondHand;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author: xuShu
 * @Date: 2022-11-11 15:12:54
 * @Desc:
 */
@RequestMapping("/v2/secondHand")
@RestController
public class SecondHandController extends BaseController {
    @Autowired
    private SecondHandService secondHandService;

    /**
     * 二手交易列表
     *
     * @param secondHandVo
     * @return
     */
    @GetMapping("/list")
    @AuthRequired
    public TableDataInfo getList(SecondHandVo secondHandVo) {
        startPage();
        List<SecondHandDTO> list = secondHandService.getSecondHandList(secondHandVo);
        return getDataTable(list);
    }

    /**
     * 添加二手交易
     *
     * @param secondHandVo
     * @return
     */
    @PostMapping("/add")
    @AuthRequired
    @ApiLog(title = "新增二手交易", businessType = BusinessType.INSERT)
    public AjaxResult addSecondHand(@RequestBody SecondHandVo secondHandVo) {
        return AjaxResult.success(secondHandService.addSecondHand(secondHandVo));
    }

    /**
     * 查询二手交易详情
     *
     * @param handId
     * @return
     */
    @GetMapping("/info")
    @AuthRequired
    public AjaxResult SecondHandInfo(Long handId) {
        if (handId == null) {
            return AjaxResult.error();
        }
        SecondHandVo secondHandVo = secondHandService.SecondHandInfo(handId);
        return AjaxResult.success(secondHandVo);
    }

    /**
     * 下架
     * @author yx
     * @date 2023/1/13 13:25
     * @param handId
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @GetMapping("/outStack")
    @AuthRequired
    public AjaxResult outStack(Long handId) {
        if (handId == null) {
            return AjaxResult.error();
        }
        secondHandService.outStack(handId);
        return AjaxResult.success();
    }

    /**
     * 已出售
     *
     * @param handId id
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2023/1/13 13:25
     */
    @GetMapping("/sell")
    @AuthRequired
    public AjaxResult sell(Long handId) {
        if (handId == null) {
            return AjaxResult.error();
        }
        secondHandService.sell(handId);
        return AjaxResult.success();
    }

    /**
     * @param communityId
     * @return
     */
    @ApiOperation("查询广告图")
    @GetMapping("{communityId}")
    public AjaxResult get(@PathVariable("communityId") Long communityId) {
        LambdaQueryWrapper<SecondHand> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecondHand::getCommunityId, communityId);
        return AjaxResult.success(secondHandService.getOne(queryWrapper));
    }
}
