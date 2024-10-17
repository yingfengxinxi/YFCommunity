package com.wr.api.owner.web.estateapi;


import com.wr.api.owner.entity.estateapi.BusCommunityCareType;
import com.wr.api.owner.service.estateapi.BusCommunityCareTypeService;
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
 * @Desc: 社区关怀类型
 */
@RestController
@RequestMapping("/estateapi/communityCareType")
@Api(tags = "业主端-社区关怀类型")
public class CommunityCareTypeController extends BaseController {

    @Autowired
    private BusCommunityCareTypeService communityCareTypeService;

    /**
     * 分页
     *
     * @param communityCareType 查询对象
     * @return
     */
    @ApiOperation("社区关怀类型列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    @GetMapping("/getListPage")
    public TableDataInfo getListPage(BusCommunityCareType communityCareType) {
        startPage();
        List<BusCommunityCareType> rescueVOList = communityCareTypeService.getList(communityCareType);
        return getDataTable(rescueVOList);
    }

    /**
     * 下拉查询
     *
     * @param communityCareType 查询对象
     * @return
     */
    @ApiOperation("社区关怀类型下拉查询")
    @GetMapping("/list")
    public AjaxResult list(BusCommunityCareType communityCareType) {
        List<BusCommunityCareType> rescueVOList = communityCareTypeService.getList(communityCareType);
        return AjaxResult.success(rescueVOList);
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @ApiOperation("社区关怀类型详情")
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable Long id) {

        return AjaxResult.success(communityCareTypeService.getById(id));
    }

    /**
     * @param communityCareType
     * @return
     */
    @ApiOperation("社区关怀类型添加")
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody BusCommunityCareType communityCareType) {
        Long id = communityCareTypeService.getByNameId(communityCareType.getName());
        if (id != null) {
            return AjaxResult.error("当前类型已存在");
        }
        communityCareTypeService.insert(communityCareType);
        return AjaxResult.success();
    }

    /**
     * @param communityCareType
     * @return
     */
    @ApiOperation("社区关怀类型修改")
    @PostMapping("update")
    public AjaxResult update(@RequestBody BusCommunityCareType communityCareType) {
        Long id = communityCareTypeService.getByNameId(communityCareType.getName());
        if (id != null) {
            Long id1 = communityCareType.getId();
            if (id != id1) {
                return AjaxResult.error("当前类型已存在");
            }
        }
        communityCareTypeService.update(communityCareType);
        return AjaxResult.success();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ApiOperation("社区关怀类型删除")

    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("id") Long id) {
        communityCareTypeService.delete(id);
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
            communityCareTypeService.delete(id);
        }
        return AjaxResult.success();
    }
}
