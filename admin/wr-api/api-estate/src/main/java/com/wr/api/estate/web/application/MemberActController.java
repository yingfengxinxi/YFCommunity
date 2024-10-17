package com.wr.api.estate.web.application;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.api.estate.service.application.MemberActService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.DecryptUtil;
import com.wr.remote.estate.member.MemberJoinVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 18:58:23
 * @Desc:
 */
@RestController
@RequestMapping("v2/member/act")
@Api(tags = "物业移动端-党员活动")
public class MemberActController extends BaseController {

    @Resource
    private MemberActService memberActService;

    @GetMapping("search")
    @EncryptResponse
    @AuthRequired
    @ApiOperation("党员活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo search(Long communityId) {
        startPage();
        return getDataTable(memberActService.searchByCommunityId(communityId));
    }

    /**
     * 详情
     *
     * @param activityId
     * @return
     */
    @GetMapping("detail")
    @EncryptResponse
    @AuthRequired
    @ApiOperation("党员活动详情")
    public AjaxResult detail(@RequestParam("activityId") Long activityId) {
        return AjaxResult.success(memberActService.detailByActivityId(activityId));
    }

    @GetMapping("selectJoinPeopleList")
    @EncryptResponse
    @AuthRequired
    @ApiOperation("参与活动人员")
    public TableDataInfo selectJoinPeopleList(MemberJoinVo memberJoinVo) {
        startPage();
        List<MemberJoinVo> memberJoinVoList = memberActService.selectJoinPeopleList(memberJoinVo);
        if (CollectionUtils.isNotEmpty(memberJoinVoList)) {
            return getDataTable(memberJoinVoList);
        }
        return new TableDataInfo();

    }

}
