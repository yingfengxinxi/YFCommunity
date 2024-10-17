package com.wr.estate.web.rescue;


import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusCommunityCare;
import com.wr.estate.service.rescue.BusCommunityCareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:46:15
 * @Desc: 社区关怀
 */
@RestController
@RequestMapping("/communityCare")
@Api(tags = "物管平台-社区关怀")
public class CommunityCareController extends BaseController {

    @Autowired
    private BusCommunityCareService communityCareService;

    /**
     * 分页
     *
     * @param communityCare 查询对象
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("社区关怀列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activityName", value = "名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityCareId", value = "类型【社区关怀类型表id】", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(BusCommunityCare communityCare) {
        communityCare.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        startPage();
        List<BusCommunityCare> rescueVOList = communityCareService.getList(communityCare);
        return getDataTable(rescueVOList);
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @ApiOperation("社区关怀详情")
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable Long id) {

        return AjaxResult.success(communityCareService.getById(id));
    }

    /**
     * @param communityCare
     * @return
     */
    @ApiOperation("社区关怀添加")
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody BusCommunityCare communityCare) {
        try {
            communityCareService.insert(communityCare);
            return AjaxResult.success();
        } catch (ServerException e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * @param communityCare
     * @return
     */
    @ApiOperation("社区关怀修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody BusCommunityCare communityCare) {
        communityCareService.update(communityCare);
        return AjaxResult.success();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ApiOperation("社区关怀删除")
    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("id") Long id) {
        communityCareService.delete(id);
        return AjaxResult.success();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @GetMapping("batchDelete")
    public AjaxResult batchDelete(@RequestParam("ids") List<Long> ids) {
        for (Long id : ids) {
            communityCareService.delete(id);
        }
        return AjaxResult.success();
    }

    /**
     * 发布
     *
     * @param status
     * @param id
     * @return
     */
    @ApiOperation("社区关怀发布")
    @GetMapping("updateByIdStatus")
    public AjaxResult updateByIdStatus(@RequestParam("status") String status, @RequestParam("id") Long id) {

        communityCareService.updateByIdStatus(status, id);

        return AjaxResult.success();
    }
}
