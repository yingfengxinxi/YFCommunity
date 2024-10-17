package com.wr.govern.service.autonomy.investigation;

import com.wr.remote.govern.autonomy.investigation.Questionnaire;

import java.text.ParseException;
import java.util.List;

/**
 * 部门管理 服务层
 * 
 * @author wr
 */
public interface InvestigationService
{
    /**
     * 查询所有问卷信息
     * @param questionnaire
     * @return
     */
    List<Questionnaire> selectQuestionnaireList(Questionnaire questionnaire);

    /**
     * 根据问题id查询问卷信息
     * @param questionnaireId
     * @return
     */
    Questionnaire selectQuestionnaireById(Long questionnaireId);

    /**
     * 新增问卷信息
     * @param questionnaire
     * @return
     */
    int insertQuestionnaire(Questionnaire questionnaire) throws ParseException;

    /**
     * 根据问题id删除问卷信息
     * @param questionnaireId
     * @return
     */
    int deleteQuestionnaireById(Long[] questionnaireId);
}
