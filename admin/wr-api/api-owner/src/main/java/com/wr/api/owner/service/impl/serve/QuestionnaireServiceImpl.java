package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.serve.QuesAnswer;
import com.wr.api.owner.entity.serve.Question;
import com.wr.api.owner.entity.serve.QuestionItem;
import com.wr.api.owner.entity.vo.serve.QuestionnaireVO;
import com.wr.api.owner.mapper.serve.QuestionnaireMapper;
import com.wr.api.owner.service.serve.QuestionnaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-14 13:07:50
 * @Desc: 服务-问卷调查 业务实现
 */
@Service
@Slf4j
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Override
    public List<QuestionnaireVO> getQuestionnaireNewAll(Long communityId, Long accountId) {
        List<QuestionnaireVO> questionnaireNewAll = questionnaireMapper.getQuestionnaireNewAll(communityId);
        questionnaireNewAll.forEach(x->{
            // 查看当业主是否以作答
            List<Long> questionnaireIds = questionnaireMapper.getQuestionnaireIds(x.getQuestionnaireId(), accountId);
            // 获取参与人数
            Integer participateCount = questionnaireMapper.getParticipateCount(x.getQuestionnaireId());
            x.setParticipateCount(participateCount);
            if (questionnaireIds.size() > 0){
                x.setStatus("1");
            }
        });
        return questionnaireNewAll;
    }

    @Override
    public QuestionnaireVO getQuestionnaireById(Long questionnaireId) {
        QuestionnaireVO questionnaireById = questionnaireMapper.getQuestionnaireById(questionnaireId);
        // 查询问卷题目
        List<Question> questionList = questionnaireMapper.getQuestionList(questionnaireById.getQuestionnaireId());
        questionnaireById.setQuestions(questionList);
        questionList.forEach(x->{
            // 获取题目选项
            List<QuestionItem> questionItemList = questionnaireMapper.getQuestionItemList(x.getQuestionId());
            x.setQuestionItems(questionItemList);
            // 获取题目答案
            List<QuesAnswer> quesAnswerList = questionnaireMapper.getQuesAnswerList(questionnaireId, x.getQuestionId());
            x.setQuesAnswers(quesAnswerList);
        });
        return questionnaireById;
    }

    @Override
    public Integer addQuesAnswer(QuesAnswer quesAnswer) {
           Integer num = questionnaireMapper.addQuesAnswer(quesAnswer);
        return num;
    }
}
