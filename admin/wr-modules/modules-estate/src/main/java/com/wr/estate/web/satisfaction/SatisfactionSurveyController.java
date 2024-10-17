package com.wr.estate.web.satisfaction;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.estate.service.satisfaction.SatisfactionSurveyService;
import com.wr.remote.domain.SatisfactionSurvey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/2/27 17:37
 */
@RestController
@RequestMapping("satisfactionSurvey")
@Api(tags = "物管平台-满意度调查")
public class SatisfactionSurveyController extends BaseController {

    @Autowired
    private SatisfactionSurveyService satisfactionSurveyService;


    @PostMapping("list")
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
    @PostMapping("save")
    public AjaxResult save(@RequestBody SatisfactionSurvey satisfactionSurvey) {
        Date parse = new Date();
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
    }


    @ApiOperation("满意度调查修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody SatisfactionSurvey satisfactionSurvey) {
        Date parse = new Date();
        if (satisfactionSurvey.getEndTime().getTime() < parse.getTime()) {
            return AjaxResult.error(510, "结束日期不得小于当前日期");
        }
        if (satisfactionSurvey.getEndTime().getTime() < satisfactionSurvey.getStartTime().getTime()) {
            return AjaxResult.error(510, "结束日期不得小于开始日期");
        }
        satisfactionSurveyService.updateSatisfactionSurvey(satisfactionSurvey);

        return AjaxResult.success();
    }


    @ApiOperation("满意度调查删除")
    @DeleteMapping("removeById")
    public AjaxResult removeById(@RequestParam("id") String id) {

        satisfactionSurveyService.delete(id);

        return AjaxResult.success();
    }
}
