package com.wr.govern.service.impl.autonomy.research;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.autonomy.investigation.ItemMapper;
import com.wr.govern.mapper.autonomy.investigation.QuestionAnswerMapper;
import com.wr.govern.mapper.autonomy.investigation.QuestionMapper;
import com.wr.govern.mapper.autonomy.investigation.ResearchPartyMapper;
import com.wr.govern.mapper.autonomy.research.ResearchMapper;
import com.wr.govern.mapper.party.PartyMapper;
import com.wr.govern.service.autonomy.research.ResearchService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.domain.vo.AnswerVo;
import com.wr.remote.domain.vo.ApiResearchVo;
import com.wr.remote.govern.autonomy.Research.Research;
import com.wr.remote.govern.autonomy.investigation.Item;
import com.wr.remote.govern.autonomy.investigation.Question;
import com.wr.remote.govern.autonomy.investigation.QuestionAnswer;
import com.wr.remote.govern.autonomy.investigation.ResearchParty;
import com.wr.remote.govern.party.vo.PartyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 党建调研服务类实现
 *
 * @author liubei
 * @createDate 2022-10-24
 */
@Service
public class ResearchServiceImpl implements ResearchService {
    @Autowired
    private ResearchMapper researchMapper;
    @Autowired
    private ResearchPartyMapper researchPartyMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;
    @Autowired
    private PartyMapper partyMapper;

    @Override
    public List<Research> selectResearchList(Research research) {
        List<Research> list = researchMapper.selectResearchList(research);
        for (Research ques : list) {
            //ques.setEstateName(researchMapper.selectCommunityById(ques.getCommunityId()).getCommunityName());
            String tableType = "1";
            Question question = new Question();
            question.setQuestionId(ques.getResearchId());
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
    public Research selectResearchById(Long researchById) {
        return researchMapper.selectResearchById(researchById);
    }

    @Override
    public int insertResearch(Research research) {
        research.setStatus( "1" );
        research.setCreateBy(SecurityUtils.getUsername());
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        //查询党建调研小区id
            PartyVO partyVO=partyMapper.getPartyById(research.getCommunityIds());
            research.setCommunity(partyVO.getCommunityId());

        Integer num = researchMapper.insertResearch(research);
/*        Long communityId = research.getCommunityId();
        List<Long> longs = Lists.newArrayList();
        if (communityId != null) {
            longs.add(communityId);
            research.setCommunityIds(longs);
        }*/
        //添加党建调研发布范围基本信息
            ResearchParty researchParty = new ResearchParty();
            researchParty.setResearchId(research.getResearchId());
            researchParty.setPartyId(research.getCommunityIds());
            num = researchPartyMapper.insertResearchParty(researchParty);

        //添加党建调研单选题题目
        for (int i = 0; i < research.getSingle().size(); i++) {
            //添加题目
            Question question = new Question();
            question.setTableType("1");
            question.setQuestionName(research.getSingle().get(i).getTitle());
            question.setTableId(research.getResearchId());
            question.setQuestionType(0L);
            num = questionMapper.insertQuestion(question);


            //添加题目对应选项
            for (int j = 0; j < research.getSingle().get(i).getItemValue().size(); j++) {
                Item item = new Item();
                item.setQuestionId(question.getQuestionId());
                item.setItemName(research.getSingle().get(i).getItemValue().get(j).getValue());
                num = itemMapper.insertItem(item);
            }
        }

        //添加党建调研单多选题题目
        for (int i = 0; i < research.getMultiple().size(); i++) {
            //添加题目
            Question question = new Question();
            question.setTableType("1");
            question.setQuestionName(research.getMultiple().get(i).getTitle());
            question.setTableId(research.getResearchId());
            question.setQuestionType(1L);
            num = questionMapper.insertQuestion(question);

            //添加题目对应选项
            for (int j = 0; j < research.getMultiple().get(i).getItemValue().size(); j++) {
                if (research.getMultiple().get(i).getItemValue().size() <= 1) {
                    return 0;
                }
                Item item = new Item();
                item.setQuestionId(question.getQuestionId());
                item.setItemName(research.getMultiple().get(i).getItemValue().get(j).getValue());
                num = itemMapper.insertItem(item);
            }
        }
        //添加党建调研单行文本题目
        for (int i = 0; i < research.getText().size(); i++) {
            //添加题目
            Question question = new Question();
            question.setTableType("1");
            question.setQuestionName(research.getText().get(i).getTitle());
            question.setTableId(research.getResearchId());
            question.setQuestionType(2L);
            num = questionMapper.insertQuestion(question);
            //添加题目对饮选项
        }
        return num;
    }

    @Override
    public int deleteResearchById(Long[] researchId) {
        return researchMapper.deleteResearchById(researchId);
    }

    @Override
    public AjaxResult findListPartyMemberResearch(Research research) {
        AjaxResult ajaxResult = new AjaxResult();
        //table_type及table_id查询表bus_question 查询出题目列表
            // 题目List
        List<ApiResearchVo> researchVos = researchMapper.selectItem(research.getResearchId());
        researchVos.forEach(arrs -> {
            if (arrs.getQuestionId() != null && ("0".equals(arrs.getQuestionType()) || "1".equals(arrs.getQuestionType()))) {
                // 题目List
                arrs.setQuestionItems(researchMapper.selectQuestionList(arrs.getQuestionId()));
            }
        });
        //根据题目列表查询bus_question_item 题目选项表
        List<AnswerVo> quesAnswers = researchMapper.answerDetail(research);
        quesAnswers.forEach(arr -> {
            if ("1".equals(arr.getQuestionType())) {
                // 多选
                ArrayList<String> arrayList = new ArrayList<>();
                Collections.addAll(arrayList, arr.getUserAnswer().split(","));
                arr.setUserAnswerArr(arrayList);
            } else {
                // 单选或简答
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(arr.getUserAnswer());
                arr.setUserAnswerArr(arrayList);
            }
        });

        ajaxResult.isSuccess();
        ajaxResult.put( "research",researchVos );
        ajaxResult.put( "quesAnswers",quesAnswers );
        return ajaxResult;
    }

    @Override
    public List<Map<String, Object>> findListAccoutByQuestionId(Long tableId) {
        List<Map<String, Object>> accoutByQuestionId = questionAnswerMapper.findListAccoutByQuestionId( tableId );
        return accoutByQuestionId;
    }
}
