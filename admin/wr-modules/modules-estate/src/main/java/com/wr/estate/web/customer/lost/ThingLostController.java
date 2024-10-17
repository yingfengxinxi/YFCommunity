package com.wr.estate.web.customer.lost;

import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.estate.service.customer.lost.ThingLostService;
import com.wr.remote.estate.customer.lost.PeopleLostVo;
import com.wr.remote.estate.customer.lost.ThingLostVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-27 10:58:11
 * @Desc: 寻物启事表控制层
 */
@RestController
@RequestMapping("/thingLost")
@Api(tags = "物管平台-寻物启事")
public class ThingLostController extends BaseController {

    @Autowired
    private ThingLostService thingLostService;

    /**
     * 分页查询
     *
     * @param thingLost 筛选条件
     * @return 查询结果
     */
    @ApiOperation("分页列表")
    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "lostName", value = "姓名", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "lostStatus", value = "状态0=寻找中，1=已找回，2=已撤销", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "contactName", value = "联系人", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "contactPhone", value = "联系人手机号", dataType = "String", required = false, paramType = "query")

    })
    public TableDataInfo list(@RequestBody ThingLostVo thingLost) {
        PageUtils.startPage(thingLost.getPageNum(), thingLost.getPageSize());
        List<ThingLostVo> list = thingLostService.selectThingLostList(thingLost);
        return getDataTable(list);
    }


    @GetMapping("/{lostId}")
    @ApiOperation("详情")
    public AjaxResult get(@PathVariable("lostId") Long lostId) {

        return AjaxResult.success(thingLostService.get(lostId));
    }

    @ApiOperation("新增")
    @PostMapping("addThingLost")
    public AjaxResult addThingLost(@RequestBody ThingLostVo thingLostVo) {

        thingLostVo.setPublishTime(new Date());
        thingLostService.addThingLost(thingLostVo);

        return AjaxResult.success();
    }

    @ApiOperation("修改")
    @PostMapping("updateThingLost")
    public AjaxResult updateThingLost(@RequestBody ThingLostVo thingLostVo) {
        thingLostService.updateThingLost(thingLostVo);

        return AjaxResult.success();
    }

    @ApiOperation("修改状态")
    @GetMapping("updateThingLostStatus")
    public AjaxResult updateThingLostStatus(@RequestParam("lostId") Long lostId, @RequestParam("lostStatus") String lostStatus) {

        ThingLostVo thingLostVo = thingLostService.get(lostId);
        thingLostVo.setLostStatus(lostStatus);
        if (lostStatus.equals("0")) {
            thingLostVo.setPublishTime(new Date());
        }
        thingLostService.updateThingLost(thingLostVo);

        return AjaxResult.success();
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 是否删除成功
     */
    @ApiOperation("寻物删除")
    @DeleteMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        return AjaxResult.success(thingLostService.deleteById(id));
    }

}

