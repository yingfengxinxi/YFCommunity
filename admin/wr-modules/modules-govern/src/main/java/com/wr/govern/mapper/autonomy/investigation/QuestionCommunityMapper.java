package com.wr.govern.mapper.autonomy.investigation;


import com.wr.remote.govern.autonomy.investigation.QuestionCommunity;

/**
 * 问卷调研管理信息
 *
 * @author liubei
 * @createDate 2022/10/25
 */
public interface QuestionCommunityMapper {

    /**
     * 新增问卷调查发布范围
     * @param questionCommunity
     * @return
     */
    public int insertQuestionCommunity(QuestionCommunity questionCommunity);
}
