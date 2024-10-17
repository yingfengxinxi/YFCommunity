package com.wr.api.estate.web.application.serve;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wr.api.estate.service.application.serve.HomeMenuService;
import com.wr.common.core.constant.DelConstants;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.HomeMenu;
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
 * @Date 2024/3/13 16:33
 */
@RestController
@RequestMapping("/v2/homeMenu")
@Api(tags = "首页菜单")
public class HomeMenuController extends BaseController {

    @Autowired
    private HomeMenuService homeMenuService;

    /**
     * @param estateId
     * @return
     */
    @GetMapping("getList")
    @ApiOperation("菜单列表")
    public AjaxResult getList() {

        return AjaxResult.success(homeMenuService.selectList());
    }

    @PostMapping("del")
    @ApiOperation("轮播图列表删除")
    public AjaxResult del(@RequestParam String homeMenuIds) {
        if (StringUtils.isBlank(homeMenuIds)) {
            return AjaxResult.error("轮播图主键ID不可为空");
        }
        List<String> homeMenuIdList = Arrays.asList(homeMenuIds.split(","));
        LambdaUpdateWrapper<HomeMenu> bannerUpdateWrapper = new LambdaUpdateWrapper<>();
        bannerUpdateWrapper.set(HomeMenu::getDelFlag, DelConstants.IS_DELETE);
        bannerUpdateWrapper.in(HomeMenu::getHomeMenuId, homeMenuIdList);
        boolean update = homeMenuService.update(bannerUpdateWrapper);
        return AjaxResult.state(update);
    }

    @PostMapping("edit")
    @ApiOperation("轮播图列表修改")
    public AjaxResult edit(@Valid @RequestBody HomeMenu homeMenu) {
        if (null == (homeMenu.getHomeMenuId())) {
            return AjaxResult.error("主键ID不可为空");
        }
        Long userid = SecurityUtils.getUserId();
        homeMenu.setType("1");
        homeMenu.setUpdateBy(String.valueOf(userid));
        homeMenu.setUpdateTime(new Date());
        LambdaUpdateWrapper<HomeMenu> eq = new LambdaUpdateWrapper<HomeMenu>()
                .eq(HomeMenu::getHomeMenuId, homeMenu.getHomeMenuId());
        boolean update = homeMenuService.update(homeMenu, eq);
        return AjaxResult.state(update);
    }

    @PostMapping("add")
    @ApiOperation("轮播图列表修改")
    public AjaxResult add(@Valid @RequestBody HomeMenu homeMenu) {
        Long userid = SecurityUtils.getUserId();
        homeMenu.setType("1");
        homeMenu.setCreateBy(String.valueOf(userid));
        homeMenu.setCreateTime(new Date());
        homeMenu.setDelFlag(DelConstants.NOT_DELETE);
        boolean save = homeMenuService.save(homeMenu);
        return AjaxResult.state(save);
    }
}
