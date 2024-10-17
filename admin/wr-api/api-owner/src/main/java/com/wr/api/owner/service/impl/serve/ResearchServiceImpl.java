package com.wr.api.owner.service.impl.serve;

import cn.hutool.core.collection.CollUtil;
import com.wr.api.owner.entity.dto.QuesAnswerDTO;
import com.wr.api.owner.entity.serve.ApiResearch;
import com.wr.api.owner.entity.serve.QuesAnswer;
import com.wr.api.owner.entity.vo.serve.AnswerVo;
import com.wr.api.owner.entity.vo.serve.ApiResearchVo;
import com.wr.api.owner.mapper.serve.QuesAnswerMapper;
import com.wr.api.owner.mapper.serve.ResearchMapper;
import com.wr.api.owner.service.serve.ResearchService;
import com.wr.common.core.web.domain.AjaxResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 党建调研
 * @Author: lvzy
 * @Date: 2022-11-15 18:01:28
 * @Desc:党建调研
 */
@Service
@AllArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    // 党建调研
    private final ResearchMapper researchMapper;

    // 问卷答案
    private final QuesAnswerMapper quesAnswerMapper;

    @Override
    public List<ApiResearchVo> newList(ApiResearchVo researchVo) {

        return researchMapper.listPage(researchVo);
    }


    @Override
    public List<ApiResearchVo> oldList(ApiResearchVo researchVo) {
        researchVo.setResearchStatus("1");
        return researchMapper.listPage(researchVo);
    }

    @Override
    public AjaxResult detail(Long researchId) {
        return AjaxResult.success(researchMapper.detail(researchId));
    }

    @Override
    public AjaxResult selectItem(Long researchId) {
        // 题目List
        List<ApiResearchVo> researchVos = researchMapper.selectItem(researchId);
        researchVos.forEach(arrs -> {
            if (arrs.getQuestionId() != null && ("0".equals(arrs.getQuestionType()) || "1".equals(arrs.getQuestionType()))) {
                // 题目List
                arrs.setQuestionItems(researchMapper.selectQuestionList(arrs.getQuestionId()));
            }
        });
        return AjaxResult.success(researchVos);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult submit(QuesAnswerDTO answerList) {
        // 新增问卷答案表
        answerList.getQuesAnswers().forEach(arr -> {
            ApiResearch apiResearch = new ApiResearch();
            QuesAnswer quesAnswer = new QuesAnswer();
            quesAnswer.setAccountId(answerList.getAccountId());
            quesAnswer.setTableId(answerList.getTableId());
            quesAnswer.setQuestionId(arr.getQuestionId());
            quesAnswer.setUserAnswer(arr.getUserAnswer());
            quesAnswer.setTableType("1");
            quesAnswerMapper.insertSelective(quesAnswer);
            // 关闭research_status
            apiResearch.setResearchId(answerList.getTableId());
            apiResearch.setResearchStatus("1");
            apiResearch.setStatus( "2" );
            researchMapper.updateByPrimaryKeySelective(apiResearch);
        });
        return AjaxResult.success();
    }

    @Override
    public AjaxResult answerDetail(Long researchId,Long accountId) {
        List<AnswerVo> quesAnswers = quesAnswerMapper.answerDetail(researchId,accountId);
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
        return AjaxResult.success(quesAnswers);
    }

    @Override
    public List<ApiResearchVo> findListResearch(ApiResearchVo apiResearchVo) {
        List<ApiResearchVo> listResearch = researchMapper.findListResearch( apiResearchVo );
        if(CollUtil.isNotEmpty( listResearch ) && listResearch.size() > 0){
            for (ApiResearchVo research : listResearch) {
                //查询参与人数进行插入
                research.setCount(  researchMapper.findCountAnswer( research.getResearchId() ) );
            }
        }
        return listResearch;
    }
}
