package com.wr.api.owner.web.estateapi;

import com.wr.api.owner.service.estateapi.PeopleLostService;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.remote.estate.customer.lost.PeopleLostVo;
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
 * @Date: 2022-10-27 10:56:19
 * @Desc: 寻人启事控制层
 */
@RestController
@RequestMapping("/estateapi/peopleLost")
@Api(tags = "业主端-寻人启示")
public class PeopleLostController extends BaseController {

    @Autowired
    private PeopleLostService peopleLostService;

    /**
     * 分页查询
     *
     * @param peopleLost 筛选条件
     * @return 查询结果
     */
    @PostMapping("/list")
    @ApiOperation("分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "lostName", value = "姓名", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "lostStatus", value = "状态0=寻找中，1=已找回，2=已撤销", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "contactName", value = "联系人", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "contactPhone", value = "联系人手机号", dataType = "String", required = false, paramType = "query")

    })
    public TableDataInfo list(@RequestBody PeopleLostVo peopleLost) {
        PageUtils.startPage(peopleLost.getPageNum(), peopleLost.getPageSize());
        List<PeopleLostVo> list = peopleLostService.selectPeopleLostList(peopleLost);
        return getDataTable(list);
    }


    @GetMapping("/{lostId}")
    @ApiOperation("详情")
    public AjaxResult get(@PathVariable("lostId") Long lostId) {

        return AjaxResult.success(peopleLostService.get(lostId));
    }

    @ApiOperation("新增")
    @PostMapping("addPeoLost")
    public AjaxResult addPeoLost(@RequestBody PeopleLostVo peopleLostVo) {

        peopleLostVo.setPublishTime(new Date());
        peopleLostService.addPeoLost(peopleLostVo);

        return AjaxResult.success();
    }

    @ApiOperation("修改")
    @PostMapping("updatePeoLost")
    public AjaxResult updatePeoLost(@RequestBody PeopleLostVo peopleLostVo) {
        peopleLostService.updatePeoLost(peopleLostVo);

        return AjaxResult.success();
    }

    @ApiOperation("修改状态")
    @GetMapping("updatePeoLostStatus")
    public AjaxResult updatePeoLostStatus(@RequestParam("lostId") Long lostId, @RequestParam("lostStatus") String lostStatus) {

        PeopleLostVo peopleLostVo = peopleLostService.get(lostId);
        peopleLostVo.setLostStatus(lostStatus);
        if (lostStatus.equals("0")) {
            peopleLostVo.setPublishTime(new Date());
        }
        peopleLostService.updatePeoLost(peopleLostVo);

        return AjaxResult.success();
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 是否删除成功
     */
    @Log(title = "寻人删除", businessType = BusinessType.DELETE)
    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        return AjaxResult.success(peopleLostService.deleteById(id));
    }
}

