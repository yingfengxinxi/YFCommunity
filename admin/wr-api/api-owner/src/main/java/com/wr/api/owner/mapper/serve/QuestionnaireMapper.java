package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.QuesAnswer;
import com.wr.api.owner.entity.serve.Question;
import com.wr.api.owner.entity.serve.QuestionItem;
import com.wr.api.owner.entity.vo.serve.QuestionnaireVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-14 11:58:30
 * @Desc: 服务-问卷调查 mapper
 */
public interface QuestionnaireMapper {

    /**
     * 获取所在小区的问卷调查
     * @param communityId 小区id
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.QuestionnaireVO>
     */
    List<QuestionnaireVO> getQuestionnaireNewAll(@Param("communityId") Long communityId);

    /**
     * 查看当前问卷是否以作答
     * @param questionnaireId 问卷id
     * @param accountId 业主账号id
     * @return: java.util.List<java.lang.Long>
     */
    List<Long> getQuestionnaireIds(@Param("questionnaireId") Long questionnaireId, @Param("accountId") Long accountId);

    /**
     * 查看详情
     * @param questionnaireId 主键id
     * @return: com.wr.api.owner.entity.vo.serve.QuestionnaireVO
     */
    QuestionnaireVO getQuestionnaireById(@Param("questionnaireId") Long questionnaireId);

    /**
     * 查询问卷题目
     * @param questionnaireId 问卷id
     * @return: java.util.List<com.wr.api.owner.entity.serve.Question>
     * @author: SJiang
     * @date: 2022/11/14 16:22
     */
    List<Question> getQuestionList(@Param("questionnaireId") Long questionnaireId);

    /**
     * 查询题目选项
     * @param questionId 题目id
     * @return: java.util.List<com.wr.api.owner.entity.serve.QuestionItem>
     */
    List<QuestionItem> getQuestionItemList(@Param("questionId") Long questionId);

    /**
     * 查询答案
     * @param tableId 问卷id
     * @param questionId 题目id
     * @return: java.util.List<com.wr.api.owner.entity.serve.QuestionItem>
     */
    List<QuesAnswer> getQuesAnswerList(@Param("tableId") Long tableId, @Param("questionId") Long questionId);

    /**
     * 添加答案
     * @param quesAnswer
     * @return: java.lang.Integer
     */
    Integer addQuesAnswer(QuesAnswer quesAnswer);

    /**
     * 获取问卷参与人数
     * @param tableId
     * @return: java.lang.Integer
     */
    Integer getParticipateCount(@Param("tableId") Long tableId);
}
