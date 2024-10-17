package com.wr.estate.web.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.estate.prevent.BusPreventType;
import com.wr.estate.service.security.BusPreventTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/15 17:31
 */
@RestController
@RequestMapping("preventType")
@Api(tags = "物管平台-防控信息类型")
public class BusPreventTypeController extends BaseController {

    @Autowired
    private BusPreventTypeService preventTypeService;

    /**
     * 分页
     *
     * @param busPreventType 查询对象
     * @return
     */
    @GetMapping("/getListPage")
    @ApiOperation("防控信息类型列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "标题", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo getListPage(BusPreventType busPreventType) {

        LambdaQueryWrapper<BusPreventType> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(busPreventType.getName())) {
            queryWrapper.like(BusPreventType::getName, busPreventType.getName());
        }
        queryWrapper.orderByAsc(BusPreventType::getSort);
        startPage();
        List<BusPreventType> rescueVOList = preventTypeService.list(queryWrapper);
        return getDataTable(rescueVOList);
    }

    /**
     * 下拉查询
     *
     * @return
     */
    @ApiOperation("防控信息类型下拉查询")
    @GetMapping("/list")
    public AjaxResult list() {
        LambdaQueryWrapper<BusPreventType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(BusPreventType::getSort);
        List<BusPreventType> rescueVOList = preventTypeService.list(queryWrapper);
        return AjaxResult.success(rescueVOList);
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @ApiOperation("防控信息类型详情")
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable String id) {

        return AjaxResult.success(preventTypeService.getById(id));
    }

    /**
     * @param busPreventType
     * @return
     */
    @ApiOperation("防控信息类型添加")
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody BusPreventType busPreventType) {

        LambdaQueryWrapper<BusPreventType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(BusPreventType::getName, busPreventType.getName());
        List<BusPreventType> list = preventTypeService.list(queryWrapper);
        if (list.size() >= 1) {
            return AjaxResult.error("当前类型已存在");
        }
        preventTypeService.save(busPreventType);
        return AjaxResult.success();
    }

    /**
     * @param busPreventType
     * @return
     */
    @ApiOperation("防控信息类型修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody BusPreventType busPreventType) {
        LambdaQueryWrapper<BusPreventType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(BusPreventType::getName, busPreventType.getName());
        List<BusPreventType> list = preventTypeService.list(queryWrapper);
        if (list.size() >= 1) {
            String id1 = list.get(0).getId();
            if (!StringUtils.equals(busPreventType.getId(), id1)) {
                return AjaxResult.error("当前类型已存在");
            }
        }
        preventTypeService.updateById(busPreventType);
        return AjaxResult.success();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ApiOperation("防控信息类型删除")
    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("id") Long id) {
        preventTypeService.removeById(id);
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
            preventTypeService.removeById(id);
        }
        return AjaxResult.success();
    }
}
