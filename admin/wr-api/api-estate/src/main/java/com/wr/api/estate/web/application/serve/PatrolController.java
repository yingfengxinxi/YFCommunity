package com.wr.api.estate.web.application.serve;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.serve.InspectionService;
import com.wr.api.estate.service.application.serve.PatrolService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.Inspection;
import com.wr.remote.domain.Patrol;
import com.wr.remote.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:51
 */
@RestController
@RequestMapping("/v2/patrol")
@Api(tags = "物业移动端-巡更工单")
public class PatrolController extends BaseController {

    @Autowired
    private PatrolService patrolService;

    @Autowired
    private LoginService loginService;


    @GetMapping("list")
    @ApiOperation("巡更工单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(Patrol patrol) {
        LambdaQueryWrapper<Patrol> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Patrol::getCommunityId, patrol.getCommunityId());
        SysUser sysUser = loginService.getByUserIdUser(patrol.getName());
        String userType = sysUser.getUserType();
        if (!userType.equals("10")) {
            queryWrapper.eq(Patrol::getName, patrol.getName());
        }

        if (StringUtils.isNotEmpty(patrol.getPatrolStatus())) {
            queryWrapper.eq(Patrol::getPatrolStatus, patrol.getPatrolStatus());
        }

        queryWrapper.orderByDesc(Patrol::getCreateTime);
        startPage();
        return getDataTable(patrolService.list(queryWrapper));
    }


    @GetMapping("getReportedCount")
    @ApiOperation("巡更待上报数量统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "物业人员id", dataType = "Long", required = false, paramType = "query"),
    })
    public AjaxResult getReportedCount(Patrol patrol) {
        LambdaQueryWrapper<Patrol> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Patrol::getCommunityId, patrol.getCommunityId());
        SysUser sysUser = loginService.getByUserIdUser(patrol.getName());
        String userType = sysUser.getUserType();
        if (!userType.equals("10")) {
            queryWrapper.eq(Patrol::getName,patrol.getName());
        }
        queryWrapper.eq(Patrol::getPatrolStatus, "0");
        queryWrapper.orderByDesc(Patrol::getCreateTime);
        return AjaxResult.success(patrolService.list(queryWrapper).size());
    }

    /**
     * @param patrol
     * @return
     */
    @ApiOperation("巡更工单提交")
    @PostMapping("save")
    public AjaxResult save(@RequestBody Patrol patrol) {
        patrolService.add(patrol);
        return AjaxResult.success();
    }

    /**
     * @return
     */
    @ApiOperation("巡更工单详情")
    @GetMapping("get")
    public AjaxResult get(@RequestParam("id") String id) {

        return AjaxResult.success(patrolService.get(id));
    }
}
