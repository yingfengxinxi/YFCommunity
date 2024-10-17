package com.wr.estate.web.customer.secondhand;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.customer.secondhand.SecondHandService;
import com.wr.remote.estate.customer.secondhand.SecondHand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-27 09:46:56
 * @Desc:
 */
@RestController
@RequestMapping("/secondHand")
@Api(tags = "二手交易")
public class SecondHandController extends BaseController {

    @Autowired
    private SecondHandService secondHandService;

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


    /**
     * @param communityId
     * @param thumbnail
     * @return
     */
    @ApiOperation("保存/更新")
    @GetMapping("save")
    public AjaxResult save(@RequestParam("communityId") Long communityId, @RequestParam("thumbnail") String thumbnail) {
        LambdaQueryWrapper<SecondHand> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecondHand::getCommunityId, communityId);
        SecondHand secondHand = secondHandService.getOne(queryWrapper);
        if (secondHand == null) {
            secondHand = new SecondHand();
            secondHand.setCommunityId(communityId);
            secondHand.setThumbnail(thumbnail);
            secondHand.setDelFlag("0");
            secondHand.setCreateBy(SecurityUtils.getUsername());
            secondHand.setCreateTime(new Date());
            secondHandService.save(secondHand);
        } else {
            secondHand.setCommunityId(communityId);
            secondHand.setThumbnail(thumbnail);
            secondHand.setUpdateBy(SecurityUtils.getUsername());
            secondHand.setUpdateTime(new Date());
            secondHandService.updateById(secondHand);
        }
        return AjaxResult.success();
    }
}
