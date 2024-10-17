package com.wr.estate.web.vote;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.vote.BusVoteScopeService;
import com.wr.estate.service.vote.BusVoteService;
import com.wr.remote.domain.vo.BusVoteVo;
import com.wr.remote.estate.vote.BusVote;
import com.wr.remote.estate.vote.BusVoteScope;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:28
 */
@RestController
@RequestMapping("voteScope")
@Api(tags = "物管平台-投票范围")
public class BusVoteScopeController extends BaseController {

    @Autowired
    private BusVoteScopeService busVoteScopeService;

    /**
     * 获取投票范围集合
     *
     * @param busVoteScope
     * @return
     */
    @PostMapping("/getList")
    @ApiOperation("分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voteId", value = "投票id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "buildingId", value = "楼栋", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "unitId", value = "单元楼", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "roomId", value = "房号", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "ownerName", value = "业主名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo getList(@RequestBody BusVoteScope busVoteScope) {
        if (StringUtils.isEmpty(busVoteScope.getIl())) {
            return getDataTable(Lists.newArrayList());
        }
        PageUtils.startPage(busVoteScope.getPageNum(), busVoteScope.getPageSize());
        List<BusVoteScope> list = busVoteScopeService.getList(busVoteScope);
        return getDataTable(list);
    }


}
