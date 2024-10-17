package com.wr.api.estate.web.application.serve;


import com.wr.api.estate.entity.application.serve.CommunityCareVo;
import com.wr.api.estate.service.application.serve.CommunityCareService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:46:15
 * @Desc: 社区关怀
 */
@RestController
@RequestMapping("/v2/communityCare")
@Api(tags = "移动物业-社区关怀")
public class CommunityCareController extends BaseController {

    @Autowired
    private CommunityCareService communityCareService;

    /**
     * 分页
     *
     * @param communityCareVo 查询对象
     * @return
     */
    @AuthRequired
    @GetMapping("/list")
    @ApiOperation("社区关怀列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityName", value = "名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(CommunityCareVo communityCareVo) {
        startPage();
        List<CommunityCareVo> rescueVOList = communityCareService.getList(communityCareVo);
        return getDataTable(rescueVOList);
    }

    /**
     * 类型查询
     *
     * @return
     */
    @ApiOperation("社区关怀类型列表")
    @AuthRequired
    @GetMapping("/getTypeList")
    public AjaxResult getTypeList() {

        return AjaxResult.success(communityCareService.getTypeList());
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @ApiOperation("社区关怀详情")
    @AuthRequired
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable Long id) {

        return AjaxResult.success(communityCareService.getById(id));
    }


}
