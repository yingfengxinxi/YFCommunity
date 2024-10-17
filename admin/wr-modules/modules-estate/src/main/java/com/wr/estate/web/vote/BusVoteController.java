package com.wr.estate.web.vote;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.vote.BusVoteService;
import com.wr.remote.domain.vo.BusVoteVo;
import com.wr.remote.estate.vote.BusVote;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.vo.PreventVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:28
 */
@RestController
@RequestMapping("vote")
@Api(tags = "物管平台-投票表决")
public class BusVoteController extends BaseController {

    @Autowired
    private BusVoteService busVoteService;

    /**
     * 获取投票表决集合
     *
     * @param busVote
     * @return
     */
    @PostMapping("/getList")
    @ApiOperation("分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", value = "投票状态", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "voteNo", value = "投票单号", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "voteName", value = "投票名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo getList(@RequestBody BusVote busVote) {
        busVote.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        PageUtils.startPage(busVote.getPageNum(), busVote.getPageSize());
        List<BusVote> list = busVoteService.getList(busVote);
        return getDataTable(list);
    }

    /**
     * 添加投票表决
     *
     * @param busVoteVo
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("添加投票表决")
    @Log(title = "添加投票表决", businessType = BusinessType.INSERT)
    @PostMapping("saveVote")
    public AjaxResult saveVote(@Validated @RequestBody BusVoteVo busVoteVo) {
        busVoteVo.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        busVoteVo.setDelFlag("0");
        busVoteVo.setCreateBy(SecurityUtils.getUsername());
        busVoteVo.setCreateTime(new Date());
        busVoteVo.setUpdateBy(SecurityUtils.getUsername());
        busVoteVo.setUpdateTime(new Date());
        AjaxResult getTimeResult = getResult(busVoteVo);
        if (getTimeResult != null) return getTimeResult;
        busVoteService.saveVote(busVoteVo);
        return AjaxResult.success();
    }

    @Nullable
    private static AjaxResult getResult(BusVoteVo busVoteVo) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = sim.parse(DateUtils.getDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (busVoteVo.getStartTime().getTime() < parse.getTime()) {
            return AjaxResult.error(510, "投票开始时间不得小于当前时间");
        }

        if (busVoteVo.getEndTime().getTime() < parse.getTime()) {
            return AjaxResult.error(510, "投票结束时间不得小于当前时间");
        }

        if (busVoteVo.getEndTime().getTime() < busVoteVo.getStartTime().getTime()) {
            return AjaxResult.error(510, "投票结束时间不得小于投票开始时间");
        }
        return null;
    }

    /**
     * 修改投票表决
     *
     * @param busVoteVo
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("修改投票表决")
    @Log(title = "修改投票表决", businessType = BusinessType.UPDATE)
    @PutMapping("updateVote")
    public AjaxResult updateVote(@Validated @RequestBody BusVoteVo busVoteVo) {
        busVoteVo.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        busVoteVo.setDelFlag("0");
        busVoteVo.setUpdateBy(SecurityUtils.getUsername());
        busVoteVo.setUpdateTime(new Date());
        AjaxResult getTimeResult = getResult(busVoteVo);
        if (getTimeResult != null) return getTimeResult;
        busVoteService.updateVote(busVoteVo);
        return AjaxResult.success();
    }

    /**
     * 修改投票表决
     *
     * @param busVoteVo
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("延期投票表决")
    @Log(title = "延期投票表决", businessType = BusinessType.UPDATE)
    @PutMapping("yqUpdate")
    public AjaxResult yqUpdate(@Validated @RequestBody BusVoteVo busVoteVo) {
        try {
            busVoteVo.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
            busVoteVo.setDelFlag("0");
            busVoteVo.setUpdateBy(SecurityUtils.getUsername());
            busVoteVo.setUpdateTime(new Date());
            busVoteVo.setIsYq("1");
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            BusVote busVote = busVoteService.getById(busVoteVo.getId());
            if (busVote.getEndTime().getTime() == busVoteVo.getEndTime().getTime()) {

                return AjaxResult.error("投票结束时间未修改，请勿保存");
            }
            if (busVoteVo.getEndTime().getTime() < busVote.getEndTime().getTime()) {
                String endTime = sim.format(busVote.getEndTime());
                return AjaxResult.error("投票结束时间不能小于" + endTime + "时间");
            }
            String date = DateUtils.getDate();

            Date parse = sim.parse(date);
            if (busVoteVo.getEndTime().getTime() < parse.getTime()) {

                return AjaxResult.error("投票结束时间不能小于当天时间");
            }
            busVoteVo.setOldEndTime(busVote.getEndTime());
            busVoteService.updateById(busVoteVo);
            return AjaxResult.success();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 投票表决详情
     *
     * @param id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("投票表决详情")
    @GetMapping("{id}")
    public AjaxResult getById(@PathVariable("id") String id) {
        return AjaxResult.success(busVoteService.get(id));
    }

    /**
     * 删除信息
     *
     * @param id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("删除投票表决")
    @Log(title = "删除投票表决", businessType = BusinessType.DELETE)
    @RequestMapping("/delete")
    public AjaxResult delete(@RequestParam("id") String id) {
        busVoteService.removeVote(id);
        return AjaxResult.success();
    }

    @ApiOperation("查看结果")
    @GetMapping("getResult")
    public AjaxResult getResult(@RequestParam("id") String id) {

        return AjaxResult.success(busVoteService.getResult(id));
    }
}
