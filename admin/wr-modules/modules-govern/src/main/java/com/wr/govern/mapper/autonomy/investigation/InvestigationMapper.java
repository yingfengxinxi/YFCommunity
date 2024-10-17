package com.wr.govern.mapper.autonomy.investigation;

import com.wr.remote.domain.Community;
import com.wr.remote.govern.autonomy.investigation.Questionnaire;

import java.util.List;

/**
 * 问卷管理信息
 *
 * @author liubei
 * @createDate 2022/10/26
 */
public interface InvestigationMapper
{
    /**
     * 查询所有问卷题目
     * @param Questionnaire
     * @return
     */
    List<Questionnaire> selectQuestionnaireList(Questionnaire Questionnaire);

    /**
     * 根据问卷ID查询问卷信息
     * @param questionnaireId
     * @return
     */
    Questionnaire selectQuestionnaireById(Long questionnaireId);

    /***
     * 插入问卷信心
     * @param questionnaire
     * @return
     */
    int insertQuestionnaire(Questionnaire questionnaire);

    /**
     * 删除问卷信息
     * @param questionnaireId
     * @return
     */

    int deleteQuestionnaireById(Long[] questionnaireId);

    /**
     * 根据社区ID查询社区信息
     * @param communityId
     * @return
     */
    Community selectCommunityById(Long communityId);
}
