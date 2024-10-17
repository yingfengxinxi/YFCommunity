package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.service.CommunityService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lvzy
 * @Date 2024/1/30 16:08
 */
@RequestMapping("v2/community")
@RestController
@Api(tags = "物业移动端-小区查询")
public class CommunityController extends BaseController {

    @Autowired
    private CommunityService communityService;

    /**
     * @return
     */
    @GetMapping("getCommunityList")
    @ApiOperation("小区列表")
    public AjaxResult getCommunityList(@RequestParam("estateId") Long estateId) {

        return AjaxResult.success(communityService.selectAllCommunityList(estateId));
    }
}
