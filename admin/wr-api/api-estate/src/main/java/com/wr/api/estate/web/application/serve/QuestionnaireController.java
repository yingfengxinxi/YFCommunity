package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.entity.application.serve.MeterRecord;
import com.wr.api.estate.entity.application.serve.QuestionnaireVo;
import com.wr.api.estate.service.application.serve.QuestionnaireService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.ServerException;

/**
 * @Author lvzy
 * @Date 2024/1/11 14:14
 */
@RestController
@RequestMapping("/v2/questionnaire")
@Api(tags = "物业移动端-问卷调查")
public class QuestionnaireController extends BaseController {

    @Autowired
    private QuestionnaireService questionnaireService;

    /**
     * 调查问卷列表
     *
     * @param questionnaireVo
     * @return
     */
    @GetMapping("/getList")
    @ApiOperation("调查问卷列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "空查询全部，2=已提交3=历史", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo getList(QuestionnaireVo questionnaireVo) {
        try {
            if (questionnaireVo.getCommunityId() == null) {
                throw new ServerException("communityId不能为空");
            }
            startPage();
            return getDataTable(questionnaireService.getList(questionnaireVo));
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }
}
