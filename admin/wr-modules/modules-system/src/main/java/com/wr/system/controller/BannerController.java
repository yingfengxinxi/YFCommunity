package com.wr.system.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wr.common.core.constant.DelConstants;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.system.domain.Banner;
import com.wr.system.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/13 16:05
 */
@RestController
@RequestMapping("/banner")
@Api(tags = "首页轮播图")
public class BannerController extends BaseController {

    @Autowired
    private BannerService bannerService;

    /**
     * @param estateId
     * @return
     */
    @GetMapping("getList")
    @ApiOperation("轮播图列表")
    public AjaxResult getList(@RequestParam(value = "estateId",required = false) Long estateId,
                              @RequestParam(value = "type",required = false) String type) {
        return AjaxResult.success(bannerService.selectList(estateId,type));
    }

    @GetMapping("getPage")
    @ApiOperation("轮播图分页")
    public TableDataInfo getPage(@RequestParam(value = "estateId",required = false) Long estateId,
                                 @RequestParam(value = "type",required = false) String type) {
        startPage();
        List<Banner> banners = bannerService.selectList(estateId, type);
        return getDataTable(banners);
    }

    @PostMapping("del")
    @ApiOperation("轮播图列表删除")
    public AjaxResult del(@RequestParam String bannerIds) {
        if (StringUtils.isBlank(bannerIds)) {
            return AjaxResult.error("轮播图主键ID不可为空");
        }
        List<String> bannerIdList = Arrays.asList(bannerIds.split(","));
        LambdaUpdateWrapper<Banner> bannerUpdateWrapper = new LambdaUpdateWrapper<>();
        bannerUpdateWrapper.set(Banner::getDelFlag, DelConstants.IS_DELETE);
        bannerUpdateWrapper.in(Banner::getBannerId, bannerIdList);
        boolean update = bannerService.update(bannerUpdateWrapper);
        return AjaxResult.state(update);
    }

    @PostMapping("edit")
    @ApiOperation("轮播图列表修改")
    public AjaxResult edit(@Valid @RequestBody Banner banner) {
        if (null == (banner.getBannerId())) {
            return AjaxResult.error("轮播图主键ID不可为空");
        }
        Long userid = SecurityUtils.getUserId();
        banner.setUpdateBy(String.valueOf(userid));
        banner.setUpdateTime(new Date());
        LambdaUpdateWrapper<Banner> eq = new LambdaUpdateWrapper<Banner>()
                .eq(Banner::getBannerId, banner.getBannerId());
        boolean update = bannerService.update(banner, eq);
        return AjaxResult.state(update);
    }

    @PostMapping("add")
    @ApiOperation("轮播图列表修改")
    public AjaxResult add(@Valid @RequestBody Banner banner) {
        Long userid = SecurityUtils.getUserId();
        banner.setCreateBy(String.valueOf(userid));
        banner.setCreateTime(new Date());
        banner.setDelFlag(DelConstants.NOT_DELETE);
        boolean save = bannerService.save(banner);
        return AjaxResult.state(save);
    }

}
