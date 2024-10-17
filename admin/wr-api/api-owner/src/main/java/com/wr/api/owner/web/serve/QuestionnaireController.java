package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.serve.QuesAnswer;
import com.wr.api.owner.entity.vo.serve.QuestionnaireVO;
import com.wr.api.owner.service.serve.QuestionnaireService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-14 15:01:04
 * @Desc: 服务-问卷调查 控制层
 */
@RestController
@RequestMapping("/v2/questionnaire")
public class QuestionnaireController extends BaseController {
    @Autowired
    private QuestionnaireService questionnaireService;

    /**
     * 获取所在小区的问卷调查
     * @param communityId 小区id
     * @param accountId 账号id
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
//    @AuthRequired
    @GetMapping("/newList")
    public TableDataInfo newList(Long communityId, Long accountId) {
        if (ObjectUtils.isEmpty(communityId)) {
            throw new ServiceException("参数不能为空");
        }
        startPage();
        List<QuestionnaireVO> questionnaireNewAll = questionnaireService.getQuestionnaireNewAll(communityId, accountId);
        return getDataTable(questionnaireNewAll);
    }

    /**
     * 查看详情
     * @param questionnaireId 主键id
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
//    @AuthRequired
    @GetMapping("/info")
    public AjaxResult info(Long questionnaireId) {
        if (ObjectUtils.isEmpty(questionnaireId)) {
            return AjaxResult.error("参数不能为空");
        }
        QuestionnaireVO questionnaireById = questionnaireService.getQuestionnaireById(questionnaireId);
        return AjaxResult.success(questionnaireById);
    }

    /**
     * 添加
     * @param quesAnswer
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
//    @AuthRequired
    @ApiLog(title = "问卷调查", businessType = BusinessType.INSERT)
    @PostMapping("/addQuesAnswer")
    public AjaxResult addQuesAnswer(@RequestBody QuesAnswer quesAnswer) {
        Integer num = questionnaireService.addQuesAnswer(quesAnswer);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
