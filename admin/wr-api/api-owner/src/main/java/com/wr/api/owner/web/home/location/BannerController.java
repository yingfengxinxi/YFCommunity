package com.wr.api.owner.web.home.location;

import com.wr.api.owner.entity.home.location.Banner;
import com.wr.api.owner.service.home.location.BannerService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 17:50:16
 * @Desc: 轮播图控制层
 */
@RequestMapping("/v2/banner")
@RestController
public class BannerController extends BaseController {

    @Autowired
    private BannerService bannerService;

    @AuthRequired
    @GetMapping("/img")
    public AjaxResult selectListBanner(@RequestParam(value = "estateId",required = false) Long estateId,
                                       @RequestParam(value = "type",required = false) String type){
        return AjaxResult.success(bannerService.selectListBanner(estateId,type));
    };

}
