package com.wr.govern.service.impl.autonomy.investigation;


import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.autonomy.investigation.*;
import com.wr.govern.service.autonomy.investigation.InvestigationService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.autonomy.investigation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 问卷调查服务类实现 服务实现
 *
 * @author liubei
 * @createDate 2022-10-24
 */
@Service
public class InvestigationServiceImpl implements InvestigationService {
    @Autowired
    private InvestigationMapper investigationMapper;
    @Autowired
    private QuestionCommunityMapper questionCommunityMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;

    @Override
    public List<Questionnaire> selectQuestionnaireList(Questionnaire questionnaire) {
        questionnaire.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        List<Questionnaire> list = investigationMapper.selectQuestionnaireList(questionnaire);
        for (Questionnaire ques : list) {
            //ques.setEstateName(investigationMapper.selectCommunityById(ques.getCommunityId()).getCommunityName());
            String tableType = "0";
            Question question = new Question();
            question.setQuestionId(ques.getQuestionnaireId());
            question.setTableType(tableType);
            ques.setHeadingList(questionMapper.selectQuestion(question));
            //根据这个获取简答人数
            for (int i = 0; i < ques.getHeadingList().size(); i++) {
                if (ques.getHeadingList().get(i).getQuestionType().equals("2")) {
                    //如果是文本答题，则统计答题的人数
                    ques.getHeadingList().get(i).setPaperNum(questionAnswerMapper.findByQuestionId(ques.getHeadingList().get(i).getQuestionId()).size());
                } else {
                    ques.getHeadingList().get(i).setPaperNum(0);
                }
                List<Item> item = itemMapper.selectItem(ques.getHeadingList().get(i).getQuestionId());
                for (Item item1 : item) {
                    Long count = 0L;
                    //如果是简答题或者选择题，则统计答案里面的内容选项
                    List<QuestionAnswer> questionAnswerList = questionAnswerMapper.findByQuestionId(item1.getQuestionId());
                    for (QuestionAnswer questionAnswer : questionAnswerList) {
                        if (questionAnswer.getUserAnswer().contains(item1.getItemId().toString())) {
                            count += 1;
                        }
                    }
                    item1.setPersonNum(count);
                }
                ques.getHeadingList().get(i).setItemList(item);
            }
        }
        return list;
    }

    @Override
    public Questionnaire selectQuestionnaireById(Long questionnaireId) {
        return investigationMapper.selectQuestionnaireById(questionnaireId);
    }

    @Override
    public int insertQuestionnaire(Questionnaire questionnaire) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date first = sdf.parse(questionnaire.getStartEndTime()[0]);
        Date lastTime = sdf.parse(questionnaire.getStartEndTime()[1]);
        questionnaire.setStartDate(first);
        questionnaire.setEndDate(lastTime);
        questionnaire.setCreateBy(SecurityUtils.getUsername());
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        questionnaire.setEstateId(sysUser.getEstateId());
        Integer num = investigationMapper.insertQuestionnaire(questionnaire);
        //添加问卷调查发布范围基本信息
        for (int i = 0; i < questionnaire.getCommunityIds().size(); i++) {
            QuestionCommunity questionCommunity = new QuestionCommunity();
            questionCommunity.setQuestionnaireId(questionnaire.getQuestionnaireId());
            questionCommunity.setCommunityId(questionnaire.getCommunityIds().get(i));
            num = questionCommunityMapper.insertQuestionCommunity(questionCommunity);
        }

        //添加问卷调查单选题题目
        for (int i = 0; i < questionnaire.getSingle().size(); i++) {
            //添加题目
            Question question = new Question();
            question.setQuestionName(questionnaire.getSingle().get(i).getTitle());
            question.setTableType("0");
            question.setTableId(questionnaire.getQuestionnaireId());
            question.setQuestionType(0L);
            num = questionMapper.insertQuestion(question);


            //添加题目对应选项
            for (int j = 0; j < questionnaire.getSingle().get(i).getItemValue().size(); j++) {
                Item item = new Item();
                item.setQuestionId(question.getQuestionId());
                item.setItemName(questionnaire.getSingle().get(i).getItemValue().get(j).getValue());
                num = itemMapper.insertItem(item);
            }
        }

        //添加问卷调查单多选题题目
        for (int i = 0; i < questionnaire.getMultiple().size(); i++) {
            //添加题目
            Question question = new Question();
            question.setTableType("0");
            question.setQuestionName(questionnaire.getMultiple().get(i).getTitle());
            question.setTableId(questionnaire.getQuestionnaireId());
            question.setQuestionType(1L);
            num = questionMapper.insertQuestion(question);

            //添加题目对应选项
            //多选题选项必须大于一，否则不能添加成功
            for (int j = 0; j < questionnaire.getMultiple().get(i).getItemValue().size(); j++) {
                Item item = new Item();
                item.setQuestionId(question.getQuestionId());
                item.setItemName(questionnaire.getMultiple().get(i).getItemValue().get(j).getValue());
                num = itemMapper.insertItem(item);
            }
        }
        //添加问卷调查单行文本题目
        for (int i = 0; i < questionnaire.getText().size(); i++) {
            //添加题目
            Question question = new Question();
            question.setTableType("0");
            question.setQuestionName(questionnaire.getText().get(i).getTitle());
            question.setTableId(questionnaire.getQuestionnaireId());
            question.setQuestionType(2L);
            num = questionMapper.insertQuestion(question);
            //添加题目对饮选项
        }
        return num;
    }

    @Override
    public int deleteQuestionnaireById(Long[] questionnaireId) {
        return investigationMapper.deleteQuestionnaireById(questionnaireId);
    }
}
