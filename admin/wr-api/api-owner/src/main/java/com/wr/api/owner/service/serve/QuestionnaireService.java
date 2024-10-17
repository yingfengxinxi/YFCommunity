package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.serve.QuesAnswer;
import com.wr.api.owner.entity.vo.serve.QuestionnaireVO;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-14 13:07:05
 * @Desc: 服务-问卷调查 业务
 */
public interface QuestionnaireService {

    /**
     * 获取所在小区的问卷调查
     * @param communityId 小区id
     * @param accountId 账号id
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.QuestionnaireVO>
     */
    List<QuestionnaireVO> getQuestionnaireNewAll(Long communityId, Long accountId);

    /**
     * 查看详情
     * @param questionnaireId 主键id
     * @return: com.wr.api.owner.entity.vo.serve.QuestionnaireVO
     */
    QuestionnaireVO getQuestionnaireById(Long questionnaireId);

    /**
     * 添加答案
     * @param quesAnswer
     * @return: java.lang.Integer
     */
    Integer addQuesAnswer(QuesAnswer quesAnswer);
}
