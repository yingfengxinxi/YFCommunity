package com.wr.api.estate.web.application.serve;

import com.google.common.collect.Lists;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.info.UserLogService;
import com.wr.api.estate.service.application.serve.DecorationService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.domain.SysDictData;
import com.wr.remote.estate.customer.vo.decorate.DecorateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 13:34:36
 * @Desc: 装修列表【物业移动端】
 */
@RestController
@RequestMapping("/v2/decoration")
@Api(tags = "物业移动端-装修工单")
public class DecorationController extends BaseController {

    @Autowired
    private DecorationService decorationService;


    /**
     * 查询装修分页列表
     *
     * @param decorateVo
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询装修分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "0=全部1=节点上传2=已完成", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(DecorateVo decorateVo) throws ServerException {
        if (decorateVo.getCommunityId() == null) {
            throw new ServerException("communityId不能为空");
        }
        if (StringUtils.isEmpty(decorateVo.getStatus())) {
            throw new ServerException("status查询数据状态0=查询全部1=节点上传数据2=已完成数据不能为空");
        }
        if (StringUtils.equals("1", decorateVo.getStatus())) {
            //查询上传节点数据
            decorateVo.setRenovationAuditStatusList(Lists.newArrayList("0,1,2,3,4".split(",")));

        }
        if (StringUtils.equals("2", decorateVo.getStatus())) {
            //查询已完成数据
            decorateVo.setRenovationAuditStatus("5");
        }
        startPage();
        List<DecorateVo> list = decorationService.getDecorateVoList(decorateVo);
        return getDataTable(list);
    }

    /**
     * 查询装修详情
     *
     * @param id
     * @return
     */
    @ApiOperation("查询装修详情")
    @GetMapping(value = "/{id}")
    public AjaxResult getDecorationDetail(@PathVariable String id) {
        return AjaxResult.success(decorationService.getDecorationDetail(id));
    }

}
