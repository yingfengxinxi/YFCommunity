package com.wr.govern.mapper.autonomy.research;

import com.wr.remote.domain.Community;
import com.wr.remote.domain.vo.AnswerVo;
import com.wr.remote.domain.vo.ApiResearchVo;
import com.wr.remote.domain.vo.QuestionItem;
import com.wr.remote.govern.autonomy.Research.Research;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 党建调研管理信息
 *
 * @author liubei
 * @createDate 2022/10/25
 */
public interface ResearchMapper
{
    /**
     * 查询所有党建调研题目
     * @param research
     * @return
     */
    List<Research> selectResearchList(Research research);

    /**
     * 根据党建调研ID查询党建调研信息
     * @param researchId
     * @return
     */
    Research selectResearchById(Long researchId);

    /***
     * 新增党建调研信息
     * @param research
     * @return
     */
    int insertResearch(Research research);

    /**
     * 根据党建调研ID删除党建信息
     * @param researchId
     * @return
     */
    int deleteResearchById(Long[] researchId);

    /**
     * 查询小区信息
     * @param communityId
     * @return
     */
    Community selectCommunityById(Long communityId);

    /**
     * 详情 - 题目
     * @param researchId
     * @return
     */
    List<ApiResearchVo> selectItem(Long researchId);

    /**
     * 题目列表
     * @param questionId
     * @return
     */
    List<QuestionItem> selectQuestionList(Long questionId);

    /**
     * 查找答案
     * @param research
     * @return
     */
    List<AnswerVo> answerDetail(Research research);
}
