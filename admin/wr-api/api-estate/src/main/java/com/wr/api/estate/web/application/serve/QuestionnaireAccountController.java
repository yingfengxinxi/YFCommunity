package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.entity.application.serve.QuestionnaireAccountVo;
import com.wr.api.estate.entity.application.serve.QuestionnaireVo;
import com.wr.api.estate.service.application.serve.QuestionnaireAccountService;
import com.wr.api.estate.service.application.serve.QuestionnaireService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.ServerException;

/**
 * @Author lvzy
 * @Date 2024/1/11 14:14
 */
@RestController
@RequestMapping("/v2/questionnaireAccount")
@Api(tags = "物业移动端-调查问卷参与人")
public class QuestionnaireAccountController extends BaseController {

    @Autowired
    private QuestionnaireAccountService questionnaireAccountService;

    /**
     * 调查问卷列表
     *
     * @param questionnaireAccountVo
     * @return
     */
    @GetMapping("/getList")
    @ApiOperation("调查问卷列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireInvestigationId", value = "问卷调查分页列表id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo getList(QuestionnaireAccountVo questionnaireAccountVo) {
        try {
            if (questionnaireAccountVo.getQuestionnaireInvestigationId() == null) {
                throw new ServerException("questionnaireInvestigationId不能为空");
            }
            startPage();
            return getDataTable(questionnaireAccountService.getList(questionnaireAccountVo.getQuestionnaireInvestigationId()));
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("getById")
    @ApiOperation("调查问卷业主提交详情")
    public AjaxResult getById(@RequestParam("id") String id) {

        return AjaxResult.success(questionnaireAccountService.getById(id));
    }
}
