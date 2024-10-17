package com.wr.api.owner.web.estateapi.satisfaction;

import cn.hutool.core.collection.CollUtil;
import com.wr.api.owner.service.estateapi.satisfaction.SatisfactionSurveyService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.SatisfactionSurvey;
import com.wr.remote.domain.SatisfactionSurveyOwner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/27 17:37
 */
@RestController
@RequestMapping("/estateapi/satisfactionSurvey")
@Api(tags = "业主端-满意度调查")
public class SatisfactionSurveyController extends BaseController {

    @Autowired
    private SatisfactionSurveyService satisfactionSurveyService;


    @PostMapping("/list")
    @ApiOperation("满意度调查列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "executorBy", value = "当前登录账号loginName", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(@RequestBody SatisfactionSurvey satisfactionSurvey) {

        PageUtils.startPage(satisfactionSurvey.getPageNum(), satisfactionSurvey.getPageSize());
        return getDataTable(satisfactionSurveyService.getList(satisfactionSurvey));
    }

    /**
     * @param id
     * @param flag 0=修改详情1=查询详情
     * @return
     */
    @ApiOperation("满意度调查详情")
    @GetMapping("{id}/{flag}")
    public AjaxResult get(@PathVariable("id") String id, @PathVariable("flag") String flag) {

        SatisfactionSurvey satisfactionSurvey = satisfactionSurveyService.get(id, flag);

        return AjaxResult.success(satisfactionSurvey);
    }


    @ApiOperation("满意度调查增加")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody SatisfactionSurvey satisfactionSurvey) {

        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sim.parse(DateUtils.getDate());
            if (satisfactionSurvey.getStartTime().getTime() < parse.getTime()) {
                return AjaxResult.error(510, "开始日期不得小于当前日期");
            }

            if (satisfactionSurvey.getEndTime().getTime() < parse.getTime()) {
                return AjaxResult.error(510, "结束日期不得小于当前日期");
            }

            if (satisfactionSurvey.getEndTime().getTime() < satisfactionSurvey.getEndTime().getTime()) {
                return AjaxResult.error(510, "结束日期不得小于开始日期");
            }

            satisfactionSurveyService.saveSatisfactionSurvey(satisfactionSurvey);

            return AjaxResult.success();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @ApiOperation("满意度调查修改")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody SatisfactionSurvey satisfactionSurvey) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = sim.parse(DateUtils.getDate());

            if (satisfactionSurvey.getEndTime().getTime() < parse.getTime()) {
                return AjaxResult.error(510, "结束日期不得小于当前日期");
            }

            if (satisfactionSurvey.getEndTime().getTime() < satisfactionSurvey.getStartTime().getTime()) {
                return AjaxResult.error(510, "结束日期不得小于开始日期");
            }
            satisfactionSurveyService.updateSatisfactionSurvey(satisfactionSurvey);

            return AjaxResult.success();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @ApiOperation("满意度调查删除")
    @DeleteMapping("/removeById")
    public AjaxResult removeById(@PathVariable("id") String id) {

        satisfactionSurveyService.delete(id);

        return AjaxResult.success();
    }

    @PostMapping("/findListSatisfaction")
    @ApiOperation("满意度调查列表-业主端")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "executorBy", value = "当前登录账号loginName", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo findListSatisfaction(@RequestBody SatisfactionSurvey satisfactionSurvey) {
        if(satisfactionSurvey == null || StringUtils.isBlank( satisfactionSurvey.getBuildingId() )){
            throw new ServiceException("获取查询参数失败，请稍后重试！");
        }
        PageUtils.startPage(satisfactionSurvey.getPageNum(), satisfactionSurvey.getPageSize());
        return getDataTable(satisfactionSurveyService.findListSatisfaction(satisfactionSurvey));
    }

    @PostMapping("/findDetilSatisfaction")
    @ApiOperation("满意度调查详情-业主端")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "executorBy", value = "当前登录账号loginName", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public AjaxResult findDetilSatisfaction(@RequestBody SatisfactionSurvey satisfactionSurvey) {
        if(satisfactionSurvey == null || satisfactionSurvey.getId() == null){
            throw new ServiceException("获取ID失败，请稍后重试！");
        }

        return AjaxResult.success(satisfactionSurveyService.findDetilSatisfaction(satisfactionSurvey));
    }

    @ApiOperation("满意度调查业主答案增加")
    @PostMapping("/saveSurveyOwner")
    public AjaxResult saveSurveyOwner(@RequestBody SatisfactionSurvey satisfactionSurvey) {
        if(satisfactionSurvey == null || CollUtil.isEmpty( satisfactionSurvey.getOwnerList() )){
            throw new ServiceException("获取满意度调查答题失败，请刷新后重试！");
        }

        return satisfactionSurveyService.saveSurveyOwner( satisfactionSurvey );

    }

    @ApiOperation("满意度调查业主答案修改")
    @PostMapping("/updateSurveyOwner")
    public AjaxResult updateSurveyOwner(@RequestBody List<SatisfactionSurveyOwner> surveyOwnerList) {
        if(CollUtil.isEmpty( surveyOwnerList )){
            throw new ServiceException("获取满意度调查答题失败，请刷新后重试！");
        }

        return satisfactionSurveyService.updateSurveyOwner( surveyOwnerList );

    }

}
