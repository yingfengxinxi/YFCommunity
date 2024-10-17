package com.wr.estate.web.autonomy;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusCommunityCare;
import com.wr.estate.service.autonomy.AutonomyStatuteService;
import com.wr.remote.domain.AutonomyStatute;
import com.wr.remote.domain.CommunityInformation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/25 14:08
 */
@RestController
@RequestMapping("autonomyStatute")
@Api(tags = "物管平台-自治规约")
public class AutonomyStatuteController extends BaseController {

    @Autowired
    private AutonomyStatuteService autonomyStatuteService;


    /**
     * 分页
     *
     * @param autonomyStatute 查询对象
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("自治规约列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(@RequestBody AutonomyStatute autonomyStatute) {

        LambdaQueryWrapper<AutonomyStatute> queryWrapper = new LambdaQueryWrapper<>();
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        queryWrapper.eq(AutonomyStatute::getEstateId, estateId);
        queryWrapper.eq(AutonomyStatute::getDelFlag, "0");
        if (autonomyStatute.getCommunityId() != null) {
            queryWrapper.eq(AutonomyStatute::getCommunityId, autonomyStatute.getCommunityId());
        }
        if (StringUtils.isNotEmpty(autonomyStatute.getName())) {
            queryWrapper.like(AutonomyStatute::getName, autonomyStatute.getName());
        }
        queryWrapper.orderByAsc(AutonomyStatute::getSort);
        queryWrapper.orderByDesc(AutonomyStatute::getCreateTime);
        PageUtils.startPage(autonomyStatute.getPageNum(), autonomyStatute.getPageSize());
        return getDataTable(autonomyStatuteService.list(queryWrapper));
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @ApiOperation("自治规约详情")
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable String id) {

        return AjaxResult.success(autonomyStatuteService.getById(id));
    }

    /**
     * @param autonomyStatute
     * @return
     */
    @ApiOperation("自治规约新增")
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody AutonomyStatute autonomyStatute) {

        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        autonomyStatute.setEstateId(estateId);
        autonomyStatute.setDelFlag("0");
        autonomyStatute.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        autonomyStatute.setCreateBy(SecurityUtils.getUsername());
        autonomyStatuteService.save(autonomyStatute);
        return AjaxResult.success();
    }

    /**
     * @param autonomyStatute
     * @return
     */
    @ApiOperation("自治规约修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody AutonomyStatute autonomyStatute) {

        autonomyStatute.setUpdateTime(new Date());
        autonomyStatute.setUpdateBy(SecurityUtils.getUsername());
        autonomyStatuteService.updateById(autonomyStatute);
        return AjaxResult.success();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ApiOperation("自治规约删除")
    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("id") String id) {
        autonomyStatuteService.removeById(id);
        return AjaxResult.success();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @GetMapping("batchDelete")
    public AjaxResult batchDelete(@RequestParam("ids") List<String> ids) {
        for (String id : ids) {
            autonomyStatuteService.removeById(id);
        }
        return AjaxResult.success();
    }
}
