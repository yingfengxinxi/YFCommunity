package com.wr.api.estate.web.application;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.estate.entity.vo.application.ActivityVO;
import com.wr.api.estate.service.application.ActivityService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.DecryptUtil;
import com.wr.remote.estate.activity.ActiviteManage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 15:55:23
 * @Desc: 【物业端】
 */
@RestController
@RequestMapping("v2/activity")
@Api(tags = "物业移动端-社区活动")
public class ActivityController extends BaseController {

    @Resource
    private ActivityService activityService;

    @GetMapping("search")
    @EncryptResponse
    @AuthRequired
    @ApiOperation("活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo search(@RequestParam(name = "communityId") Long communityId, @RequestParam(name = "state", required = false, defaultValue = "") String state) {
        startPage();
        return getDataTable(activityService.searchByCommunityId(communityId, state));
    }

    @GetMapping("detail")
    @EncryptResponse
    @AuthRequired
    @ApiOperation("活动详情")
    public AjaxResult detail(Long activityId) {
        ActivityVO activityVO = activityService.detailByActivityId(activityId);
        return AjaxResult.success(activityVO);
    }

    @GetMapping("selectJoinPeopleList")
    @EncryptResponse
    @AuthRequired
    public TableDataInfo selectJoinPeopleList(ActiviteManage activiteManage) {
        startPage();
        return getDataTable(activityService.selectJoinPeopleList(activiteManage));
    }

}
